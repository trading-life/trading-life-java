package com.aiden.trading.dto.tradingview.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveOrUpdateChartsResp {

    @JsonProperty("status")
    private String status;

    @JsonProperty("id")
    private Integer id;

}
