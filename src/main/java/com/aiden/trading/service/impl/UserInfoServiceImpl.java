package com.aiden.trading.service.impl;

import com.aiden.trading.dao.UserInfoDao;
import com.aiden.trading.dto.user.req.LoginReq;
import com.aiden.trading.dto.user.res.UserInfoModelResp;
import com.aiden.trading.entity.UserInfo;
import com.aiden.trading.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-05-02 13:56:10
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements IUserInfoService {

    @Override
    public List<UserInfo> getUserInfos(String username) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public UserInfo validUser(List<UserInfo> users, LoginReq loginReq) {
        if (CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("用户不存在");
        }
        for (UserInfo userInfo : users) {
            if (Objects.equals(userInfo.getUsername(),loginReq.getUsername()) && Objects.equals(userInfo.getPassword(),loginReq.getPassword())) {
                return userInfo;
            }
        }
        throw new RuntimeException("用户校验不通过");
    }

    @Override
    public UserInfoModelResp getUserInfoModel(Integer loginId) {
        if (Objects.isNull(loginId)) {
            throw new RuntimeException("用户未登录");
        }
        UserInfo userInfo = baseMapper.selectById(loginId);
        if (Objects.isNull(userInfo)) {
            throw new RuntimeException("用户信息查不到");
        }
        UserInfoModelResp ret = new UserInfoModelResp();
        BeanUtils.copyProperties(userInfo,ret);
        ret.setUserId(userInfo.getId());
        return ret;
    }
}
