package cn.exrick.xboot.modules.file.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.file.entity.FileCategory;

import java.util.List;

/**
 * 文件分类数据处理层
 * @author Exrick
 */
public interface FileCategoryDao extends XbootBaseDao<FileCategory, String> {

    /**
     * 通过名称和创建人模糊搜索
     * @param title
     * @param createBy
     * @return
     */
    List<FileCategory> findByTitleLikeAndCreateByOrderBySortOrder(String title, String createBy);
}
