package com.aiden.trading.constant;

import lombok.Getter;


@Getter
public enum JobStateEnum {

    JOB_RUN(1, "运行"),
    JOB_STOP(2, "暂停"),
    JOB_DEL(3, "删除");

    private final Integer status;
    private final String desc;

    JobStateEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}