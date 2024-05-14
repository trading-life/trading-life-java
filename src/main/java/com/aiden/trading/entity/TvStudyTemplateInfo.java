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
 * 指标模板
 * </p>
 *
 * @author zd
 * @since 2024-03-30 20:15:44
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tv_study_template_info")
@Schema(name = "TvStudyTemplateInfo", description = "指标模板")
public class TvStudyTemplateInfo extends BaseEntity {

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
}
