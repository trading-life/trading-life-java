package com.aiden.trading.config;

import com.aiden.trading.dto.AkResult;
import com.aiden.trading.dto.akshare.AkShareReq;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface AKshareApi {

    @PostExchange("/ak/method")
    AkResult<?> pyMethod(@RequestBody AkShareReq akShareReq);
}
