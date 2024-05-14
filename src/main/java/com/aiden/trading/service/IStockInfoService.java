package com.aiden.trading.service;

import com.aiden.trading.dto.AkResult;
import com.aiden.trading.dto.stock.SymbolResp;
import com.aiden.trading.dto.stock.req.DataDayWeekYearReq;
import com.aiden.trading.entity.StockInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 股票信息 服务类
 * </p>
 *
 * @author zd
 * @since 2024-04-05 14:10:59
 */
public interface IStockInfoService extends IService<StockInfo> {

    List<SymbolResp> searchSymbols(String userInput, String exchange, String symbolType, String ticker);

    AkResult<?> getDayWeekYearData(DataDayWeekYearReq dataDayWeekYearReq);

    void tradeDate();
}
