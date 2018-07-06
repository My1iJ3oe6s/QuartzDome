package cn.joes.utils;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 *  自定义调度器
 *
 *  该对象与Scheduler的实现类具有相同的功能,具体开发使用官方的实现类:
 *  StdScheduler
 *  RemoteMBeanScheduler
 *
 * Created by myijoes on 2018/7/5.
 */
public class QuartzScheduleManager {

    @Autowired
    @Qualifier(value = "Scheduler")
    Scheduler scheduler;

    /**
     * 启动一个调度对象
     *
     * @throws SchedulerException
     */
    public void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 检查调度是否启动
     *
     * @return
     * @throws SchedulerException
     */
    public boolean isStarted() throws SchedulerException {
        return scheduler.isStarted();
    }

    /**
     * 关闭调度信息
     *
     * @throws SchedulerException
     */
    public void shutdown() throws SchedulerException {
        scheduler.shutdown();
    }

    /**
     * 添加调度的job信息
     *
     * @param jobdetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(JobDetail jobdetail, Trigger trigger)
            throws SchedulerException {
        return scheduler.scheduleJob(jobdetail, trigger);
    }

    /**
     * 添加相关的触发器
     *
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        return scheduler.scheduleJob(trigger);
    }

    /**
     * 添加多个job任务
     *
     * @param triggersAndJobs
     * @param replace
     * @throws SchedulerException
     */
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        scheduler.scheduleJobs(triggersAndJobs, replace);
    }

    /**
     * 停止调度Job任务
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJob(TriggerKey triggerkey)
            throws SchedulerException {
        return scheduler.unscheduleJob(triggerkey);
    }

    /**
     * 停止调度多个触发器相关的job
     *
     * @param triggerKeylist
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJobs(List<TriggerKey> triggerKeylist) throws SchedulerException {
        return scheduler.unscheduleJobs(triggerKeylist);
    }

    /**
     * 重新恢复触发器相关的job任务
     *
     * @param triggerkey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger)
            throws SchedulerException {
        return scheduler.rescheduleJob(triggerkey, trigger);
    }

    /**
     * 添加相关的job任务
     *
     * @param jobdetail
     * @param flag
     * @throws SchedulerException
     */
    public void addJob(JobDetail jobdetail, boolean flag)
            throws SchedulerException {
        scheduler.addJob(jobdetail, flag);
    }

    /**
     * 删除相关的job任务
     *
     * @param jobkey
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJob(JobKey jobkey) throws SchedulerException {
        return scheduler.deleteJob(jobkey);
    }

    /**
     * 删除相关的多个job任务
     *
     * @param jobKeys
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJobs(List<JobKey> jobKeys)
            throws SchedulerException {
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * @param jobkey
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey) throws SchedulerException {
        scheduler.triggerJob(jobkey);
    }

    /**
     * @param jobkey
     * @param jobdatamap
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey, JobDataMap jobdatamap)
            throws SchedulerException {
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 停止一个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 停止多个job任务
     *
     * @param groupmatcher
     * @throws SchedulerException
     */
    public void pauseJobs(GroupMatcher<JobKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 停止使用相关的触发器
     *
     * @param triggerkey
     * @throws SchedulerException
     */
    public void pauseTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.pauseTrigger(triggerkey);
    }

    public void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复相关的job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void resumeJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    public void resumeJobs(GroupMatcher<JobKey> matcher)
            throws SchedulerException {
        scheduler.resumeJobs(matcher);
    }

    public void resumeTrigger(TriggerKey triggerkey)
            throws SchedulerException {
        scheduler.resumeTrigger(triggerkey);
    }

    public void resumeTriggers(GroupMatcher<TriggerKey> groupmatcher)
            throws SchedulerException {
        scheduler.resumeTriggers(groupmatcher);
    }

    /**
     * 暂停调度中所有的job任务
     *
     * @throws SchedulerException
     */
    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有的job的任务
     *
     * @throws SchedulerException
     */
    public void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }


}
