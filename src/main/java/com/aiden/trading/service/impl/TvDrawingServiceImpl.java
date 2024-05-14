package com.aiden.trading.service.impl;

import com.aiden.trading.dao.TvDrawingDao;
import com.aiden.trading.dto.tradingview.req.SaveDrawingReq;
import com.aiden.trading.entity.TvDrawing;
import com.aiden.trading.service.ITvDrawingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 画图 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-04-04 15:58:50
 */
@Service
public class TvDrawingServiceImpl extends ServiceImpl<TvDrawingDao, TvDrawing> implements ITvDrawingService {

    @Override
    public void saveDrawing(SaveDrawingReq saveDrawingReq) {
        LambdaQueryWrapper<TvDrawing> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvDrawing::getUser, saveDrawingReq.getUser());
        queryWrapper.eq(TvDrawing::getClient, saveDrawingReq.getClient());
        queryWrapper.eq(TvDrawing::getChart, saveDrawingReq.getChart());
        queryWrapper.eq(TvDrawing::getLayout, saveDrawingReq.getLayout());
        TvDrawing tvDrawing = baseMapper.selectOne(queryWrapper);
        if (Objects.nonNull(tvDrawing)) {
            tvDrawing.setState(saveDrawingReq.getState().getBytes());
            baseMapper.updateById(tvDrawing);
            return;
        }
        tvDrawing = new TvDrawing();
        BeanUtils.copyProperties(saveDrawingReq,tvDrawing);
        tvDrawing.setState(saveDrawingReq.getState().getBytes());
        baseMapper.insert(tvDrawing);
    }
}
