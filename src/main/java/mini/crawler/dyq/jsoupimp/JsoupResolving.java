/*
 * 
 */

package mini.crawler.dyq.jsoupimp;

import mini.crawler.dyq.conf.Configuration;
import mini.crawler.dyq.core.Constant;
import mini.crawler.dyq.core.Resolving;
import mini.crawler.dyq.tool.BloomFilterUtil;
import mini.crawler.dyq.tool.DataStream;
import mini.crawler.dyq.tool.StringUtil;
import mini.crawler.dyq.tool.Tuple_3;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-15 19:55
 **/
public class JsoupResolving implements Resolving<Tuple_3<String,byte[],String>,String> {
    private Logger logger = Logger.getLogger(JsoupResolving.class);

    private ConcurrentLinkedQueue<DataStream<Tuple_3<String,byte[],String>>> src;
    private ConcurrentLinkedQueue<DataStream<String>> dest;

    //crawler.resolve.urls.queue.size
    private int outQueueSize;
    private JsoupVisitable visitable = new JsoupVisitable();

    private Tuple_3<String,byte[],String> get1(){
        logger.debug("get a document");
        while(true) {
            for (DataStream<Tuple_3<String,byte[],String>> dataStream : src) {
                if (!dataStream.empty()) {
                    Tuple_3<String,byte[],String> res = dataStream.read();
                    logger.debug("get 1 tuple:"+res._1);
                    return res;
                }
            }
        }
    }
    private void write1(String url){
        while (true){
            for(DataStream<String> out:dest){
                if(out.size() <= outQueueSize){
                    out.write(url);
                    return;
                }
            }
        }
    }
    @Override
    public void run() {
        Thread.currentThread().setName("Thread-JsoupResolving");
        logger.info("starting resolving module...");
        while (true){
            Tuple_3<String,byte[],String> doc = get1();
            logger.info("resolving "+doc._1+"'s data");
            try {
                visitable.configure(new Configuration());
            } catch (Exception e) {
                logger.error("visit configure error ");
                e.printStackTrace();
            }
            logger.debug("start visit the url's doc:"+doc._1);
            visitable.visit(doc);

            try {
                for(String url: StringUtil.getJsoupDocLinks(doc)){
                    if(visitable.shouldVisit(url)) {
                        if(!BloomFilterUtil.containsUrl(url)) {
                            write1(url);
                            BloomFilterUtil.add2UrlFilter(url);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void configure(Configuration conf) throws Exception {
        logger.info("setup resolve configure");
        outQueueSize = conf.getInt(Constant.Resolve.TO_URL_QUEUE_CAPACITY,Constant.Resolve.DEFAULT_TO_URL_QUEUE_CAPACITY);
    }

    @Override
    public void transfer(ConcurrentLinkedQueue<DataStream<Tuple_3<String,byte[],String>>> src, ConcurrentLinkedQueue<DataStream<String>> dest) {
        logger.info("setup resolve transfer");
        this.src = src;
        this.dest = dest;
    }
    @Override
    public void setVisitable(JsoupVisitable v){
        this.visitable = v;
    }
}
