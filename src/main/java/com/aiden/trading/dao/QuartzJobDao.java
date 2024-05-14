package com.aiden.trading.dao;

import com.aiden.trading.entity.QuartzJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 任务列表 Mapper 接口
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:22:44
 */
@Mapper
public interface QuartzJobDao extends BaseMapper<QuartzJob> {

}
