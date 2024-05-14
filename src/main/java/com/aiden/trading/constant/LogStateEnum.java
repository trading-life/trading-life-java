package com.aiden.trading.constant;

import lombok.Getter;


@Getter
public enum LogStateEnum {

    LOG_SUS((byte)1, "成功"),
    LOG_FAIL((byte)2, "失败");

    private final Byte status;
    private final String desc;

    LogStateEnum(Byte status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}