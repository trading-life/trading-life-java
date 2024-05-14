package com.aiden.trading;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.junit.jupiter.api.Test;

public class TalibTest {

    /*
   简单移动平均，需要懂的参数一览:
       int startIdx：开始的索引
       int endIdx：结束的索引
       double[] inReal：收盘价集合
       int optInTimePeriod：移动平均的周期数，如 MA20
       MInteger outBegIdx：如 MA20 线开始的索引值
       MInteger outNBElement：如 MA20 线的长度
       double[] outReal：当日的 MA20 移动平均线的数值
    */

    /**
     * The number of periods to average together.
     * 含义：20日移动平均
     */
    public static final int PERIODS_AVERAGE = 20;

    @Test
    void simpleMovingAverage() {
        //数据存在
        //参数准备
        //收盘价的数组
        double[] closePrice = new double[10];
        //移动平均线数值的数组
        double[] out = new double[10];
        //MInteger类: mutable integer，可变整数
        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        //获取收盘价数组 length = 2580
//            for (int i = 0; i < stockHists.size(); i++) {
//                float close = stockHists.get(i).getClose();
//                closePrice[i] = close;
//            }

        //Talib的核心类
        Core core = new Core();
        //调用核心类
        RetCode retCode = core.sma(
                //int startIdx：开始的索引
                0,
                //int endIdx：结束的索引
                closePrice.length - 1,
                //收盘价集合
                closePrice,
                //移动平均的周期数，如 MA20
                PERIODS_AVERAGE,
                //如 MA20 线开始的索引值
                begin,
                //如 MA20 线的长度
                length,
                //当日的 MA20 移动平均线的数值
                out
        );

        //打印信息
        if (retCode == RetCode.Success) {

            //数值上 = PERIODS_AVERAGE-1
            System.out.println("输出开始的周期数: " + begin.value);
            //总周期数
            System.out.println("输出结束的周期数: " + (begin.value + length.value - 1));

            //遍历有线
            for (int i = begin.value; i < begin.value + length.value; i++) {
                //检验当前记录对应的id
//                    Long id = stockHists.get(i).getId();
//                    System.out.println("当前记录的id: "+id);
                //当日收盘价（保留两位小数）
                String tempClose = String.format("%.2f", closePrice[i]);
                //移动平均数（保留两位小数）
                String tempMovAver = String.format("%.2f", out[i - begin.value]);
                //拼接字符串
                String line = "Period # " +
                        i +
                        " 当日收盘价 = " +
                        tempClose +
                        " 移动平均数 = " +
                        tempMovAver;
                System.out.println(line);
            }
        } else {
            System.out.println("Error");
        }

    }
}
