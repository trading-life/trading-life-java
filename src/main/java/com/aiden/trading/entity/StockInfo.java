package com.aiden.trading.entity;

import com.aiden.trading.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 股票信息
 * </p>
 *
 * @author zd
 * @since 2024-04-05 14:10:59
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("stock_info")
@Schema(name = "StockInfo", description = "股票信息")
public class StockInfo extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "代码")
    @TableField("`code`")
    private String code;

    @Schema(description = "交易所")
    @TableField("`exchange`")
    private String exchange;

    @Schema(description = "交易所代码")
    @TableField("exchange_code")
    private String exchangeCode;

    @Schema(description = "币种")
    @TableField("currency")
    private String currency;

    @Schema(description = "名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "上市日期")
    @TableField("list_date")
    private LocalDateTime listDate;

    @TableField("ths_code")
    private String thsCode;
}
