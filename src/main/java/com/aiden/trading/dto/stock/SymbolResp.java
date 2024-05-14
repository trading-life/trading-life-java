package com.aiden.trading.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SymbolResp {
    private String symbol;
    @JsonProperty("full_name")
    private String fullName;
    private String description;

    @JsonProperty("exchange")
    private String exchangeCode;
    private String type;
    private List<String> supported_resolutions;
}
