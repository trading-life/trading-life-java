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
 * 任务列表
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:22:44
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("quartz_job")
@Schema(name = "QuartzJob", description = "任务列表")
public class QuartzJob extends BaseEntity {

    @Schema(description = "任务id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "SpringBean名称")
    @TableField("bean_name")
    private String beanName;

    @Schema(description = "执行参数")
    @TableField("params")
    private String params;

    @Schema(description = "cron表达式")
    @TableField("cron_expres")
    private String cronExpres;

    @Schema(description = "任务状态：1正常，2暂停，3删除")
    @TableField("state")
    private Integer state;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
