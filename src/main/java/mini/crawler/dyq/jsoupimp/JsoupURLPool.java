/*
 * 
 */

package mini.crawler.dyq.jsoupimp;

import mini.crawler.dyq.conf.Configuration;
import mini.crawler.dyq.core.Constant;
import mini.crawler.dyq.core.URLPool;
import mini.crawler.dyq.tool.DataStream;

import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-14 22:36
 **/
public class JsoupURLPool implements URLPool<String,String> {
    private Logger logger = Logger.getLogger(JsoupURLPool.class);

    private LinkedList<String> urls;

    private ConcurrentLinkedQueue<DataStream<String>> src;
    private ConcurrentLinkedQueue<DataStream<String>> dest;

    private int outQueueSize;
    private String[] seed;
    public JsoupURLPool(){
        urls = new LinkedList<>();
    }

    @Override
    public long size() {
        return urls.size();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Thread-JsoupURLPool");
        logger.info("starting urlPool module");
        for (String s : seed) {
            logger.debug("add seed:"+s+" to pool");
            urls.offer(s);
        }
        while (true) {
            for (DataStream<String> queue : dest) {
                if(urls.isEmpty())    break;
                if (queue.size() < outQueueSize) {
                    String url = urls.poll();
                    queue.write(url);
                    logger.debug("write url:" + url + " to dest");
                }
            }

            for (DataStream<String> queue : src) {
                if (!queue.empty()) {
                    String url = queue.read();
                    logger.debug("put url:"+url+"to pool");
                    urls.offer(url);
                }
            }
        }
    }

    @Override
    public void configure(Configuration conf) throws Exception {
        logger.info("setup urlPool configure");
        seed = conf.getStrings(Constant.Global.URL_SEED,null);
        if(seed == null){
            throw new Exception("the url seed is null");
        }
        outQueueSize = conf.getInt(Constant.URL.TO_FETCH_QUEUE_CAPACITY,Constant.URL.DEFAULT_TO_FETCH_QUEUE_SIZE);

    }

    @Override
    public void transfer(ConcurrentLinkedQueue<DataStream<String>> src, ConcurrentLinkedQueue<DataStream<String>> dest) {
        logger.info("setup urlPool transfer");
        this.src = src;
        this.dest = dest;
    }
}
