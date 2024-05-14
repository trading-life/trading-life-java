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
 * 画图
 * </p>
 *
 * @author zd
 * @since 2024-04-04 15:58:50
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tv_drawing")
@Schema(name = "TvDrawing", description = "画图")
public class TvDrawing extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "layout")
    @TableField("layout")
    private String layout;

    @Schema(description = "chart")
    @TableField("chart")
    private String chart;

    @Schema(description = "客户端")
    @TableField("`client`")
    private String client;

    @Schema(description = "用户id")
    @TableField("`user`")
    private String user;

    @Schema(description = "state")
    @TableField("state")
    private byte[] state;
}
