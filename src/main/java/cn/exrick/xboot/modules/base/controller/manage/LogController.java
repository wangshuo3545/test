package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Log;
import cn.exrick.xboot.modules.base.entity.elasticsearch.EsLog;
import cn.exrick.xboot.modules.base.service.LogService;
import cn.exrick.xboot.modules.base.service.elasticsearch.EsLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "日志管理接口")
@RequestMapping("/xboot/log")
@Transactional
public class LogController {

    @Value("${xboot.logRecord.es:false}")
    private Boolean esRecord;

    @Autowired
    private EsLogService esLogService;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "分页获取全部")
    public Result getAllByPage(@RequestParam(required = false) Integer type,
                               @RequestParam String key,
                               SearchVo searchVo,
                               PageVo pageVo) throws UnsupportedEncodingException {

        if (esRecord) {
            // 支持排序的字段
            if (!"costTime".equals(pageVo.getSort())) {
                pageVo.setSort("timeMillis");
            }
            Page<EsLog> es = esLogService.findByCondition(type, key.trim(), searchVo, PageUtil.initPage(pageVo));
            return ResultUtil.data(es);
        } else {
            Page<Log> log = logService.findByConfition(type, key, searchVo, PageUtil.initPage(pageVo));
            return ResultUtil.data(log);
        }
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "批量删除")
    public Result delByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            if (esRecord) {
                esLogService.deleteLog(id);
            } else {
                logService.delete(id);
            }
        }
        return ResultUtil.success("删除成功");
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.POST)
    @Operation(summary = "全部删除")
    public Result delAll() {

        if (esRecord) {
            esLogService.deleteAll();
        } else {
            logService.deleteAll();
        }
        return ResultUtil.success("删除成功");
    }
}
