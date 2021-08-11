/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.jsoupimp;

import mini.crawler.dyq.conf.Configuration;
import mini.crawler.dyq.core.Constant;
import mini.crawler.dyq.core.Fetching;
import mini.crawler.dyq.tool.DataStream;
import mini.crawler.dyq.tool.Tuple_3;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-15 12:31
 **/
public class JsoupFetching implements Fetching<String, Tuple_3<String,byte[],String>> {
    private static int fetchId = 0;
    private Logger logger = Logger.getLogger(JsoupFetching.class);

    private ConcurrentLinkedQueue<DataStream<String>> src;
    private ConcurrentLinkedQueue<DataStream<Tuple_3<String,byte[],String>>> dest;

    private int timeout;
    private int timeInterval;
    private int fetchResolveQueueSize;
    private String get1(){
        while(true) {
            for (DataStream<String> dataStream : src) {
                if (!dataStream.empty())
                    return dataStream.read();
            }
        }
    }
    private void write1(Tuple_3<String,byte[],String> document){
        while (true){
            for(DataStream<Tuple_3<String,byte[],String>> out:dest){
                if(out.size() <= fetchResolveQueueSize){
                    out.write(document);
                    return;
                }
            }
        }
    }
    @Override
    public void run() {
        Thread.currentThread().setName("Thread-JsoupFetching-"+(fetchId++));
        logger.info("starting fetch module...");
        while(true){
            String url = get1();
            Connection.Response  response;
            logger.info("start fetching url:"+url+"...");

            try {
                response = Jsoup.connect(url).ignoreContentType(true)
                    .timeout(timeout).maxBodySize(10 * 1024 * 1024).execute();
            } catch (IOException e) {
                continue;
            }
            if(response!=null && response.statusCode() !=200) continue;
            Tuple_3<String,byte[],String> document;
            if(response == null){
                document = new Tuple_3<>(url,new byte[]{},"unknow");
            }else {
                document = new Tuple_3<>(url, response.bodyAsBytes(), response.contentType());
            }
            write1(document);
            logger.debug("fetch "+url+" finish");
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void configure(Configuration conf) {
        logger.info("setup fetch configure");
        timeout = conf.getInt(Constant.Fetch.CONNECTION_TIMEOUT,Constant.Fetch.DEFAULT_CONNECTION_TIMEOUT);
        timeInterval = conf.getInt(Constant.Fetch.CONNECTION_TIME_INTERVAL,Constant.Fetch.DEFAULT_CONNECTION_TIME_INTERVAL);
        fetchResolveQueueSize = conf.getInt(Constant.Fetch.TO_RESOLVE_QUEUE_CAPACITY,Constant.Fetch.DEFAULT_TO_RESOLVE_QUEUE_CAPACITY);
    }

    @Override
    public void transfer(ConcurrentLinkedQueue<DataStream<String>> src, ConcurrentLinkedQueue<DataStream<Tuple_3<String,byte[],String>>> dest) {
        logger.info("setup fetch transfer");
        this.src = src;
        this.dest = dest;
    }
}
