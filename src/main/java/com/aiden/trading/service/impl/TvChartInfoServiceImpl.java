package com.aiden.trading.service.impl;

import com.aiden.trading.dao.TvChartInfoDao;
import com.aiden.trading.dto.tradingview.ChartInfo;
import com.aiden.trading.dto.tradingview.req.SaveChartReq;
import com.aiden.trading.entity.TvChartInfo;
import com.aiden.trading.service.ITvChartInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 布局 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 21:05:46
 */
@Service
public class TvChartInfoServiceImpl extends ServiceImpl<TvChartInfoDao, TvChartInfo> implements ITvChartInfoService {

    @Override
    public ChartInfo getChartInfoById(Integer chart, String user, String client) {
        LambdaQueryWrapper<TvChartInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvChartInfo::getId, chart);
        queryWrapper.eq(TvChartInfo::getUser, user);
        queryWrapper.eq(TvChartInfo::getClient, client);
        TvChartInfo tvStudyTemplateInfo = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvStudyTemplateInfo)) {
            ChartInfo ret = new ChartInfo();
            ret.setName(tvStudyTemplateInfo.getName());
            ret.setContent(new String(tvStudyTemplateInfo.getContent()));
            ret.setId(tvStudyTemplateInfo.getId());
            ret.setTimestamp(tvStudyTemplateInfo.getTimestamp());
            return ret;
        }
        return null;
    }


    @Override
    public List<Map<String, Object>> getChartInfos(String user, String client) {
        LambdaQueryWrapper<TvChartInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(TvChartInfo::getId, TvChartInfo::getName, TvChartInfo::getSymbol, TvChartInfo::getResolution, TvChartInfo::getTimestamp);
        queryWrapper.eq(TvChartInfo::getUser, user);
        queryWrapper.eq(TvChartInfo::getClient, client);
        return baseMapper.selectMaps(queryWrapper);
    }

    @Transactional
    @Override
    public TvChartInfo saveChart(SaveChartReq saveChartReq) {
        if (Objects.nonNull(saveChartReq.getChart())) {
            LambdaQueryWrapper<TvChartInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TvChartInfo::getId, saveChartReq.getChart());
            queryWrapper.eq(TvChartInfo::getUser, saveChartReq.getUser());
            queryWrapper.eq(TvChartInfo::getClient, saveChartReq.getClient());
            TvChartInfo tvChartInfo = baseMapper.selectOne(queryWrapper);
            if (Objects.nonNull(tvChartInfo)) {
                tvChartInfo.setContent(saveChartReq.getContent().getBytes());
                tvChartInfo.setResolution(saveChartReq.getResolution());
                tvChartInfo.setSymbol(saveChartReq.getSymbol());
                tvChartInfo.setName(saveChartReq.getName());
                tvChartInfo.setClient(saveChartReq.getClient());
                tvChartInfo.setUser(saveChartReq.getUser());
                tvChartInfo.setTimestamp((int) (System.currentTimeMillis() / 1000));
                baseMapper.updateById(tvChartInfo);
                return tvChartInfo;
            }
        }
        TvChartInfo tvChartInfo = new TvChartInfo();
        tvChartInfo.setResolution(saveChartReq.getResolution());
        tvChartInfo.setSymbol(saveChartReq.getSymbol());
        tvChartInfo.setName(saveChartReq.getName());
        tvChartInfo.setClient(saveChartReq.getClient());
        tvChartInfo.setUser(saveChartReq.getUser());
        tvChartInfo.setTimestamp((int) (System.currentTimeMillis() / 1000));
        tvChartInfo.setContent(saveChartReq.getContent().getBytes());
        baseMapper.insert(tvChartInfo);
        return tvChartInfo;
    }

    @Override
    public void deleteChart(String client, String user, Integer chart) {
        LambdaQueryWrapper<TvChartInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvChartInfo::getId, chart);
        queryWrapper.eq(TvChartInfo::getUser, user);
        queryWrapper.eq(TvChartInfo::getClient, client);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public Boolean isFirst(String loginId, String clientId) {
        LambdaQueryWrapper<TvChartInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvChartInfo::getUser, loginId);
        queryWrapper.eq(TvChartInfo::getClient, clientId);
        TvChartInfo tvChartInfo = baseMapper.selectOne(queryWrapper);
        return !Objects.nonNull(tvChartInfo);
    }
}
