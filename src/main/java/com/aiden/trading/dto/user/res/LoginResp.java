package com.aiden.trading.dto.user.res;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginResp implements Serializable {
    // 用户id
    private Integer userId;
    // 用户名
    private String token;
    //角色
    private List<?> roles;
}
