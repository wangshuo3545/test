package cn.exrick.xboot.common.constant;

/**
 * @author Exrick
 */
public interface QuartzConstant {

    /**
     * 所有未触发的任务都会立即执行
     */
    Integer MISFIRE_IGNORE_MISFIRES = 0;

    /**
     * 只会立即执行一次第一次未触发的任务
     */
    Integer MISFIRE_FIRE_AND_PROCEED = 1;

    /**
     * 忽略所有未触发的任务
     */
    Integer MISFIRE_DO_NOTHING = 2;
}
