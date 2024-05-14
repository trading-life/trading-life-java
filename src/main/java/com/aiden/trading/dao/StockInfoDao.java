package com.aiden.trading.dao;

import com.aiden.trading.entity.StockInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 股票信息 Mapper 接口
 * </p>
 *
 * @author zd
 * @since 2024-04-05 14:10:59
 */
@Mapper
public interface StockInfoDao extends BaseMapper<StockInfo> {

}
