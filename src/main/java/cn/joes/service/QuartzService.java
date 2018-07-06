package cn.joes.service;

import cn.joes.job.BaseJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myijoes on 2018/7/5.
 */
@Component
public class QuartzService {

    private static final String JOB_PATH_PREFIX = "cn.joes.job.impl.";

    @Autowired
    Scheduler scheduler;

    /**
     * 创建定时任务(Cron)
     * <p>
     * 该定时任务采用的是在启动后每隔多少秒执行一次
     * 允许设置的参数包含:
     *
     * @param jobClassName: className(即Job接口的实现)
     * @param jobName:      该Job的名字
     * @param groupName:    属于的组织
     * @param interval:     时间的表达式
     * @param jobDataMap:   定时任务的变量
     */
    public void addSimpleJob(String jobClassName, String jobName, String groupName, Integer interval, Map<String, Object> jobDataMap) throws Exception {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever();
        addJob(jobClassName, jobName, groupName, scheduleBuilder, jobDataMap);
    }

    /**
     * 创建定时任务(Cron)
     * <p>
     * 该定时任务采用的是cron的触发规则
     * 允许设置的参数包含:
     *
     * @param jobClassName:   className(即Job接口的实现)
     * @param jobName:        该Job的名字
     * @param groupName:      属于的组织
     * @param cronExpression: 时间的表达式
     * @param jobDataMap      : 定时任务的变量
     */
    public void addCronJob(String jobClassName, String jobName, String groupName, String cronExpression, Map<String, Object> jobDataMap) throws Exception {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        addJob(jobClassName, jobName, groupName, scheduleBuilder, jobDataMap);
    }

    /**
     *
     */
    public void addJob(String jobClassName, String jobName, String groupName, ScheduleBuilder scheduleBuilder, Map<String, Object> jobDataMap) throws Exception {
        if (!isBaseJob(jobClassName)) {
            return;
        }

        if (jobDataMap == null) {
            jobDataMap = new HashMap<>();
        }

        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobName, groupName).usingJobData(new JobDataMap(jobDataMap)).build();

        //按新的cronExpression表达式构建一个新的trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, groupName)
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    /**
     * 中断定任务
     *
     * @param jobName
     * @param jobGroupName
     */
    public void interrupt(String jobName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 重启定时任务
     *
     * @param jobName
     * @param jobGroupName
     */
    public void restart(String jobName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 重设指定任务的定时生效规则
     * <p>
     * 这里的name一般都是用Job的class的名字代替的, 但是当遇到同一个Job设置多个定时规则的时候可能不一样
     * 所以为了验证这里参数是随机还是指定为className 需通过开启Job和Trigger一对多的例子来观察之间的关系
     * <p>
     * 因为上面创建的时候入参只用className和groupName 所以后面的不管是Job和Trigger的名字和组织为这两个
     *
     * @param jobName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    public void updateJobTrigger(String jobName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }

    }

    /**
     * 定时任务的删除
     *
     * @param jobName
     * @param jobGroupName
     * @throws Exception
     */
    public void deleteJob(String jobName, String jobGroupName) throws Exception {
        //中断Trigger
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobName, jobGroupName));
        //移除Trigger
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobName, jobGroupName));
        //删除Job
        scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));

    }

    /**
     * 定时任务的查询
     *
     * @param jobName
     * @param jobGroupName
     * @throws Exception
     */
    public void queryJob(String jobName, String jobGroupName) throws Exception {
        Trigger trigger = scheduler.getTrigger(new TriggerKey(jobName, jobGroupName));

        List<String> jobGroupNames = scheduler.getJobGroupNames();

        jobGroupNames.stream().forEach(c -> System.out.println("###### 存在的组织有: " + c));
    }

    /**
     * 通过类名获取BaseJob接口实现类的对象
     *
     * @param className
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String className) throws Exception {
        Class<?> jobClass = Class.forName(JOB_PATH_PREFIX + className);
        return (BaseJob) jobClass.newInstance();
    }

    /**
     * 判断该类名是否为BaseJob的实现类
     *
     * @param className
     * @return
     */
    public static Boolean isBaseJob(String className) {
        try {
            Class<?> jobClass = Class.forName(JOB_PATH_PREFIX + className);
            if (BaseJob.class.isAssignableFrom(jobClass)) {
                return true;
            }
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }

}
