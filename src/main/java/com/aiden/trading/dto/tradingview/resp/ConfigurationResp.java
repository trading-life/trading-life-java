package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ConfigurationResp {

    @JsonProperty("supports_search")
    private Boolean supportsSearch;
    @JsonProperty("supports_group_request")
    private Boolean supportsGroupRequest;
    @JsonProperty("supports_marks")
    private Boolean supportsMarks;
    @JsonProperty("supports_timescale_marks")
    private Boolean supportsTimescaleMarks;
    @JsonProperty("supports_time")
    private Boolean supportsTime;
    @JsonProperty("exchanges")
    private List<ExchangesDTO> exchanges;
    @JsonProperty("symbols_types")
    private List<SymbolsTypesDTO> symbolsTypes;
    @JsonProperty("supported_resolutions")
    private List<String> supportedResolutions;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ExchangesDTO {
        @JsonProperty("value")
        private String value;
        @JsonProperty("name")
        private String name;
        @JsonProperty("desc")
        private String desc;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SymbolsTypesDTO {
        @JsonProperty("name")
        private String name;
        @JsonProperty("value")
        private String value;
    }
}




