package com.aiden.trading.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.aiden.trading.dto.Result;
import com.aiden.trading.dto.user.req.LoginReq;
import com.aiden.trading.dto.user.res.UserInfoModelResp;
import com.aiden.trading.dto.user.res.LoginResp;
import com.aiden.trading.entity.UserInfo;
import com.aiden.trading.service.IUserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "认证")
public class UserAuthController {

    @Resource
    private IUserInfoService userInfoService;

    @PostMapping("login")
    public Result<LoginResp> doLogin(@RequestBody LoginReq loginReq) {
        List<UserInfo> users = userInfoService.getUserInfos(loginReq.getUsername());
        UserInfo loginUser = userInfoService.validUser(users, loginReq);
        StpUtil.login(loginUser.getId());
        LoginResp tokenInfo = new LoginResp();
        tokenInfo.setToken(StpUtil.getTokenValue());
        return Result.data(tokenInfo);
    }


    @GetMapping("logout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.ok();
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @GetMapping("getUserInfo")
    public Result<UserInfoModelResp> getUserInfo() {
        UserInfoModelResp ret = userInfoService.getUserInfoModel(Integer.valueOf((String) StpUtil.getLoginId()));
        return Result.data(ret);
    }
}
