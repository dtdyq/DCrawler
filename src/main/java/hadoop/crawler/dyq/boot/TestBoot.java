/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.boot;

import crawler.dtdyq.core.CrawlerConfig;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-24 19:21
 **/
public class TestBoot {
    public static void main(String[] args) throws Exception {
        CrawlerConfig config = new CrawlerConfig()
            .setIgnoreContentType(true)
            .setCrawlerDeep(1)
            .setOnlyInnerLink(true)
            .setInitUrl(new String[]{"http://twitter.github.io/scala_school/zh_cn/index.html",});

        new CrawlerBoot().setCrawlerConfig(config).build().run();
    }
}
