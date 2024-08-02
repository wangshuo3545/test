package cn.exrick.xboot.base;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Exrick
 */
public abstract class XbootBaseController<E, ID extends Serializable> {

    /**
     * 获取service
     * @return
     */
    @Autowired
    public abstract XbootBaseService<E, ID> getService();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "通过id获取")
    public Result<E> get(@PathVariable ID id) {

        E entity = getService().get(id);
        return new ResultUtil<E>().setData(entity);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "获取全部数据")
    public Result<List<E>> getAll() {

        List<E> list = getService().getAll();
        return new ResultUtil<List<E>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "分页获取")
    public Result<Page<E>> getByPage(PageVo page) {

        Page<E> data = getService().findAll(PageUtil.initPage(page));
        return new ResultUtil<Page<E>>().setData(data);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "保存数据")
    public Result<E> save(E entity) {

        E e = getService().save(entity);
        return new ResultUtil<E>().setData(e);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    @Operation(summary = "更新数据")
    public Result<E> update(E entity) {

        E e = getService().update(entity);
        return new ResultUtil<E>().setData(e);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ResponseBody
    @Operation(summary = "批量通过id删除")
    public Result delByIds(ID[] ids) {

        getService().deleteAllById(Arrays.asList(ids));
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
