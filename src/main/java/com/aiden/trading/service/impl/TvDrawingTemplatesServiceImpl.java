package com.aiden.trading.service.impl;

import com.aiden.trading.dao.TvDrawingTemplatesDao;
import com.aiden.trading.dto.tradingview.req.SaveDrawingTemplateReq;
import com.aiden.trading.dto.tradingview.resp.DrawingTemplatesInfo;
import com.aiden.trading.entity.TvDrawingTemplates;
import com.aiden.trading.service.ITvDrawingTemplatesService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 画图工具模板 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-04-01 20:27:15
 */
@Service
public class TvDrawingTemplatesServiceImpl extends ServiceImpl<TvDrawingTemplatesDao, TvDrawingTemplates> implements ITvDrawingTemplatesService {

    @Override
    public DrawingTemplatesInfo getInfoByToolAndName(String user, String client, String tool, String name) {
        LambdaQueryWrapper<TvDrawingTemplates> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvDrawingTemplates::getUser, user);
        queryWrapper.eq(TvDrawingTemplates::getClient, client);
        queryWrapper.eq(TvDrawingTemplates::getTool, tool);
        queryWrapper.eq(TvDrawingTemplates::getName, name);
        TvDrawingTemplates tvStudyTemplateInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvStudyTemplateInfo)) {
            DrawingTemplatesInfo ret = new DrawingTemplatesInfo();
            ret.setName(tvStudyTemplateInfo.getName());
            ret.setContent(new String(tvStudyTemplateInfo.getContent()));
            return ret;
        }
        return null;
    }

    @Override
    public List<String> getInfosByTool(String user, String client, String tool) {
        LambdaQueryWrapper<TvDrawingTemplates> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TvDrawingTemplates::getName);
        queryWrapper.eq(TvDrawingTemplates::getUser, user);
        queryWrapper.eq(TvDrawingTemplates::getClient, client);
        queryWrapper.eq(TvDrawingTemplates::getTool, tool);
        List<String> ret = new ArrayList<>();
        ret.add("");
        List<String> dbRets = baseMapper.selectObjs(queryWrapper);
        if (CollectionUtils.isNotEmpty(dbRets)) {
            ret.addAll(dbRets);
        }
        return ret;
    }

    @Override
    public void saveDrawingTemplate(SaveDrawingTemplateReq saveDrawingTemplateReq) {
        LambdaQueryWrapper<TvDrawingTemplates> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvDrawingTemplates::getUser, saveDrawingTemplateReq.getUser());
        queryWrapper.eq(TvDrawingTemplates::getClient, saveDrawingTemplateReq.getClient());
        queryWrapper.eq(TvDrawingTemplates::getTool, saveDrawingTemplateReq.getTool());
        queryWrapper.eq(TvDrawingTemplates::getName, saveDrawingTemplateReq.getName());
        TvDrawingTemplates tvStudyTemplateInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvStudyTemplateInfo)) {
            tvStudyTemplateInfo.setContent(saveDrawingTemplateReq.getContent().getBytes());
            baseMapper.updateById(tvStudyTemplateInfo);
            return;
        }
        tvStudyTemplateInfo = new TvDrawingTemplates();
        BeanUtils.copyProperties(saveDrawingTemplateReq,tvStudyTemplateInfo);
        tvStudyTemplateInfo.setContent(saveDrawingTemplateReq.getContent().getBytes());
        baseMapper.insert(tvStudyTemplateInfo);

    }

    @Override
    public void deleteByToolAndName(String user, String client, String tool, String name) {
        LambdaQueryWrapper<TvDrawingTemplates> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvDrawingTemplates::getUser, user);
        queryWrapper.eq(TvDrawingTemplates::getClient,client);
        queryWrapper.eq(TvDrawingTemplates::getTool, tool);
        queryWrapper.eq(TvDrawingTemplates::getName, name);
        baseMapper.delete(queryWrapper);
    }
}
