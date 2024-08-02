package cn.exrick.xboot.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;


/**
 * @author Exrick
 */
// JDK8函数式接口注解 仅能包含一个抽象方法
@FunctionalInterface
public interface XbootBaseService<E, ID extends Serializable> {

    XbootBaseDao<E, ID> getRepository();

    /**
     * 根据ID获取 不存在且使用返回的对象时会抛异常
     * @param id
     * @return
     */
    default E get(ID id) {
        return getRepository().getById(id);
    }

    /**
     * 根据ID获取 不存在则返回null
     * @param id
     * @return
     */
    default E findById(ID id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 获取所有列表
     * @return
     */
    default List<E> getAll() {
        return getRepository().findAll();
    }

    /**
     * 获取总数
     * @return
     */
    default Long getTotalCount() {
        return getRepository().count();
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    default E save(E entity) {

        return getRepository().save(entity);
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    default E update(E entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 批量保存与修改
     * @param entities
     * @return
     */
    default Iterable<E> saveOrUpdateAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 删除
     * @param entity
     */
    default void delete(E entity) {
        getRepository().delete(entity);
    }

    /**
     * 根据Id删除
     * @param id
     */
    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 批量根据id删除
     * @param ids
     */
    default void deleteAllById(Iterable<ID> ids) {
        getRepository().deleteAllById(ids);
    }

    /**
     * 批量删除
     * @param entities
     */
    default void delete(Iterable<E> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 清空缓存，提交持久化
     */
    default void flush() {
        getRepository().flush();
    }

    /**
     * 根据条件查询获取
     * @param spec
     * @return
     */
    default List<E> findAll(Specification<E> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * 分页获取
     * @param pageable
     * @return
     */
    default Page<E> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    /**
     * 根据查询条件分页获取
     * @param spec
     * @param pageable
     * @return
     */
    default Page<E> findAll(Specification<E> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 获取查询条件的结果数
     * @param spec
     * @return
     */
    default long count(Specification<E> spec) {
        return getRepository().count(spec);
    }
}
