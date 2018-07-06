package cn.joes.job.impl;

import cn.joes.job.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by myijoes on 2018/7/6.
 */
public class EasyJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("###### Easy Job RUN ....");
    }
}
