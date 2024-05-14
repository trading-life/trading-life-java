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
 * 用户
 * </p>
 *
 * @author zd
 * @since 2024-05-02 13:56:10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_info")
@Schema(name = "UserInfo", description = "用户")
public class UserInfo extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    @TableField("username")
    private String username;

    @Schema(description = "用户名")
    @TableField("`password`")
    private String password;

    @Schema(description = "真实名字")
    @TableField("realName")
    private String realName;

    @Schema(description = "头像")
    @TableField("avatar")
    private String avatar;

    @Schema(description = "介绍")
    @TableField("`desc`")
    private String desc;
}
