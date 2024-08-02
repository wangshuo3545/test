package cn.exrick.xboot.modules.quartz.jobs;

import cn.exrick.xboot.common.annotation.QuartzRecordLog;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 示例带参定时任务
 * 添加@QuartzRecordLog注解后可通过XBoot管理系统控制是否记录执行日志
 * 若想禁止该任务并发同时执行可添加@DisallowConcurrentExecution注解
 * @author Exrick
 */
@Slf4j
public class SampleJob implements Job {

    @Override
    @QuartzRecordLog
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info(String.format("欢迎使用XBoot前后端分离开发平台!作者:Exrick 时间:" + DateUtil.now()));
    }
}
