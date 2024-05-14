package com.aiden.trading.dao;

import com.aiden.trading.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author zd
 * @since 2024-05-02 13:56:10
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

}
