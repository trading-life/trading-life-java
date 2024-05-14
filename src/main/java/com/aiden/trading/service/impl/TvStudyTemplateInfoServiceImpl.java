package com.aiden.trading.service.impl;

import com.aiden.trading.dao.TvStudyTemplateInfoDao;
import com.aiden.trading.dto.tradingview.StudyTemplateInfo;
import com.aiden.trading.dto.tradingview.req.SaveStudyTemplateReq;
import com.aiden.trading.entity.TvStudyTemplateInfo;
import com.aiden.trading.service.ITvStudyTemplateInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 指标模板 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 19:51:38
 */
@Service
public class TvStudyTemplateInfoServiceImpl extends ServiceImpl<TvStudyTemplateInfoDao, TvStudyTemplateInfo> implements ITvStudyTemplateInfoService {

    @Override
    public StudyTemplateInfo getByName(String templateName,String user,String client) {
        LambdaQueryWrapper<TvStudyTemplateInfo> queryWrapper = new LambdaQueryWrapper<>() ;
        queryWrapper.eq(TvStudyTemplateInfo::getName, templateName);
        queryWrapper.eq(TvStudyTemplateInfo::getUser, user);
        queryWrapper.eq(TvStudyTemplateInfo::getClient, client);
        TvStudyTemplateInfo tvStudyTemplateInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvStudyTemplateInfo)) {
            StudyTemplateInfo ret = new StudyTemplateInfo();
            ret.setName(tvStudyTemplateInfo.getName());
            ret.setContent(new String(tvStudyTemplateInfo.getContent()));
            return ret;
        }
        return null;
    }

    @Override
    public List<Map<String,Object>> getStudyTemplateNames(String user, String client) {
        LambdaQueryWrapper<TvStudyTemplateInfo> queryWrapper = new LambdaQueryWrapper<>() ;
        queryWrapper.select(TvStudyTemplateInfo::getName);
        queryWrapper.eq(TvStudyTemplateInfo::getUser, user);
        queryWrapper.eq(TvStudyTemplateInfo::getClient, client);
        return baseMapper.selectMaps(queryWrapper);
    }

    @Transactional
    @Override
    public void saveStudyTemplate(SaveStudyTemplateReq saveStudyTemplateReq) {
        LambdaQueryWrapper<TvStudyTemplateInfo> queryWrapper = new LambdaQueryWrapper<>() ;
        queryWrapper.eq(TvStudyTemplateInfo::getName, saveStudyTemplateReq.getName());
        queryWrapper.eq(TvStudyTemplateInfo::getUser, saveStudyTemplateReq.getUser());
        queryWrapper.eq(TvStudyTemplateInfo::getClient, saveStudyTemplateReq.getClient());
        TvStudyTemplateInfo tvStudyTemplateInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvStudyTemplateInfo)) {
            tvStudyTemplateInfo.setContent(saveStudyTemplateReq.getContent().getBytes());
            baseMapper.updateById(tvStudyTemplateInfo);
            return;
        }
        tvStudyTemplateInfo = new TvStudyTemplateInfo();
        tvStudyTemplateInfo.setClient(saveStudyTemplateReq.getClient());
        tvStudyTemplateInfo.setUser(saveStudyTemplateReq.getUser());
        tvStudyTemplateInfo.setName(saveStudyTemplateReq.getName());
        tvStudyTemplateInfo.setContent(saveStudyTemplateReq.getContent().getBytes());
        baseMapper.insert(tvStudyTemplateInfo);
    }

    @Override
    public void deleteStudyTemplateByName(String client, String user, String template) {
        LambdaQueryWrapper<TvStudyTemplateInfo> queryWrapper = new LambdaQueryWrapper<>() ;
        queryWrapper.eq(TvStudyTemplateInfo::getName, template);
        queryWrapper.eq(TvStudyTemplateInfo::getUser,user);
        queryWrapper.eq(TvStudyTemplateInfo::getClient, client);
        baseMapper.delete(queryWrapper);
    }
}
