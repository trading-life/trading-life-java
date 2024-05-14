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
 * @since 2024-04-09 22:19:16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("stock_group_item")
@Schema(name = "StockGroupItem", description = "股票分组")
public class StockGroupItem extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "代码")
    @TableField("group_code")
    private String groupCode;

    @Schema(description = "代码")
    @TableField("ticker")
    private String ticker;
}
