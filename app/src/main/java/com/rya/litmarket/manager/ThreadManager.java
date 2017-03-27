package com.rya.litmarket.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ryanyans32 on 2017/3/27.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */

public class ThreadManager {
    private static ThreadPool threadPool;

    public static ThreadPool getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPool();
                }
            }
        }
        return threadPool;
    }


    static class ThreadPool {
        private ThreadPoolExecutor executor;
        private int corePoolSize = 5;
        private int maximumPoolSize = 10;
        private long keepAliveTime = 1L;

        private ThreadPool() {

        }

        public void execute(Runnable runnable) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
                        Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            }

            executor.execute(runnable);

        }
    }
}
