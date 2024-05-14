package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetChartsResp {

    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("timestamp")
        private Long timestamp;
        @JsonProperty("content")
        private String content;
    }
}
