package com.aiden.trading.service;

import com.aiden.trading.dto.tradingview.req.SaveDrawingReq;
import com.aiden.trading.entity.TvDrawing;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 画图 服务类
 * </p>
 *
 * @author zd
 * @since 2024-04-04 15:58:50
 */
public interface ITvDrawingService extends IService<TvDrawing> {

    void saveDrawing(SaveDrawingReq saveDrawingReq);
}
