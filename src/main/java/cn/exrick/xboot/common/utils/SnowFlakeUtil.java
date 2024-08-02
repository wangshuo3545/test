package cn.exrick.xboot.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Exrick
 */
@Slf4j
public class SnowFlakeUtil {

    /**
     * 派号器workid：0~31
     * 机房datacenterid：0~31
     */
    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static Long nextId() {
        return snowflake.nextId();
    }
}
