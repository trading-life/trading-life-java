package com.aiden.trading.service.impl;

import com.aiden.trading.dao.TvKlineMarkDao;
import com.aiden.trading.dto.tradingview.resp.MarksResp;
import com.aiden.trading.entity.TvKlineMark;
import com.aiden.trading.service.ITvKlineMarkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * k线mark 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 22:17:00
 */
@Service
public class TvKlineMarkServiceImpl extends ServiceImpl<TvKlineMarkDao, TvKlineMark> implements ITvKlineMarkService {

    @Override
    public MarksResp selectKlineMark(String symbol, String resolution, Long from, Long to) {
        LambdaQueryWrapper<TvKlineMark> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TvKlineMark::getSymbol, symbol);
        queryWrapper.eq(TvKlineMark::getResolution, resolution);
        queryWrapper.between(TvKlineMark::getTime, from,to);
        List<TvKlineMark> klineMarks = baseMapper.selectList(queryWrapper);
        MarksResp ret = new MarksResp();
        List<Integer> ids = new ArrayList<>();
        ret.setId(ids);
        List<Integer> times = new ArrayList<>();
        ret.setTime(times);
        List<String> colors = new ArrayList<>();
        ret.setColor(colors);
        List<String> texts = new ArrayList<>();
        ret.setText(texts);
        List<String> labels = new ArrayList<>();
        ret.setLabel(labels);
        List<String> labelFontColors = new ArrayList<>();
        ret.setLabelFontColor(labelFontColors);
        List<Integer> minSizes = new ArrayList<>();
        ret.setMinSize(minSizes);
        if (CollectionUtils.isNotEmpty(klineMarks)) {
            for (TvKlineMark klineMark : klineMarks) {
                ids.add(klineMark.getId());
                times.add(klineMark.getTime());
                colors.add(klineMark.getColor());
                texts.add(klineMark.getText());
                labels.add(klineMark.getLabel());
                labelFontColors.add(klineMark.getLabelFontColor());
                minSizes.add(klineMark.getMinSize());
            }
        }
        return ret;
    }
}
