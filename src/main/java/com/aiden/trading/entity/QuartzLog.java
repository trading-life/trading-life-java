package com.aiden.trading.entity;

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
 * 任务日志
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:28:43
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("quartz_log")
@Schema(name = "QuartzLog", description = "任务日志")
public class QuartzLog extends BaseEntity {

    @Schema(description = "任务日志id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "任务id")
    @TableField("job_id")
    private Integer jobId;

    @Schema(description = "SpringBean名称")
    @TableField("bean_name")
    private String beanName;

    @Schema(description = "执行参数")
    @TableField("params")
    private String params;

    @Schema(description = "任务状态：1成功，2失败")
    @TableField("state")
    private Byte state;

    @Schema(description = "失败信息")
    @TableField("`error`")
    private String error;

    @Schema(description = "耗时(单位：毫秒)")
    @TableField("times")
    private Integer times;
}
