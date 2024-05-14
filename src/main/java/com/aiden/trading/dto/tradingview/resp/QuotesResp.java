package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * s: 商品的状态码。预期的值为:ok|error
 * n: 商品名称。 此值必须与请求中 完全相同
 * v: object, 商品报价对象
 * ch: 价格变动（通常从当天的开盘价计算）
 * chp: 价格变动百分比
 * short_name: 商品略称
 * exchange: 交易所名称
 * description: 商品的简短描述
 * lp: 最后的成交价格
 * ask: 买盘价
 * bid: 卖盘价
 * spread: 费率
 * open_price: 当天开盘价
 * high_price: 当天最高价
 * low_price: 当天最低价
 * prev_close_price: 昨天收盘价
 * volume: 当天成交量
 */
@NoArgsConstructor
@Data
public class QuotesResp {
    @JsonProperty("s")
    private String s;
    @JsonProperty("d")
    private List<Datas> d;

    @NoArgsConstructor
    @Data
    public static class Datas {
        @JsonProperty("s")
        private String s;
        @JsonProperty("n")
        private String n;
        @JsonProperty("v")
        private DataV v;

        @NoArgsConstructor
        @Data
        public static class DataV {
            @JsonProperty("ch")
            private BigDecimal ch;
            @JsonProperty("chp")
            private BigDecimal chp;
            @JsonProperty("short_name")
            private String shortName;
            @JsonProperty("exchange")
            private String exchange;
            @JsonProperty("original_name")
            private String originalName;
            @JsonProperty("description")
            private String description;
            @JsonProperty("lp")
            private BigDecimal lp;
            @JsonProperty("ask")
            private BigDecimal ask;
            @JsonProperty("bid")
            private BigDecimal bid;
            @JsonProperty("open_price")
            private BigDecimal openPrice;
            @JsonProperty("high_price")
            private BigDecimal highPrice;
            @JsonProperty("low_price")
            private BigDecimal lowPrice;
            @JsonProperty("prev_close_price")
            private BigDecimal prevClosePrice;
            @JsonProperty("volume")
            private BigDecimal volume;
        }
    }
}
