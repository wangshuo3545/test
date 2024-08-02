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
public class SampleParamJob implements Job {

    /**
     * 若参数变量名修改 QuartzJobController中也需对应修改
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    @QuartzRecordLog
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info(String.format("Hello %s! 欢迎使用XBoot前后端分离开发平台!作者:Exrick 时间:" + DateUtil.now(), this.parameter));
    }
}
