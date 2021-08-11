/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.core;

import mini.crawler.dyq.tool.DataStream;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-15 19:14
 **/
public interface Transferable<T,V> {
    void transfer(ConcurrentLinkedQueue<DataStream<T>> src, ConcurrentLinkedQueue<DataStream<V>> dest);
}
