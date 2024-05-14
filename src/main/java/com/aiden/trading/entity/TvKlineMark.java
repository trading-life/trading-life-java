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
 * k线mark
 * </p>
 *
 * @author zd
 * @since 2024-03-30 22:17:00
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tv_kline_mark")
@Schema(name = "TvKlineMark", description = "k线mark")
public class TvKlineMark extends BaseEntity {

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "text")
    @TableField("`text`")
    private String text;

    @Schema(description = "label")
    @TableField("label")
    private String label;

    @Schema(description = "label_font_color")
    @TableField("label_font_color")
    private String labelFontColor;

    @Schema(description = "symbol")
    @TableField("symbol")
    private String symbol;

    @Schema(description = "resolution")
    @TableField("resolution")
    private String resolution;

    @Schema(description = "color")
    @TableField("color")
    private String color;

    @Schema(description = "min_size")
    @TableField("min_size")
    private Integer minSize;

    @Schema(description = "time")
    @TableField("`time`")
    private Integer time;
}
