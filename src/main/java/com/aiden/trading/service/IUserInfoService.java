package com.aiden.trading.service;

import com.aiden.trading.dto.user.req.LoginReq;
import com.aiden.trading.dto.user.res.UserInfoModelResp;
import com.aiden.trading.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author zd
 * @since 2024-05-02 13:56:10
 */
public interface IUserInfoService extends IService<UserInfo> {

    List<UserInfo> getUserInfos(String username);

    UserInfo validUser(List<UserInfo> users, LoginReq loginReq);

    UserInfoModelResp getUserInfoModel(Integer loginId);
}
