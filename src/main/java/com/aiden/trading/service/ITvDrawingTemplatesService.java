package com.aiden.trading.service;

import com.aiden.trading.dto.tradingview.req.SaveDrawingTemplateReq;
import com.aiden.trading.dto.tradingview.resp.DrawingTemplatesInfo;
import com.aiden.trading.entity.TvDrawingTemplates;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 画图工具模板 服务类
 * </p>
 *
 * @author zd
 * @since 2024-04-01 20:27:15
 */
public interface ITvDrawingTemplatesService extends IService<TvDrawingTemplates> {

    DrawingTemplatesInfo getInfoByToolAndName(String user, String client, String tool, String name);

    List<String> getInfosByTool(String user, String client, String tool);

    void saveDrawingTemplate(SaveDrawingTemplateReq saveDrawingTemplateReq);

    void deleteByToolAndName(String user, String client, String tool, String name);
}
