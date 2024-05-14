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
 * 股票成分
 * </p>
 *
 * @author zd
 * @since 2024-04-27 14:19:52
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("stock_gn_item")
@Schema(name = "StockGnItem", description = "股票成分")
public class StockGnItem extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("gn_id")
    private Integer gnId;

    @TableField("stock_id")
    private Integer stockId;
}
