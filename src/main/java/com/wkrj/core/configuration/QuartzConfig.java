package com.wkrj.core.configuration;


import com.wkrj.core.component.quartz.TestQuartz;
import com.wkrj.core.utils.QuartzManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 *
 * @author ziro
 * @date 2020/4/28 15:08
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private QuartzManagerUtil quartzManagerUtil;

    /**
     * 使用cron表达式，定时定点任务   秒 分 时 日 月 年
     */
    @Bean
    public void Initialization() {
        quartzManagerUtil.addJob("quartz1", TestQuartz.class, "0 0 2 * * ?");
        /*//增加一条定时任务
        quartzManagerUtil.addJob("quartz1", TestQuartz.class, "0 0 2 * * ?");
        quartzManagerUtil.addJob("quartz2", TestQuartz.class, "0 21 17 * * ?");
        //删除一条定时任务
        quartzManagerUtil.removeJob("quartz2");*/
    }

}
