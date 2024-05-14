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
 * 股票分组
 * </p>
 *
 * @author zd
 * @since 2024-04-09 22:18:58
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("stock_group")
@Schema(name = "StockGroup", description = "股票分组")
public class StockGroup extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "代码")
    @TableField("`name`")
    private String name;

    @Schema(description = "代码")
    @TableField("`code`")
    private String code;
}
