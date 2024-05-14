package com.aiden.trading.controller;

import com.aiden.trading.dto.PageReq;
import com.aiden.trading.dto.PageResp;
import com.aiden.trading.dto.Result;
import com.aiden.trading.entity.StockGn;
import com.aiden.trading.service.IStockGnService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 股票概念 前端控制器
 * </p>
 *
 * @author zd
 * @since 2024-05-02 08:45:00
 */
@RestController
@RequestMapping("/stockGn")
public class StockGnController {

    @Resource
    private IStockGnService stockGnService;
    @Operation(summary = "任务查询")
    @PostMapping("/page")
    public Result<PageResp<StockGn>> page(@RequestBody PageReq pageReq) {
        PageResp<StockGn> ret = stockGnService.pageList(pageReq);
        return Result.data(ret);
    }

}
