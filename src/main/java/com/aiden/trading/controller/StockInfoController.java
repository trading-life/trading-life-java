package com.aiden.trading.controller;

import com.aiden.trading.dto.AkResult;
import com.aiden.trading.dto.Result;
import com.aiden.trading.dto.stock.SymbolResp;
import com.aiden.trading.dto.stock.req.DataDayWeekYearReq;
import com.aiden.trading.service.IStockInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 股票信息 前端控制器
 * </p>
 *
 * @author zd
 * @since 2024-04-05 14:10:59
 */
@RestController
@RequestMapping("/stock-info")
public class StockInfoController {

    @Resource
    private IStockInfoService stockInfoService;
    @GetMapping("/searchSymbols")
    public Result<List<SymbolResp>> searchSymbols(@RequestParam(value = "userInput",required = false) String userInput,
                                                  @RequestParam(value = "ticker",required = false) String ticker,
                                                  @RequestParam(value = "exchange",required = false) String exchange,
                                                  @RequestParam(value = "symbolType",required = false) String symbolType) {
        List<SymbolResp> ret = stockInfoService.searchSymbols(userInput,exchange,symbolType,ticker);
        return Result.data(ret);
    }
    @PostMapping("/getDayWeekYearData")
    public Result<?> getDayWeekYearData(@RequestBody DataDayWeekYearReq dataDayWeekYearReq) {
        AkResult<?> ret = stockInfoService.getDayWeekYearData(dataDayWeekYearReq);
        if (Objects.nonNull(ret) && Objects.equals(0,ret.getCode())) {
            return Result.data(ret.getData());
        }
        return Result.ok();
    }
    @PostMapping("/tradeDate")
    public Result<?> tradeDate() {
        stockInfoService.tradeDate();
        return Result.ok();
    }

}
