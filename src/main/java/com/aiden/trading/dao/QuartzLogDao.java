package com.aiden.trading.dao;

import com.aiden.trading.entity.QuartzLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 任务日志 Mapper 接口
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:28:43
 */
@Mapper
public interface QuartzLogDao extends BaseMapper<QuartzLog> {

}
