package com.aiden.trading.service;

import com.aiden.trading.dto.PageReq;
import com.aiden.trading.dto.PageResp;
import com.aiden.trading.entity.QuartzJob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务列表 服务类
 * </p>
 *
 * @author zd
 * @since 2024-03-30 10:22:44
 */
public interface IQuartzJobService extends IService<QuartzJob> {
    QuartzJob getById(Integer id);

    int insert(QuartzJob quartzJob);

    int update(QuartzJob quartzJob);

    void pause(Integer id);

    void resume(Integer id);
    void runOnce(Integer id);

    PageResp<QuartzJob> pageList(PageReq pageReq);
}
