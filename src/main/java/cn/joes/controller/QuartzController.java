package cn.joes.controller;

import cn.joes.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by myijoes on 2018/7/5.
 */
@RestController
public class QuartzController {

    @Autowired
    QuartzService quartzService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public void sayHello() {
        System.out.println("###### Hello....");
    }

    @PostMapping(value = "/addjob/simple")
    public void addSimplejob(@RequestParam(value = "jobClassName") String jobClassName,
                             @RequestParam(value = "jobName") String jobName,
                             @RequestParam(value = "jobGroupName") String jobGroupName,
                             @RequestParam(value = "interval") Integer interval,
                             @RequestParam(value = "mapData", required = false) Map<String, Object> mapData) throws Exception {
        quartzService.addSimpleJob(jobClassName, jobName, jobGroupName, interval, mapData);
    }

    @PostMapping(value = "/addjob/cron")
    public void addSimplejob(@RequestParam(value = "jobClassName") String jobClassName,
                             @RequestParam(value = "jobClassName") String jobName,
                             @RequestParam(value = "jobGroupName") String jobGroupName,
                             @RequestParam(value = "cronExpression") String cronExpression,
                             @RequestParam(value = "mapData", required = false) Map<String, Object> mapData) throws Exception {
        quartzService.addCronJob(jobClassName, jobName, jobGroupName, cronExpression, mapData);
    }

    @PostMapping(value = "/interuptjob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        quartzService.interrupt(jobClassName, jobGroupName);
    }

    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        quartzService.restart(jobClassName, jobGroupName);
    }

    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        quartzService.updateJobTrigger(jobClassName, jobGroupName, cronExpression);
    }

    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        quartzService.deleteJob(jobClassName, jobGroupName);
    }

    @PostMapping(value = "/queryjob")
    public void queryJob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        quartzService.queryJob(jobClassName, jobGroupName);
    }


}
