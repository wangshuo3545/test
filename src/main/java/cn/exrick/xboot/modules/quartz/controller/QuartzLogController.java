package cn.exrick.xboot.modules.quartz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.quartz.entity.QuartzLog;
import cn.exrick.xboot.modules.quartz.service.QuartzLogService;
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

import java.util.Arrays;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "定时任务执行日志管理接口")
@RequestMapping("/xboot/quartzLog")
@Transactional
public class QuartzLogController {

    @Autowired
    private QuartzLogService quartzLogService;

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "多条件分页获取")
    public Result<Page<QuartzLog>> getByCondition(QuartzLog quartzLog, SearchVo searchVo, PageVo pageVo) {

        Page<QuartzLog> page = quartzLogService.findByCondition(quartzLog, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<QuartzLog>>().setData(page);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除日志")
    public Result delByIds(@RequestParam String[] ids) {

        quartzLogService.deleteAllById(Arrays.asList(ids));
        return ResultUtil.success("删除定时任务成功");
    }
}
