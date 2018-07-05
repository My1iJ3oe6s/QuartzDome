package cn.joes.job.impl;

import cn.joes.job.BaseJob;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;

/**
 *
 * Job接口的实现类
 *
 * Created by myijoes on 2018/7/5.
 */

public class HelloJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        String name = detail.getJobDataMap().getString("name");
        System.out.println("say hello to " + name + " at " + new Date());
        context.getJobDetail().getJobDataMap();
    }  
}  
