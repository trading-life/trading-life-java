package com.aiden.trading;

import com.dolphindb.jdbc.JDBCConnection;
import com.dolphindb.jdbc.JDBCStatement;
import com.xxdb.*;
import com.xxdb.comm.ErrorCodeInfo;
import com.xxdb.comm.SqlStdEnum;
import com.xxdb.data.*;
import com.xxdb.multithreadedtablewriter.MultithreadedTableWriter;
import com.xxdb.route.AutoFitTableUpsert;
import com.xxdb.route.PartitionedTableAppender;
import com.xxdb.streaming.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * <a href="https://docs.dolphindb.cn/zh/api/java/java.html">...</a>
 * 维度表是分布式数据库中没有进行分区的表，适用于存储不频繁更新的小数据集。
 * k线案例
 * <a href="https://docs.dolphindb.cn/zh/tutorials/best_practices_for_partitioned_storage.html?hl=%E7%BB%B4%E5%BA%A6#312-%E8%82%A1%E7%A5%A8%E6%97%A5-k-%E7%BA%BF">...</a>
 * 数据回放
 * https://docs.dolphindb.cn/zh/tutorials/data_replay.html
 */
public class DolphindbTest {

    static SimpleDBConnectionPool dbConnectionPool;
    static DBConnection conn;

    static {
        SimpleDBConnectionPoolConfig config = new SimpleDBConnectionPoolConfig();
        config.setHostName("127.0.0.1");
        config.setPort(8031);
        config.setUserId("admin");
        config.setPassword("123456");
        config.setInitialPoolSize(5);
        config.setEnableHighAvailability(false);
        dbConnectionPool = new SimpleDBConnectionPool(config);
        // 从连接池中获取一个连接
        conn = dbConnectionPool.getConnection();
    }

    /**
     * dolphindb 建内存表
     * t = table(10000:0,`cstring`cint`ctimestamp`cdouble,[STRING,INT,TIMESTAMP,DOUBLE])
     * share t as sharedTable
     *
     * @throws IOException
     */
    @Test
    public void testInsert() throws IOException {
        test_save_Insert("sharedTable", "hel2", 2, 22L, 2.2);
    }

    @Test
    public void testBatchInsert() throws IOException {
        test_save_TableInsert("sharedTable", List.of("hel2"), List.of(2), List.of(22L), List.of(2.2));
    }

    @Test
    public void testBasicTableInsert() throws IOException {
        List<String> colNames = List.of("cstring", "cint", "ctimestamp", "cdouble");
        List<Vector> cols = new ArrayList<>();
        Vector cstring = new BasicStringVector(List.of("he22222l2221111"));
        cols.add(cstring);
        Vector cint = new BasicIntVector(List.of(22122222));
        cols.add(cint);
        Vector ctimestamp = new BasicTimestampVector(List.of(1711855160000L));
        cols.add(ctimestamp);
        Vector cdouble = new BasicDoubleVector(List.of(2.2));
        cols.add(cdouble);
        BasicTable table1 = new BasicTable(colNames, cols);
        test_save_table("sharedTable", table1);
    }

    public void test_save_Insert(String memoryTableName, String str, int i, long ts, double dbl) throws IOException {
        conn.run(String.format("insert into %s values('%s',%s,%s,%s)", memoryTableName, str, i, ts, dbl));
    }

    public void test_save_TableInsert(String memoryTableName, List<String> strArray, List<Integer> intArray, List<Long> tsArray, List<Double> dblArray) throws IOException {
        String sql = String.format("tableInsert{%s}", memoryTableName);
        //用数组构造参数
        List<Entity> args = Arrays.asList(new BasicStringVector(strArray), new BasicIntVector(intArray), new BasicTimestampVector(tsArray), new BasicDoubleVector(dblArray));
        conn.run(sql, args);
    }

    /**
     * dolphindb 建内存表
     * 主键由sym和id字段组成
     * t=keyedTable(`sym`id,1:0,`sym`id`val,(SYMBOL,INT,INT))
     * share t as sharedTable1
     * 重复添加只会更新主键的记录
     *
     * @throws IOException
     */
    @Test
    public void testBasicTableInsert2() throws IOException {
        List<String> colNames = List.of("sym", "id", "val");
        List<Vector> cols = new ArrayList<>();
        Vector sym = new BasicSymbolVector(List.of("hel2"));
        cols.add(sym);
        Vector id = new BasicIntVector(List.of(23));
        cols.add(id);
        Vector val = new BasicIntVector(List.of(223));
        cols.add(val);
        BasicTable table1 = new BasicTable(colNames, cols);
        test_save_table("sharedTable1", table1);
    }

    public void test_save_table(String memoryTableName, BasicTable table1) throws IOException {
        List<Entity> args = List.of(table1);
        String sql = String.format("tableInsert{%s}", memoryTableName);
        conn.run(sql, args);
    }

    /**
     * dolphindb 建分布式表
     * dbPath = 'dfs://testDatabase'
     * tbName = 'tb1'
     * <p>
     * if(existsDatabase(dbPath)){dropDatabase(dbPath)}
     * db = database(dbPath,RANGE,2018.01.01..2018.12.31)
     * db.createPartitionedTable(t,tbName,'ctimestamp')
     */
    @Test
    public void testDistributionBasicTableInsert2() throws IOException {
        List<String> colNames = List.of("sym", "id", "ctimestamp", "val");
        List<Vector> cols = new ArrayList<>();
        Vector sym = new BasicSymbolVector(List.of("hel2"));
        cols.add(sym);
        Vector id = new BasicIntVector(List.of(232));
        cols.add(id);
        Vector ctimestamp = new BasicTimestampVector(List.of(1709349560000L));
        cols.add(ctimestamp);
        Vector val = new BasicIntVector(List.of(225));
        cols.add(val);
        BasicTable table1 = new BasicTable(colNames, cols);
        test_save_distribution_table("dfs://testDatabase3", "tb1", table1);
    }

    @Test
    public void testDistributionBasicTableInsert3() throws IOException {
        List<String> colNames = List.of("sym", "id", "ctimestamp", "val");
        List<Vector> cols = new ArrayList<>();
        Vector sym = new BasicSymbolVector(List.of("hel2"));
        cols.add(sym);
        Vector id = new BasicIntVector(List.of(232));
        cols.add(id);
        Vector ctimestamp = new BasicTimestampVector(List.of(1709349560000L));
        cols.add(ctimestamp);
        Vector val = new BasicIntVector(List.of(223));
        cols.add(val);
        BasicTable table1 = new BasicTable(colNames, cols);
        test_save_distribution_table("dfs://testDatabase10", "tb1", table1);
    }

    public void test_save_distribution_table(String dbPath, String tableName, BasicTable table1) throws IOException {
        List<Entity> args = new ArrayList<Entity>(1);
        args.add(table1);
        conn.run(String.format("tableInsert{loadTable('%s','%s')}", dbPath, tableName), args);
    }

    @Test
    public void testMutBasicTableInsert3() throws Exception {
        DBConnectionPool conn = new ExclusiveDBConnectionPool("192.168.0.208", 8031, "admin", "123456", 2, false, false);

        String dbPath = "dfs://demohash";
        String tableName = "pt";
        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "sym", conn);
//        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "gid", "saveGridData{'" + dbPath + "','" + tableName + "'}", conn);
        BasicTable table1 = createTable(1709349560000L, "232");
        appender.append(table1);

        conn.waitForThreadCompletion();
        conn.shutdown();
    }

    @Test
    public void testMutBasicTableInsert4() throws Exception {
        DBConnectionPool conn = new ExclusiveDBConnectionPool("192.168.0.208", 8031, "admin", "123456", 2, false, false);

        String dbPath = "dfs://demohash1";
        String tableName = "pt";
        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "sym", conn);
//        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "gid", "saveGridData{'" + dbPath + "','" + tableName + "'}", conn);
        BasicTable table1 = createTable(1709349560000L, "2322");
        appender.append(table1);

        conn.waitForThreadCompletion();
        conn.shutdown();
    }

    @Test
    public void testMutBasicTableInsert5() throws Exception {

        List<String> colNames = List.of("date", "sym", "val");
        List<Vector> cols = new ArrayList<>();
        Vector date = new BasicTimestampVector(List.of(1709349560000L));
        cols.add(date);
        Vector sym = new BasicStringVector(List.of("2322"));
        cols.add(sym);
        Vector val = new BasicStringVector(List.of("3"));
        cols.add(val);
        BasicTable table = new BasicTable(colNames, cols);

        DBConnectionPool conn1 = new ExclusiveDBConnectionPool("192.168.0.208", 8031, "admin", "123456", 2, false, false);

        String dbPath = "dfs://demohash2";
        String tableName = "pt";
        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "sym", conn1);
//        PartitionedTableAppender appender = new PartitionedTableAppender(dbPath, tableName, "gid", "saveGridData{'" + dbPath + "','" + tableName + "'}", conn);
        appender.append(table);

        conn1.waitForThreadCompletion();
        conn1.shutdown();
    }

    private BasicTable createTable(Long timestamp, String val) {
        List<String> colNames = List.of("date", "sym");
        List<Vector> cols = new ArrayList<>();
        Vector date = new BasicTimestampVector(List.of(timestamp));
        cols.add(date);
        Vector sym = new BasicStringVector(List.of(val));
        cols.add(sym);
        BasicTable table = new BasicTable(colNames, cols);
        return table;
    }


    @Test
    public void testQueryMemoryTableAll() throws Exception {
        String tbName = "sharedTable";
        BasicTable table = (BasicTable) conn.run(String.format("select * from %s", tbName));
        for (int i = 0; i < table.columns(); i++) {
            System.out.println(table.getColumnName(i));
        }
    }


    @Test
    public void testQueryTableAll() throws Exception {
        String dbPath = "dfs://demohash2";
        String tbName = "pt";
        BasicTable table = (BasicTable) conn.run(String.format("select * from loadTable('%s','%s')", dbPath, tbName));
        test_loop_basicTable(table);
    }

    public void test_loop_basicTable(BasicTable table1) throws Exception {
        BasicTimestampVector stringv = (BasicTimestampVector) table1.getColumn("date");
        BasicStringVector intv = (BasicStringVector) table1.getColumn("sym");
        BasicStringVector timestampv = (BasicStringVector) table1.getColumn("val");
        for (int ri = 0; ri < table1.rows(); ri++) {
            System.out.println(timestampv.getString(ri));
            System.out.println(intv.getString(ri));
            LocalDateTime timestamp = stringv.getTimestamp(ri);
            System.out.println(timestamp);
        }
    }

    /**
     * <a href="https://docs.dolphindb.cn/zh/api/java/java.html#%E4%BD%BF%E7%94%A8tableinsert%E5%87%BD%E6%95%B0%E4%BF%9D%E5%AD%98-basictable-%E5%AF%B9%E8%B1%A1-1">...</a>
     * 没有成功
     */

    @Test
    public void testMultithreadedTableWriter() throws Exception {

//        MultithreadedTableWriter multithreadingTableWriter_ = new MultithreadedTableWriter("192.168.0.208", 8031, "admin", "123456", "dfs://demohash2", "pt",
//                false, false, null, 10000, 1,
//                5, "sym", new int[]{Vector.COMPRESS_LZ4, Vector.COMPRESS_LZ4, Vector.COMPRESS_DELTA}, MultithreadedTableWriter.Mode.M_Upsert,new String[]{"val"});
       MultithreadedTableWriter multithreadingTableWriter_ = new MultithreadedTableWriter("192.168.0.208", 8031, "admin", "123456", "dfs://demohash2", "pt",
                false, false, null, 10000, 1,
                5, "sym", null, MultithreadedTableWriter.Mode.M_Append,null);
        ErrorCodeInfo ret;
//插入 1 行数据，插入数据的列数和表的列数不匹配，MTW 立刻返回错误信息
        ret = multithreadingTableWriter_.insert(1709349560000L, "102000", "0000");
        ret = multithreadingTableWriter_.insert(1709349560000L, "100300", "0000");
        ret = multithreadingTableWriter_.insert(1709349560000L, "100400", "0000");
        ret = multithreadingTableWriter_.insert(1709349560000L, "100010", "0000");
        ret = multithreadingTableWriter_.insert(1709349560000L, "100500", "0000");
        if (!ret.getErrorCode().equals(""))
            System.out.println("insert wrong format data: {3}\n" + ret.toString());
    }
    @Test
    public void testSubscribe() throws Exception {

        PollingClient client = new PollingClient(10009);
        TopicPoller poller1 = client.subscribe("192.168.0.208", 8031, "trades", "trades", -1,true,null,"admin", "123456");
    }
    @Test
    public void testSubscribePooledClient() throws Exception {
        ThreadPooledClient client = new ThreadPooledClient(10000,10);
        client.subscribe("192.168.0.208", 8031, "trades","trades", new MyHandler(), -1,true,null,null,false,"admin", "123456");
        Thread.sleep(1000000000);
        client.unsubscribe("192.168.0.208", 8031, "trades","trades");
    }

    @Test
    public void testJdbc() throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user","admin");
        prop.setProperty("password","123456");
        prop.setProperty("sqlStd", SqlStdEnum.DolphinDB.getName());
        String url = "jdbc:dolphindb://127.0.0.1:8031?user=admin&password=123456";

//        prop.put("tableAlias","orders:dfs://test_stock1/orders,trades:dfs://test_stock1/trades,");
        Connection conn = new JDBCConnection(url,prop);
        JDBCStatement stm = null;
        try {
            Class.forName("com.dolphindb.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stm = (JDBCStatement)conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from loadTable(\"dfs://test_stock1\",\"orders\")");
            printData(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Release
            try {
                if (stm != null)
                    stm.close();
            } catch (SQLException se2) {
            }
            // Rlease
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public static void printData(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int len = resultSetMetaData.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= len; ++i) {
                System.out.print(
                        MessageFormat.format("{0}: {1},    ", resultSetMetaData.getColumnName(i), rs.getObject(i)));
            }
            System.out.print("\n");
        }
    }
    @Test
    public void ts() throws Exception {
        DBConnection conn = new DBConnection(false, false, false);
        conn.connect("127.0.0.1", 8031, "admin", "123456");
        String dbName ="dfs://upsertTable";
        String tableName = "pt";
//        String script = "dbName = \"dfs://upsertTable\"\n"+
//                "if(exists(dbName)){\n"+
//                "\tdropDatabase(dbName)\t\n"+
//                "}\n"+
//                "db  = database(dbName, RANGE,1 10000)\n"+
//                "t = table(1000:0, `id`value,[ INT, INT])\n"+
//                "pt = db.createPartitionedTable(t,`pt,`id)";
//        conn.run(script);

        BasicIntVector v1 = new BasicIntVector(List.of(1));
        BasicIntVector v2 = new BasicIntVector(List.of(1));

        BasicArrayVector ba = new BasicArrayVector(Entity.DATA_TYPE.DT_INT_ARRAY, 1);
        ba.Append(v1);
        ba.Append(v1);
        ba.Append(v1);

        List<String> colNames = new ArrayList<>();
        colNames.add("id");
        colNames.add("value");
        List<Vector> cols = new ArrayList<>();
        cols.add(v1);
        cols.add(v2);
        BasicTable bt = new BasicTable(colNames, cols);
        String[] keyColName = new String[]{"id"};
        AutoFitTableUpsert aftu = new AutoFitTableUpsert(dbName, tableName, conn, false, keyColName, null);
        aftu.upsert(bt);
        BasicTable res = (BasicTable) conn.run("select * from loadTable('dfs://upsertTable','pt');");
        System.out.println(res.getString());
    }

}

class MyHandler implements MessageHandler {
    public void doEvent(IMessage msg) {
        BasicInt qty = msg.getValue(2);
        //..处理数据
    }
}