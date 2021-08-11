/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author dyq
 * @create 2018-04-15 13:00
 **/
public class ThreadUtil {
    private static ExecutorService service = Executors.newCachedThreadPool();
    public static ExecutorService fixedPool(int poolSize){
        return Executors.newFixedThreadPool(poolSize);
    }
    public static ExecutorService cachedPool(){
        return service;
    }
    public static Future<?> exec(Runnable task){
        return service.submit(task);
    }
    public static <T> Future<T> exec(Callable<T> callable){
        return service.submit(callable);
    }
}
