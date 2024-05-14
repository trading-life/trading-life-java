package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GetChartsListResp {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("timestamp")
        private Long timestamp;
        @JsonProperty("symbol")
        private String symbol;
        @JsonProperty("resolution")
        private String resolution;
    }
}
