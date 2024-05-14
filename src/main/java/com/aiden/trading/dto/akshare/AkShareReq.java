package com.aiden.trading.dto.akshare;

import lombok.Data;

import java.util.Map;

//{
//    "method":"fund_individual_basic_info_xq",
//    "args":{
//        "symbol":"000001"
//    }
//}

@Data
public class AkShareReq {
    private String method;
    private Map<String,Object> args;
}
