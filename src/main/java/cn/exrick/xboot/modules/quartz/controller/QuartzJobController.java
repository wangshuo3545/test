package cn.exrick.xboot.modules.quartz.controller;

import cn.exrick.xboot.common.annotation.QuartzRecordLog;
import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.quartz.entity.QuartzJob;
import cn.exrick.xboot.modules.quartz.service.QuartzJobService;
import cn.exrick.xboot.modules.quartz.service.QuartzLogService;
import cn.exrick.xboot.modules.quartz.utils.SchedulerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "定时任务管理接口")
@RequestMapping("/xboot/quartzJob")
@Transactional
public class QuartzJobController {

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private QuartzLogService quartzLogService;

    @Autowired
    private SchedulerUtil schedulerUtil;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "获取所有定时任务")
    public Result<Page<QuartzJob>> getAllByPage(String key, PageVo page) {

        Page<QuartzJob> data = quartzJobService.findByCondition(key, PageUtil.initPage(page));
        return new ResultUtil<Page<QuartzJob>>().setData(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加定时任务")
    public Result addJob(QuartzJob job) {

        QuartzJob quartzJob = quartzJobService.findByJobClassName(job.getJobClassName());
        if (quartzJob != null) {
            return ResultUtil.error("该定时任务类名已存在");
        }
        schedulerUtil.add(job);
        quartzJobService.save(job);
        return ResultUtil.success("创建定时任务成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "更新定时任务")
    public Result editJob(QuartzJob job) {

        schedulerUtil.delete(job);
        schedulerUtil.add(job);
        job.setStatus(CommonConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return ResultUtil.success("更新定时任务成功");
    }

    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @Operation(summary = "暂停定时任务")
    public Result pauseJob(QuartzJob job) {

        schedulerUtil.pauseJob(job);
        job.setStatus(CommonConstant.STATUS_DISABLE);
        quartzJobService.update(job);
        return ResultUtil.success("暂停定时任务成功");
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @Operation(summary = "恢复定时任务")
    public Result resumeJob(QuartzJob job) {

        schedulerUtil.resumeJob(job);
        job.setStatus(CommonConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return ResultUtil.success("恢复定时任务成功");
    }

    @RequestMapping(value = "/changeRecordLog", method = RequestMethod.POST)
    @Operation(summary = "改变记录日志开关")
    public Result changeRecordLog(String id) {

        QuartzJob job = quartzJobService.get(id);
        job.setIsRecordLog(!job.getIsRecordLog());
        quartzJobService.update(job);
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除定时任务")
    public Result deleteJob(@RequestParam String[] ids) {

        for (String id : ids) {
            QuartzJob job = quartzJobService.get(id);
            schedulerUtil.delete(job);
            quartzJobService.delete(job);
            // 删除关联执行日志
            quartzLogService.deleteByJobClassName(job.getJobClassName());
        }
        return ResultUtil.success("删除定时任务成功");
    }
}
