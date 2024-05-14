package com.aiden.trading.service;

import com.aiden.trading.dto.PageReq;
import com.aiden.trading.dto.PageResp;
import com.aiden.trading.entity.StockGn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 股票概念 服务类
 * </p>
 *
 * @author zd
 * @since 2024-05-02 08:45:00
 */
public interface IStockGnService extends IService<StockGn> {

    PageResp<StockGn> pageList(PageReq pageReq);
}
