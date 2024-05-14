package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SymbolInfoResp {
    @JsonProperty("name")
    private String name;
    @JsonProperty("exchange-traded")
    private String exchangetraded;
    @JsonProperty("exchange-listed")
    private String exchangelisted;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("minmov")
    private Integer minmov;
    @JsonProperty("minmov2")
    private Integer minmov2;
    @JsonProperty("pointvalue")
    private Integer pointvalue;
    @JsonProperty("session")
    private String session;
    @JsonProperty("has_intraday")
    private Boolean hasIntraday;
    @JsonProperty("visible_plots_set")
    private String visiblePlotsSet;
    @JsonProperty("description")
    private String description;
    @JsonProperty("type")
    private String type;
    @JsonProperty("supported_resolutions")
    private List<String> supportedResolutions;
    @JsonProperty("pricescale")
    private Integer pricescale;
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("logo_urls")
    private List<String> logoUrls;
    @JsonProperty("exchange_logo")
    private String exchangeLogo;
}
