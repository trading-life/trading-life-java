package com.aiden.trading.service;

import com.aiden.trading.dto.tradingview.StudyTemplateInfo;
import com.aiden.trading.dto.tradingview.req.SaveStudyTemplateReq;
import com.aiden.trading.entity.TvStudyTemplateInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 指标模板 服务类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 19:51:38
 */
public interface ITvStudyTemplateInfoService extends IService<TvStudyTemplateInfo> {

    StudyTemplateInfo getByName(String templateName,String user,String client);

    List<Map<String,Object>> getStudyTemplateNames(String user, String client);

    void saveStudyTemplate(SaveStudyTemplateReq saveStudyTemplateReq);

    void deleteStudyTemplateByName(String client, String user, String template);
}
