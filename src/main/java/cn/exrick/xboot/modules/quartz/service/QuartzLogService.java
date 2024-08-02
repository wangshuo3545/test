package cn.exrick.xboot.modules.quartz.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.quartz.entity.QuartzLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 定时任务执行日志接口
 * @author Exrick
 */
public interface QuartzLogService extends XbootBaseService<QuartzLog, String> {

    /**
    * 多条件分页获取
    * @param quartzLog
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<QuartzLog> findByCondition(QuartzLog quartzLog, SearchVo searchVo, Pageable pageable);

    /**
     * 通过className删除
     * @param jobClassName
     */
    void deleteByJobClassName(String jobClassName);
}