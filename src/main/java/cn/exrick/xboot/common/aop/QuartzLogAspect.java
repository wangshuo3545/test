package cn.exrick.xboot.common.aop;

import cn.exrick.xboot.common.utils.ThreadPoolUtil;
import cn.exrick.xboot.modules.quartz.entity.QuartzJob;
import cn.exrick.xboot.modules.quartz.entity.QuartzLog;
import cn.exrick.xboot.modules.quartz.service.QuartzJobService;
import cn.exrick.xboot.modules.quartz.service.QuartzLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Spring AOP实现日志管理
 * @author Exrick
 */
@Aspect
@Component
@Slf4j
public class QuartzLogAspect {

    private static final ThreadLocal<Date> THREAD_LOCAL_BEGIN_TIME = new NamedThreadLocal<>("ThreadLocal beginTime");

    @Autowired
    private QuartzJobService jobService;

    @Autowired
    private QuartzLogService logService;

    /**
     * Controller层切点 注解方式
     */
    @Pointcut("@annotation(cn.exrick.xboot.common.annotation.QuartzRecordLog)")
    public void quartzLogAspect() {

    }

    /**
     * 前置通知
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("quartzLogAspect()")
    public void doBefore(JoinPoint joinPoint) {

        // 线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime = new Date();
        THREAD_LOCAL_BEGIN_TIME.set(beginTime);
    }

    /**
     * 后置通知(在方法执行之后并返回数据)
     * @param joinPoint 切点
     */
    @AfterReturning("quartzLogAspect()")
    public void after(JoinPoint joinPoint) {

        try {
            QuartzLog log = new QuartzLog();

            String className = joinPoint.getTarget().getClass().getName();
            QuartzJob job = jobService.findByJobClassName(className);
            // 未找到对应任务或未开启记录日志
            if (job == null || !job.getIsRecordLog()) {
                return;
            }
            log.setJobClassName(className);
            log.setExecuteTime(new Date());
            // 请求开始时间
            long beginTime = THREAD_LOCAL_BEGIN_TIME.get().getTime();
            long endTime = System.currentTimeMillis();
            // 请求耗时
            Long logElapsedTime = endTime - beginTime;
            log.setCostTime(logElapsedTime.intValue());

            // 调用线程保存日志
            ThreadPoolUtil.getPool().execute(new SaveQuartzLogThread(log, logService));
        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
        THREAD_LOCAL_BEGIN_TIME.remove();
    }

    /**
     * 保存日志至数据库
     */
    private static class SaveQuartzLogThread implements Runnable {

        private final QuartzLog log;
        private final QuartzLogService logService;

        public SaveQuartzLogThread(QuartzLog esLog, QuartzLogService logService) {
            this.log = esLog;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.save(log);
        }
    }
}
