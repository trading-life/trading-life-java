package com.aiden.trading.dto.user.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoModelResp implements Serializable {
    // 用户id
    private Integer userId;
    // 用户名
    private String username;
    // 真实名字
    private String realName;
    // 头像
    private  String avatar;
    // 介绍
    private String  desc;
}
