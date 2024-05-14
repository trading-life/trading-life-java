package com.aiden.trading.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.aiden.trading.dto.Result;
import com.aiden.trading.dto.user.res.UserInfoModelResp;
import com.aiden.trading.service.IUserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author zd
 * @since 2024-05-02 13:56:10
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private IUserInfoService userInfoService;

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @GetMapping("getUserInfo")
    public Result<UserInfoModelResp> getUserInfo() {
        UserInfoModelResp ret = userInfoService.getUserInfoModel(Integer.valueOf((String) StpUtil.getLoginId()));
        return Result.data(ret);
    }
}
