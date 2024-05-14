package com.aiden.trading.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.aiden.trading.dto.Result;
import com.aiden.trading.dto.user.req.LoginReq;
import com.aiden.trading.entity.UserInfo;
import com.aiden.trading.service.IUserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证")
public class UserAuthController {

    @Resource
    private IUserInfoService userInfoService;

    @PostMapping("doLogin")
    public Result<?> doLogin(@RequestBody LoginReq loginReq) {
        List<UserInfo> users = userInfoService.getUserInfos(loginReq.getUsername());
        UserInfo loginUser = userInfoService.validUser(users, loginReq);
        StpUtil.login(loginUser.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return Result.data(tokenInfo);
    }

    @GetMapping("isLoginSystem")
    public Result<Boolean> isLoginSystem() {
        return Result.data(StpUtil.isLogin());
    }

    @GetMapping("doLogout")
    public Result<?> logout() {
        StpUtil.logout();
        return Result.ok();
    }

}
