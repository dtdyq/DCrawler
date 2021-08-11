/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.boot;

import crawler.dtdyq.core.Constant;
import crawler.dtdyq.core.CrawlerConfig;
import crawler.dtdyq.docresolve.DocProcessor;
import crawler.dtdyq.tool.URLUtil;
import crawler.dtdyq.urlgather.URLProcessor;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-24 16:26
 **/
public class CrawlerBoot {
    private CrawlerConfig crawlerConfig = new CrawlerConfig();
    public CrawlerBoot setCrawlerConfig(CrawlerConfig crawlerConfig){
        this.crawlerConfig = crawlerConfig;
        return this;
    }
    public CrawlerBoot build() throws IOException {
        Configuration configuration = crawlerConfig.getConf();

        //获取全部主域名
        List<String> domains = URLUtil.getDomains(configuration);
        crawlerConfig.setDomains(domains);

        //设置工作目录
        FileSystem fs = FileSystem.get(configuration);
        Path root = new Path(Constant.ROOT_DIR);
        if(fs.exists(root)){
            fs.delete(root,true);
        }
        fs.mkdirs(root);
        return this;
    }
    public void run() throws Exception {
        URLProcessor urlProcessor = new URLProcessor();
        urlProcessor.setConf(this.crawlerConfig.getConf());
        ToolRunner.run(urlProcessor,new String[]{});
        DocProcessor docProcessor = new DocProcessor();
        docProcessor.setConf(this.crawlerConfig.getConf());
        ToolRunner.run(docProcessor,new String[]{});
    }
}
