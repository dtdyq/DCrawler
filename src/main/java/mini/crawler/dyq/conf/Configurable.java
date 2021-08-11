/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.conf;


/**
 * @author dyq
 * @create 2018-04-14 22:40
 **/
public interface Configurable {
    void configure(Configuration conf) throws Exception;
}
