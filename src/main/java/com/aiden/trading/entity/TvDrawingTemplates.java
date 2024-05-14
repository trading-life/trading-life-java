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
 * 画图工具模板
 * </p>
 *
 * @author zd
 * @since 2024-04-01 20:27:15
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tv_drawing_templates")
@Schema(name = "TvDrawingTemplates", description = "画图工具模板")
public class TvDrawingTemplates extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "name")
    @TableField("`name`")
    private String name;

    @Schema(description = "tool")
    @TableField("tool")
    private String tool;

    @Schema(description = "客户端")
    @TableField("`client`")
    private String client;

    @Schema(description = "用户id")
    @TableField("`user`")
    private String user;

    @Schema(description = "内容")
    @TableField("content")
    private byte[] content;
}
