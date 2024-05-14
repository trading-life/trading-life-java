package com.aiden.trading.service.impl;

import com.aiden.trading.dao.StockGnDao;
import com.aiden.trading.dto.PageReq;
import com.aiden.trading.dto.PageResp;
import com.aiden.trading.entity.StockGn;
import com.aiden.trading.service.IStockGnService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 股票概念 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-05-02 08:45:00
 */
@Service
public class StockGnServiceImpl extends ServiceImpl<StockGnDao, StockGn> implements IStockGnService {

    @Override
    public PageResp<StockGn> pageList(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        LambdaQueryWrapper<StockGn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(StockGn::getListDate);
        List<StockGn> list = baseMapper.selectList(queryWrapper);
        PageInfo<StockGn> ret = new PageInfo<>(list) ;
        return new PageResp<>(ret.getList(),ret.getTotal());
    }
}
