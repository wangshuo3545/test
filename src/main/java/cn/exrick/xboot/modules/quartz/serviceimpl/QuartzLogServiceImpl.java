package cn.exrick.xboot.modules.quartz.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.quartz.dao.QuartzLogDao;
import cn.exrick.xboot.modules.quartz.entity.QuartzLog;
import cn.exrick.xboot.modules.quartz.service.QuartzLogService;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务执行日志接口实现
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class QuartzLogServiceImpl implements QuartzLogService {

    @Autowired
    private QuartzLogDao quartzLogDao;

    @Override
    public QuartzLogDao getRepository() {
        return quartzLogDao;
    }

    @Override
    public Page<QuartzLog> findByCondition(QuartzLog quartzLog, SearchVo searchVo, Pageable pageable) {

        return quartzLogDao.findAll(new Specification<QuartzLog>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<QuartzLog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> jobClassNameField = root.get("jobClassName");

                List<Predicate> list = new ArrayList<>();

                // 任务类名相等匹配
                if (StrUtil.isNotBlank(quartzLog.getJobClassName())) {
                    list.add(cb.equal(jobClassNameField, quartzLog.getJobClassName()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public void deleteByJobClassName(String jobClassName) {

        quartzLogDao.deleteByJobClassName(jobClassName);
    }
}
