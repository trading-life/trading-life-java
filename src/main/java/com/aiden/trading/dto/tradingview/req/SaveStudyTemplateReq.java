package com.aiden.trading.dto.tradingview.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SaveStudyTemplateReq {

    private String client;
    private String user;
    private String name;
    private String content;


}
