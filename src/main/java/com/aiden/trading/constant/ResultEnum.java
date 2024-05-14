package com.aiden.trading.constant;

import lombok.Getter;

@Getter
public enum ResultEnum {

    CODE_SUCCESS(0, "成功"),
    CODE_ERROR(-1, "失败"),
    ;
    private final Integer code;
    private final String desc;

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
