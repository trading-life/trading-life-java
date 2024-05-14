package com.aiden.trading.service;

import com.aiden.trading.dto.tradingview.ChartInfo;
import com.aiden.trading.dto.tradingview.req.SaveChartReq;
import com.aiden.trading.entity.TvChartInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 布局 服务类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 21:05:46
 */
public interface ITvChartInfoService extends IService<TvChartInfo> {

    ChartInfo getChartInfoById(Integer chart, String user, String client);

    List<Map<String, Object>> getChartInfos(String user, String client);

    TvChartInfo saveChart(SaveChartReq saveChartReq);

    void deleteChart(String client, String user, Integer chart);

    Boolean isFirst(String loginId, String clientId);
}
