/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.core;

import mini.crawler.dyq.conf.Configurable;

/**
 * urls module's core class,maintain the URLs
 * client can read url or write url using different strategy
 * @author dyq
 * @create 2018-04-14 22:24
 **/
public interface URLPool<T,V> extends Transferable<T,V>, Configurable,Runnable{
    long size();
}
