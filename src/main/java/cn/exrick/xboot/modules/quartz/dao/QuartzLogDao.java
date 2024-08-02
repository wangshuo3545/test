 package cn.exrick.xboot.modules.quartz.dao;

 import cn.exrick.xboot.base.XbootBaseDao;
 import cn.exrick.xboot.modules.quartz.entity.QuartzLog;
 import org.springframework.data.jpa.repository.Modifying;
 import org.springframework.data.jpa.repository.Query;

/**
 * 定时任务执行日志数据处理层
 * @author Exrick
 */
public interface QuartzLogDao extends XbootBaseDao<QuartzLog, String> {

    /**
     * 通过className删除
     * @param jobClassName
     */
    @Modifying
    @Query("delete from QuartzLog m where m.jobClassName = ?1")
    void deleteByJobClassName(String jobClassName);
}