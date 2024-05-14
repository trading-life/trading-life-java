package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetStudyTemplateResp {
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("content")
        private String content;
        @JsonProperty("name")
        private String name;
    }
}
