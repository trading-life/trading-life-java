package com.aiden.trading.entity;

import com.aiden.trading.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 布局
 * </p>
 *
 * @author zd
 * @since 2024-03-30 21:22:00
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tv_chart_info")
@Schema(name = "TvChartInfo", description = "布局")
public class TvChartInfo extends BaseEntity {

    @Schema(description = "模板id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "内容")
    @TableField("content")
    private byte[] content;

    @Schema(description = "客户端")
    @TableField("`client`")
    private String client;

    @Schema(description = "用户id")
    @TableField("`user`")
    private String user;

    @Schema(description = "symbol")
    @TableField("symbol")
    private String symbol;

    @Schema(description = "timestamp")
    @TableField("`timestamp`")
    private Integer timestamp;

    @Schema(description = "resolution")
    @TableField("resolution")
    private String resolution;
}
