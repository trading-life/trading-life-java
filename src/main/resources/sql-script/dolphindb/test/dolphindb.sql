-- dolphindb 建内存表
t = table(10000:0,`cstring`cint`ctimestamp`cdouble,[STRING,INT,TIMESTAMP,DOUBLE])
share t as sharedTable

-- dolphindb 建内存表
-- 主键由sym和id字段组成
-- 重复添加只会更新主键的记录
t=keyedTable(`sym`id,1:0,`sym`id`val,(SYMBOL,INT,INT))
share t as sharedTable1
-- select * from sharedTable1
--  insert into sharedTable1 values(`GOOG1, 200,23);

-- dolphindb 建分布式表
dbPath = 'dfs://testDatabase3'
tbName = 'tb1'
if (existsDatabase(dbPath)) {
    dropDatabase(dbPath)
}
db = database(dbPath, RANGE, 2024.01.01..2024.12.31)
t=table(1:0,`sym`id`ctimestamp`val,(SYMBOL,INT,TIMESTAMP,INT))
db.createPartitionedTable(t, tbName, 'ctimestamp')
-- select * from loadTable("dfs://testDatabase3","tb1")

-- dolphindb 建分布式表

dbPath = 'dfs://testDatabase10'
tbName = 'tb1'
if (existsDatabase(dbPath)) {
    dropDatabase(dbPath)
}
db = database(dbPath, RANGE, 2024.01.01..2024.12.31,engine="TSDB")
t=keyedTable(`ctimestamp`sym,1:0,`sym`id`ctimestamp`val,(SYMBOL,INT,TIMESTAMP,INT))
db.createPartitionedTable(t, tbName, 'ctimestamp',keepDuplicates=LAST,sortColumns=`sym`ctimestamp)
-- select * from loadTable("dfs://testDatabase10","tb1")

-- 数据库按照 VALUE-HASH-HASH 的组合进行三级分区。
t = table(timestamp(1..10)  as date,string(1..10) as sym)
db1=database("",HASH,[DATETIME,10])
db2=database("",HASH,[STRING,5])
if(existsDatabase("dfs://demohash")){
    dropDatabase("dfs://demohash")
}
db =database("dfs://demohash",COMPO,[db2,db1])
pt = db.createPartitionedTable(t,`pt,`sym`date)
-- select * from loadTable("dfs://demohash","pt")

-- 数据库按照 VALUE-HASH-HASH 的组合进行三级分区。
t = table(timestamp(1..10)  as date,string(1..10) as sym)
db1=database("",HASH,[DATETIME,10])
db2=database("",HASH,[STRING,5])
if(existsDatabase("dfs://demohash1")){
    dropDatabase("dfs://demohash1")
}
db =database("dfs://demohash1",COMPO,[db2,db1],engine="TSDB")
pt = db.createPartitionedTable(t,`pt,`sym`date,keepDuplicates=LAST,sortColumns=`sym`date)
-- select * from loadTable("dfs://demohash1","pt")

-- 数据库按照 VALUE-HASH-HASH 的组合进行三级分区。
t = table(timestamp(1..10)  as date,string(1..10) as sym,string(1..10) as val)
db1=database("",HASH,[DATETIME,10])
db2=database("",HASH,[STRING,5])
if(existsDatabase("dfs://demohash2")){
    dropDatabase("dfs://demohash2")
}
db =database("dfs://demohash2",COMPO,[db2,db1],engine="TSDB")
pt = db.createPartitionedTable(t,`pt,`sym`date,keepDuplicates=LAST,sortColumns=`sym`date)
-- select * from loadTable("dfs://demohash2","pt")


-- https://docs.dolphindb.cn/zh/help/FunctionsandCommands/CommandsReferences/d/dropStreamTable.html?highlight=stream
colNames = `timestamp`sym`qty`price
colTypes = [TIMESTAMP,SYMBOL,INT,DOUBLE]
t=streamTable(1:0,colNames,colTypes)
enableTableShareAndPersistence(t,`trades);
-- dropStreamTable(`trades);