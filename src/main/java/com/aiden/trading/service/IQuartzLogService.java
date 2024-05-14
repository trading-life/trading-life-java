package com.aiden.trading.service;

import com.aiden.trading.entity.QuartzLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务日志 服务类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:28:43
 */
public interface IQuartzLogService extends IService<QuartzLog> {
    Integer insert(QuartzLog quartzLog);
}
