package com.aiden.trading.service.impl;

import com.aiden.trading.entity.QuartzLog;
import com.aiden.trading.dao.QuartzLogDao;
import com.aiden.trading.service.IQuartzLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务日志 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:28:43
 */
@Service("quartzLogService")
public class QuartzLogServiceImpl extends ServiceImpl<QuartzLogDao, QuartzLog> implements IQuartzLogService {

    @Resource
    private QuartzLogDao quartzLogDao;

    public Integer insert(QuartzLog quartzLog) {
        return quartzLogDao.insert(quartzLog);
    }
}
