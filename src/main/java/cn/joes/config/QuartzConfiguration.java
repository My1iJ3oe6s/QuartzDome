package cn.joes.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

/**
 *
 * Quartz配置
 *
 * Created by myijoes on 2018/6/30.
 */

@Configuration
public class QuartzConfiguration {

    @Bean(name="SchedulerFactory")
    public SchedulerFactory schedulerFactoryBean() throws IOException {
        SchedulerFactory factory = new StdSchedulerFactory();
        return factory;
    }

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws Exception {
        return schedulerFactoryBean().getScheduler();
    }
}
