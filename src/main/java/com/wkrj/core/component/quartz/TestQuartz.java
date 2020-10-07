package com.wkrj.core.component.quartz;

import cn.hutool.core.date.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 定时任务类
 * @author ziro
 * @date 2020/4/17 17:28
 */
public class TestQuartz implements Job {
    /*@Autowired
    private JdbcTemplate jdbcTemplate;*/

    /**
     * 按时定时任务
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(DateTime.now());
        System.out.println("定时任务：空");
    }

}
