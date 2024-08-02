package cn.exrick.xboot.modules.quartz.utils;

import cn.exrick.xboot.common.constant.QuartzConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.quartz.entity.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author exrick
 */
@Slf4j
@Component
public class SchedulerUtil {

    @Autowired
    private Scheduler scheduler;

    public static Job getClass(String classname) throws Exception {

        Class<?> target = Class.forName(classname);
        return (Job) target.newInstance();
    }

    /**
     * 添加定时任务
     * @param job
     */
    public void add(QuartzJob job) {

        String jobClassName = job.getJobClassName();
        String cronExpression = job.getCronExpression();
        Integer misfirePolicy = job.getMisfirePolicy();
        String parameter = job.getParameter();

        if (!CronExpression.isValidExpression(cronExpression)) {
            throw new XbootException("Cron表达式非法");
        }

        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter", parameter)
                    .build();

            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 设置历史未执行（错误失效）的任务触发策略
            scheduleBuilder = handleCronScheduleMisfirePolicy(scheduleBuilder, misfirePolicy);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.toString());
            throw new XbootException("创建定时任务失败");
        } catch (Exception e) {
            throw new XbootException("后台找不到该类名任务");
        }
    }

    /**
     * 设置定时任务失效执行策略
     * @param cb
     * @param type
     * @return
     */
    public CronScheduleBuilder handleCronScheduleMisfirePolicy(CronScheduleBuilder cb, Integer type) {

        if (QuartzConstant.MISFIRE_IGNORE_MISFIRES.equals(type)) {
            return cb.withMisfireHandlingInstructionIgnoreMisfires();
        } else if (QuartzConstant.MISFIRE_FIRE_AND_PROCEED.equals(type)) {
            return cb.withMisfireHandlingInstructionFireAndProceed();
        } else {
            return cb.withMisfireHandlingInstructionDoNothing();
        }
    }

    /**
     * 暂停执行
     * @param job
     */
    public void pauseJob(QuartzJob job) {

        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new XbootException("暂停定时任务失败");
        }
    }

    /**
     * 恢复执行
     * @param job
     */
    public void resumeJob(QuartzJob job) {

        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new XbootException("恢复定时任务失败");
        }
    }

    /**
     * 删除定时任务
     * @param job
     */
    public void delete(QuartzJob job) {

        try {
            String jobClassName = job.getJobClassName();
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception e) {
            throw new XbootException("删除定时任务失败");
        }
    }
}
