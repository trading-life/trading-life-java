package com.aiden.trading.scheduler;

import com.aiden.trading.constant.LogStateEnum;
import com.aiden.trading.constant.Quartz;
import com.aiden.trading.entity.QuartzJob;
import com.aiden.trading.entity.QuartzLog;
import com.aiden.trading.service.IQuartzLogService;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Date;


public class QuartzRecord extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob)context.getMergedJobDataMap().get(Quartz.JOB_PARAM_KEY) ;
        IQuartzLogService quartzLogService = (IQuartzLogService)SpringContextUtil.getBean("quartzLogService") ;
        // 定时器日志记录
        QuartzLog quartzLog = new QuartzLog () ;
        quartzLog.setJobId(quartzJob.getId());
        quartzLog.setBeanName(quartzJob.getBeanName());
        quartzLog.setParams(quartzJob.getParams());
        quartzLog.setCreateTime(new Date());
        long beginTime = System.currentTimeMillis() ;
        try {
            // 加载并执行
            Object target = SpringContextUtil.getBean(quartzJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, quartzJob.getParams());
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzLog.setTimes((int)executeTime);
            quartzLog.setState(LogStateEnum.LOG_SUS.getStatus());
        } catch (Exception e){
            // 异常信息
            long executeTime = System.currentTimeMillis() - beginTime;
            quartzLog.setTimes((int)executeTime);
            quartzLog.setState(LogStateEnum.LOG_FAIL.getStatus());
            quartzLog.setError(e.getMessage());
        } finally {
            // 保存执行日志
            quartzLogService.insert(quartzLog) ;
        }
    }
}