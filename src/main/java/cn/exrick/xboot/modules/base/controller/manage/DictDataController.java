package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.Dict;
import cn.exrick.xboot.modules.base.entity.DictData;
import cn.exrick.xboot.modules.base.service.DictDataService;
import cn.exrick.xboot.modules.base.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "字典数据管理接口")
@RequestMapping("/xboot/dictData")
@CacheConfig(cacheNames = "dictData")
@Transactional
public class DictDataController {

    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "多条件分页获取用户列表")
    public Result<Page<DictData>> getByCondition(DictData dictData,
                                                 PageVo pageVo) {

        Page<DictData> page = dictDataService.findByCondition(dictData, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<DictData>>().setData(page);
    }

    @RequestMapping(value = "/getByType/{type}", method = RequestMethod.GET)
    @Operation(summary = "通过类型获取")
    @Cacheable(key = "#type")
    public Result getByType(@PathVariable String type) {

        Dict dict = dictService.findByType(type);
        if (dict == null) {
            return ResultUtil.error("字典类型 " + type + " 不存在");
        }
        List<DictData> list = dictDataService.findByDictId(dict.getId());
        return ResultUtil.data(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加")
    public Result add(DictData dictData) {

        Dict dict = dictService.get(dictData.getDictId());
        dictDataService.save(dictData);
        // 删除缓存
        redisTemplate.delete("dictData::" + dict.getType());
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑")
    public Result edit(DictData dictData) {

        dictDataService.update(dictData);
        // 删除缓存
        Dict dict = dictService.get(dictData.getDictId());
        redisTemplate.delete("dictData::" + dict.getType());
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "批量通过id删除")
    public Result delByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            DictData dictData = dictDataService.get(id);
            Dict dict = dictService.get(dictData.getDictId());
            dictDataService.delete(id);
            // 删除缓存
            redisTemplate.delete("dictData::" + dict.getType());
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
