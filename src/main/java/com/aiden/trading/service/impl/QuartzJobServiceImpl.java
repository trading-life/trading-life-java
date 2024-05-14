package com.aiden.trading.service.impl;

import com.aiden.trading.constant.JobStateEnum;
import com.aiden.trading.dao.QuartzJobDao;
import com.aiden.trading.dao.QuartzLogDao;
import com.aiden.trading.dto.PageReq;
import com.aiden.trading.dto.PageResp;
import com.aiden.trading.entity.QuartzJob;
import com.aiden.trading.scheduler.QuartzManage;
import com.aiden.trading.service.IQuartzJobService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.quartz.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 任务列表 服务实现类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:22:44
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobDao, QuartzJob> implements IQuartzJobService {

    @Resource
    private QuartzJobDao quartzJobDao;
    @Resource
    private QuartzLogDao quartzLogDao;

    @Resource
    private QuartzManage quartzManage;

    /**
     * 初始化加载定时任务
     */
    @PostConstruct
    public void init() {
        LambdaQueryWrapper<QuartzJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(QuartzJob::getState, JobStateEnum.JOB_RUN.getStatus(), JobStateEnum.JOB_STOP.getStatus());
        List<QuartzJob> jobList = quartzJobDao.selectList(queryWrapper);
        jobList.forEach(quartzJob -> {
            CronTrigger cronTrigger = quartzManage.getCronTrigger(quartzJob.getId());
            if (Objects.isNull(cronTrigger)) {
                quartzManage.createJob(quartzJob);
            } else {
                quartzManage.updateJob(quartzJob);
            }
        });
    }

    /**
     * 任务主键查询
     */
    public QuartzJob getById(Integer id) {
        return quartzJobDao.selectById(id);
    }

    /**
     * 新增任务
     */
    @Transactional
    public int insert(QuartzJob quartzJob) {
        int flag = quartzJobDao.insert(quartzJob);
        if (flag > 0) {
            quartzManage.createJob(quartzJob);
        }
        return flag;
    }

    /**
     * 更新任务
     */
    public int update(QuartzJob quartzJob) {
        int flag = quartzJobDao.updateById(quartzJob);
        if (flag > 0) {
            quartzManage.updateJob(quartzJob);
        }
        return flag;
    }

    /**
     * 暂停任务
     */
    public void pause(Integer id) {
        QuartzJob quartzJob = quartzJobDao.selectById(id);
        if (!Objects.isNull(quartzJob)) {
            quartzJob.setState(JobStateEnum.JOB_STOP.getStatus());
            if (quartzJobDao.updateById(quartzJob) > 0) {
                quartzManage.checkStop(quartzJob);
            }
        }
    }

    /**
     * 恢复任务
     */
    public void resume(Integer id) {
        QuartzJob quartzJob = quartzJobDao.selectById(id);
        if (!Objects.isNull(quartzJob)) {
            quartzJob.setState(JobStateEnum.JOB_RUN.getStatus());
            if (quartzJobDao.updateById(quartzJob) > 0) {
                quartzManage.resumeJob(id);
            }
        }
    }

    /**
     * 执行任务一次
     */
    public void runOnce(Integer id) {
        QuartzJob quartzJob = quartzJobDao.selectById(id);
        if (!Objects.isNull(quartzJob) && quartzJob.getState() != JobStateEnum.JOB_DEL.getStatus()) {
            quartzManage.run(quartzJob);
        }
    }

    @Override
    public PageResp<QuartzJob> pageList(PageReq pageReq) {
        PageHelper.startPage(pageReq.getPage(), pageReq.getPageSize());
        LambdaQueryWrapper<QuartzJob> queryWrapper = new LambdaQueryWrapper<>();
        List<QuartzJob> list = baseMapper.selectList(queryWrapper);
        PageInfo<QuartzJob> ret = new PageInfo<>(list) ;
        return new PageResp<>(ret.getList(),ret.getTotal());

    }
}
