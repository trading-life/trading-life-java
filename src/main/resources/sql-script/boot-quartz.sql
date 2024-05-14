
alter  table stock_gn add column
  `event` varchar(300) DEFAULT NULL COMMENT '事件';

drop table if exists `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '用户名',
  `realName` varchar(50) DEFAULT NULL COMMENT '真实名字',
  `avatar` varchar(50) DEFAULT NULL COMMENT '头像',
  `desc` varchar(50) DEFAULT NULL COMMENT '介绍',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

INSERT INTO trading.user_info
(id, username, password, realName, avatar, `desc`, create_time, update_time)
VALUES(1, 'vben', '123456', 'vben', NULL, 'vben', NULL, NULL);

drop table if exists `stock_gn`;
CREATE TABLE `stock_gn` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `code` varchar(50) DEFAULT NULL COMMENT '代码',
  `ex_code` varchar(50) DEFAULT NULL COMMENT '代码',
  `source` varchar(50) DEFAULT NULL COMMENT '代码',
  `url` varchar(300) DEFAULT NULL COMMENT '代码',
  `event_url` varchar(300) DEFAULT NULL COMMENT '代码',
  `list_date` datetime DEFAULT NULL COMMENT '上市日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票概念';


drop table if exists `stock_gn_item`;
CREATE TABLE `stock_gn_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `gn_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票成分';

drop table if exists `stock_group`;
CREATE TABLE `stock_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '代码',
  `code` varchar(50) DEFAULT NULL COMMENT '代码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票分组';

drop table if exists `stock_group_item`;
CREATE TABLE `stock_group_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_code` varchar(50) DEFAULT NULL COMMENT '代码',
  `ticker` varchar(50) DEFAULT NULL COMMENT '代码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票分组';

drop table if exists `stock_info`;
CREATE TABLE `stock_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(50) DEFAULT NULL COMMENT '代码',
  `ths_code` varchar(50) DEFAULT NULL COMMENT '代码',
  `exchange` varchar(50) DEFAULT NULL COMMENT '交易所',
  `exchange_code` varchar(50) DEFAULT NULL COMMENT '交易所代码',
  `currency` varchar(50) DEFAULT NULL COMMENT '币种',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `list_date` datetime DEFAULT NULL COMMENT '上市日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票信息';

drop table if exists `tv_drawing`;
CREATE TABLE `tv_drawing` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `layout` varchar(50) DEFAULT NULL COMMENT 'layout',
  `chart` varchar(50) DEFAULT NULL COMMENT 'chart',
  `client` varchar(20) DEFAULT NULL COMMENT '客户端',
  `user` varchar(20) DEFAULT NULL COMMENT '用户id',
  `state` blob DEFAULT NULL COMMENT 'state',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='画图';

drop table if exists `tv_drawing_templates`;
CREATE TABLE `tv_drawing_templates` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT 'name',
  `tool` varchar(50) DEFAULT NULL COMMENT 'tool',
  `client` varchar(20) DEFAULT NULL COMMENT '客户端',
  `user` varchar(20) DEFAULT NULL COMMENT '用户id',
  `content` blob DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='画图工具模板';

drop table if exists `tv_kline_mark`;
CREATE TABLE `tv_kline_mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `text` varchar(50) DEFAULT NULL COMMENT 'text',
  `label` varchar(50) DEFAULT NULL COMMENT 'label',
  `label_font_color` varchar(20) DEFAULT NULL COMMENT 'label_font_color',
  `symbol` varchar(20) DEFAULT NULL COMMENT 'symbol',
  `resolution` varchar(20) DEFAULT NULL COMMENT 'resolution',
  `color` varchar(20) DEFAULT NULL COMMENT 'color',
  `min_size` int(11) DEFAULT NULL COMMENT 'min_size',
  `time` int(11) DEFAULT NULL COMMENT 'time',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='k线mark';



drop table if exists `tv_chart_info`;
CREATE TABLE `tv_chart_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `content` longblob DEFAULT NULL COMMENT '内容',
  `client` varchar(20) DEFAULT NULL COMMENT '客户端',
  `user` varchar(20) DEFAULT NULL COMMENT '用户id',
  `symbol` varchar(20) DEFAULT NULL COMMENT 'symbol',
  `timestamp` int(11) DEFAULT NULL COMMENT 'timestamp',
  `resolution` varchar(5) DEFAULT NULL COMMENT 'resolution',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布局';


drop table if exists `tv_study_template_info`;
CREATE TABLE `tv_study_template_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `content` blob DEFAULT NULL COMMENT '内容',
  `client` varchar(20) DEFAULT NULL COMMENT '客户端',
  `user` varchar(20) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标模板';

CREATE TABLE `quartz_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'SpringBean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '执行参数',
  `cron_expres` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `state` int(1) DEFAULT NULL COMMENT '任务状态：1正常，2暂停，3删除',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务列表';

CREATE TABLE `quartz_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` int(11) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'SpringBean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '执行参数',
  `state` tinyint(4) NOT NULL COMMENT '任务状态：1成功，2失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务日志';

INSERT INTO `quartz_job` (`id`, `bean_name`, `params`, `cron_expres`, `state`, `remark`, `create_time`) VALUES (1, 'printJob', 'printJob-params', '0 /2 * * * ? *', 2, '打印信息任务', '2023-07-26 13:17:17');
INSERT INTO `quartz_job` (`id`, `bean_name`, `params`, `cron_expres`, `state`, `remark`, `create_time`) VALUES (2, 'timerJob', 'timerJob-params-update', '0 /3 * * * ? *', 2, '时间输出任务', '2023-07-26 14:17:17');


#
# In your Quartz properties file, you'll need to set
# org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#
#
# By: Ron Cordell - roncordell
#  I didn't see this anywhere, so I thought I'd post it here. This is the script from Quartz to create the tables in a MySQL database, modified to use INNODB instead of MYISAM.

drop table if exists qrtz_fired_triggers;
drop table if exists qrtz_paused_trigger_grps;
drop table if exists qrtz_scheduler_state;
drop table if exists qrtz_locks;
drop table if exists qrtz_simple_triggers;
drop table if exists qrtz_simprop_triggers;
drop table if exists qrtz_cron_triggers;
drop table if exists qrtz_blob_triggers;
drop table if exists qrtz_triggers;
drop table if exists qrtz_job_details;
drop table if exists qrtz_calendars;

create table qrtz_job_details(
    sched_name varchar(120) not null,
    job_name varchar(190) not null,
    job_group varchar(190) not null,
    description varchar(250) null,
    job_class_name varchar(250) not null,
    is_durable varchar(1) not null,
    is_nonconcurrent varchar(1) not null,
    is_update_data varchar(1) not null,
    requests_recovery varchar(1) not null,
    job_data blob null,
    primary key (sched_name,job_name,job_group)
)engine=innodb;

create table qrtz_triggers (
    sched_name varchar(120) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    job_name varchar(190) not null,
    job_group varchar(190) not null,
    description varchar(250) null,
    next_fire_time bigint(13) null,
    prev_fire_time bigint(13) null,
    priority integer null,
    trigger_state varchar(16) not null,
    trigger_type varchar(8) not null,
    start_time bigint(13) not null,
    end_time bigint(13) null,
    calendar_name varchar(190) null,
    misfire_instr smallint(2) null,
    job_data blob null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,job_name,job_group)
    references qrtz_job_details(sched_name,job_name,job_group)
)engine=innodb;

create table qrtz_simple_triggers (
    sched_name varchar(120) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    repeat_count bigint(7) not null,
    repeat_interval bigint(12) not null,
    times_triggered bigint(10) not null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
)engine=innodb;

create table qrtz_cron_triggers (
    sched_name varchar(120) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    cron_expression varchar(120) not null,
    time_zone_id varchar(80),
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
)engine=innodb;

create table qrtz_simprop_triggers
  (
    sched_name varchar(120) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    str_prop_1 varchar(512) null,
    str_prop_2 varchar(512) null,
    str_prop_3 varchar(512) null,
    int_prop_1 int null,
    int_prop_2 int null,
    long_prop_1 bigint null,
    long_prop_2 bigint null,
    dec_prop_1 numeric(13,4) null,
    dec_prop_2 numeric(13,4) null,
    bool_prop_1 varchar(1) null,
    bool_prop_2 varchar(1) null,
    primary key (sched_name,trigger_name,trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
)engine=innodb;

create table qrtz_blob_triggers (
    sched_name varchar(120) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    blob_data blob null,
    primary key (sched_name,trigger_name,trigger_group),
    index (sched_name,trigger_name, trigger_group),
    foreign key (sched_name,trigger_name,trigger_group)
    references qrtz_triggers(sched_name,trigger_name,trigger_group)
)engine=innodb;

create table qrtz_calendars (
    sched_name varchar(120) not null,
    calendar_name varchar(190) not null,
    calendar blob not null,
    primary key (sched_name,calendar_name)
)engine=innodb;

create table qrtz_paused_trigger_grps (
    sched_name varchar(120) not null,
    trigger_group varchar(190) not null,
    primary key (sched_name,trigger_group)
)engine=innodb;

create table qrtz_fired_triggers (
    sched_name varchar(120) not null,
    entry_id varchar(95) not null,
    trigger_name varchar(190) not null,
    trigger_group varchar(190) not null,
    instance_name varchar(190) not null,
    fired_time bigint(13) not null,
    sched_time bigint(13) not null,
    priority integer not null,
    state varchar(16) not null,
    job_name varchar(190) null,
    job_group varchar(190) null,
    is_nonconcurrent varchar(1) null,
    requests_recovery varchar(1) null,
    primary key (sched_name,entry_id)
)engine=innodb;

create table qrtz_scheduler_state (
    sched_name varchar(120) not null,
    instance_name varchar(190) not null,
    last_checkin_time bigint(13) not null,
    checkin_interval bigint(13) not null,
    primary key (sched_name,instance_name)
)engine=innodb;

create table qrtz_locks (
    sched_name varchar(120) not null,
    lock_name varchar(40) not null,
    primary key (sched_name,lock_name)
)engine=innodb;

create index idx_qrtz_j_req_recovery on qrtz_job_details(sched_name,requests_recovery);
create index idx_qrtz_j_grp on qrtz_job_details(sched_name,job_group);

create index idx_qrtz_t_j on qrtz_triggers(sched_name,job_name,job_group);
create index idx_qrtz_t_jg on qrtz_triggers(sched_name,job_group);
create index idx_qrtz_t_c on qrtz_triggers(sched_name,calendar_name);
create index idx_qrtz_t_g on qrtz_triggers(sched_name,trigger_group);
create index idx_qrtz_t_state on qrtz_triggers(sched_name,trigger_state);
create index idx_qrtz_t_n_state on qrtz_triggers(sched_name,trigger_name,trigger_group,trigger_state);
create index idx_qrtz_t_n_g_state on qrtz_triggers(sched_name,trigger_group,trigger_state);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(sched_name,next_fire_time);
create index idx_qrtz_t_nft_st on qrtz_triggers(sched_name,trigger_state,next_fire_time);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(sched_name,misfire_instr,next_fire_time);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(sched_name,misfire_instr,next_fire_time,trigger_state);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(sched_name,misfire_instr,next_fire_time,trigger_group,trigger_state);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(sched_name,instance_name);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(sched_name,instance_name,requests_recovery);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(sched_name,job_name,job_group);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(sched_name,job_group);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(sched_name,trigger_name,trigger_group);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(sched_name,trigger_group);



commit;

