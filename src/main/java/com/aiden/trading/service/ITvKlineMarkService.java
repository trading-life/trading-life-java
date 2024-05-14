package com.aiden.trading.service;

import com.aiden.trading.dto.tradingview.resp.MarksResp;
import com.aiden.trading.entity.TvKlineMark;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * k线mark 服务类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 22:17:00
 */
public interface ITvKlineMarkService extends IService<TvKlineMark> {

    MarksResp selectKlineMark(String symbol, String resolution, Long from, Long to);
}
