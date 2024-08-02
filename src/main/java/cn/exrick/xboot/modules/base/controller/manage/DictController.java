package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.Dict;
import cn.exrick.xboot.modules.base.service.DictDataService;
import cn.exrick.xboot.modules.base.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "字典管理接口")
@RequestMapping("/xboot/dict")
@Transactional
public class DictController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @Operation(summary = "获取全部数据")
    public Result<List<Dict>> getAll() {

        List<Dict> list = dictService.findAllOrderBySortOrder();
        return new ResultUtil<List<Dict>>().setData(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加")
    public Result add(Dict dict) {

        if (dictService.findByType(dict.getType()) != null) {
            return ResultUtil.error("字典类型Type已存在");
        }
        dictService.save(dict);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑")
    public Result edit(Dict dict) {

        Dict old = dictService.get(dict.getId());
        // 若type修改判断唯一
        if (!old.getType().equals(dict.getType()) && dictService.findByType(dict.getType()) != null) {
            return ResultUtil.error("字典类型Type已存在");
        }
        dictService.update(dict);
        // 删除缓存
        redisTemplate.delete("dictData::" + dict.getType());
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "通过id删除")
    public Result delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            Dict dict = dictService.get(id);
            dictService.delete(id);
            dictDataService.deleteByDictId(id);
            // 删除缓存
            redisTemplate.delete("dictData::" + dict.getType());
        }
        return ResultUtil.success("删除成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @Operation(summary = "搜索字典")
    public Result<List<Dict>> searchPermissionList(@RequestParam String key) {

        List<Dict> list = dictService.findByTitleOrTypeLike(key);
        return new ResultUtil<List<Dict>>().setData(list);
    }
}
