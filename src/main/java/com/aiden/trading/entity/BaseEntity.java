package com.aiden.trading.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "更新时间")
    private Date updateTime;
}
