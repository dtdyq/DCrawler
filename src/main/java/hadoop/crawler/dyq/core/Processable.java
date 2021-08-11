/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.core;

import hadoop.crawler.dyq.docresolve.DocumentWritable;

import org.jsoup.Connection;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-26 15:46
 **/
public interface Processable {
    boolean shouldProcess(String url);
    DocumentWritable process(Connection.Response response) throws Exception;
}
