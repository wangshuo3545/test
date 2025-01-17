package cn.exrick.xboot.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
public class ThreadPoolUtil {

    /**
     * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
     */
    private static final int SIZE_CORE_POOL = 5;

    /**
     * 线程池维护线程的最大数量
     */
    private static final int SIZE_MAX_POOL = 10;

    /**
     * 当前线程数超过核心线程数时，空闲线程存活时间
     */
    private static final long ALIVE_TIME = 2000;

    /**
     * 线程缓冲队列
     */
    private static final BlockingQueue<Runnable> blockQueue = new ArrayBlockingQueue<>(100);

    private static final ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME, TimeUnit.MILLISECONDS,
            blockQueue, new ThreadPoolExecutor.CallerRunsPolicy());

    static {

        pool.prestartAllCoreThreads();
    }

    private ThreadPoolUtil() {
        // 工具类建议不提供public构造入口
        throw new IllegalStateException("Utility class");
    }

    public static ThreadPoolExecutor getPool() {
        return pool;
    }
}
