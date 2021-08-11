/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.boot;

import mini.crawler.dyq.conf.Configuration;
import mini.crawler.dyq.core.Constant;
import mini.crawler.dyq.core.Fetching;
import mini.crawler.dyq.core.Resolving;
import mini.crawler.dyq.core.URLPool;
import mini.crawler.dyq.jsoupimp.JsoupFetching;
import mini.crawler.dyq.jsoupimp.JsoupResolving;
import mini.crawler.dyq.jsoupimp.JsoupURLPool;
import mini.crawler.dyq.jsoupimp.JsoupVisitable;
import mini.crawler.dyq.tool.DataStream;
import mini.crawler.dyq.tool.ThreadUtil;
import mini.crawler.dyq.tool.Tuple_3;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-15 22:48
 **/
public class CrawlerBooter {
    static {
        System.setProperty("logDir", "c:/crawler/logs");
    }

    URLPool pool = new JsoupURLPool();

    Fetching fetching = new JsoupFetching();

    Resolving resolving = new JsoupResolving();

    private Logger logger = Logger.getLogger(CrawlerBooter.class);

    private ConcurrentLinkedQueue<DataStream<String>> u2f = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<DataStream<Tuple_3<String, byte[], String>>> f2r = new ConcurrentLinkedQueue<>();

    private ConcurrentLinkedQueue<DataStream<String>> r2u = new ConcurrentLinkedQueue<>();

    private Configuration configuration = new Configuration();

    private JsoupVisitable visitable = new JsoupVisitable();

    public static void main(String[] args) throws Exception {
        new CrawlerBooter().seed("https://www.pexels.com")
            .visitable(new JsoupVisitable())
            .threadCount(String.valueOf(3))
            .run();
    }

    private void setup() throws IOException {
        String rootDir = configuration.getString(Constant.Global.ROOT_DIR, Constant.Global.DEFAULT_ROOT_DIR);
        String dataDir = configuration.getString(Constant.Global.DATA_STORE_DIR,
            Constant.Global.DEFAULT_DATA_STORE_DIR);
        String logDir = configuration.getString(Constant.Global.LOG_DIR, Constant.Global.DEFAULT_LOG_DIR);
        Path root = Paths.get(rootDir);
        if (!Files.exists(root)) {
            Files.createDirectory(root);
        }
        Path data = Paths.get(rootDir + dataDir);
        if (!Files.exists(data)) {
            Files.createDirectory(data);
        }
        Path log = Paths.get(rootDir + logDir);
        if (!Files.exists(log)) {
            Files.createDirectory(log);
        }
    }

    private void build() throws Exception {
        u2f.offer(new DataStream<>());
        u2f.offer(new DataStream<>());
        f2r.offer(new DataStream<>());
        f2r.offer(new DataStream<>());
        r2u.offer(new DataStream<>());

        pool.transfer(r2u, u2f);
        pool.configure(configuration);

        fetching.configure(configuration);
        fetching.transfer(u2f, f2r);

        resolving.configure(configuration);
        resolving.transfer(f2r, r2u);
        visitable.configure(configuration);
        resolving.setVisitable(visitable);
    }

    private void start() {
        int cnt = configuration.getInt(Constant.Fetch.THREAD_COUNT, Constant.Fetch.DEFAULT_THREAD_COUNT);
        ThreadUtil.exec(pool);
        for (int i = 0; i < cnt; i++) {
            ThreadUtil.exec(fetching);
        }
        ThreadUtil.exec(resolving);
    }

    public CrawlerBooter visitable(JsoupVisitable v) {
        this.visitable = v;
        return this;
    }

    public CrawlerBooter seed(String seeds) {
        configuration.set(Constant.Global.URL_SEED, seeds);
        return this;
    }

    public CrawlerBooter threadCount(String num) {
        configuration.set(Constant.Fetch.THREAD_COUNT, num);
        return this;
    }

    public void run() throws Exception {
        Thread.currentThread().setName("Thread-CrawlerMain");
        logger.info("init crawler booter...");
        setup();
        build();
        logger.info("start crawler...");
        start();
    }
}
