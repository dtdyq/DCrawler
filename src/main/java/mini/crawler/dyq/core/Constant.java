/*
 * 
 */

package mini.crawler.dyq.core;

/**
 * @author dyq
 * @create 2018-04-15 21:42
 **/
public final class Constant {

    public static class URL{
        public static final String POOL_SIZE = "url.maxSize";
        public static final int DEFAULT_POOL_SIZE = 1000;

        public static final String TO_FETCH_QUEUE_CAPACITY = "url.toFetchQueueCapacity";
        public static final int DEFAULT_TO_FETCH_QUEUE_SIZE = 64;

    }
    public static class Fetch{
        public static final String CONNECTION_TIMEOUT = "fetch.connectionTimeout";
        public static final int DEFAULT_CONNECTION_TIMEOUT = 10000;

        public static final String CONNECTION_TIME_INTERVAL = "fetch.connectionTimeInterval";
        public static final int DEFAULT_CONNECTION_TIME_INTERVAL = 0;

        public static final String CONNECTION_RETRY_TIME = "fetch.connectionRetryTime";
        public static final int DEFAULT_CONNECTION_RETRY_TIME = 1;

        public static final String THREAD_COUNT = "fetch.threadCount";
        public static final int DEFAULT_THREAD_COUNT = 5;

        public static final String TO_RESOLVE_QUEUE_CAPACITY = "fetch.toResolveQueueCapacity";
        public static final int DEFAULT_TO_RESOLVE_QUEUE_CAPACITY = 32;

    }
    public static class Resolve{
        public static final String TO_URL_QUEUE_CAPACITY = "resolve.toUrlQueueCapacity";
        public static final int DEFAULT_TO_URL_QUEUE_CAPACITY = 256;
    }
    public static class Global {
        public static final String ROOT_DIR = "global.rootDir";
        public static final String DEFAULT_ROOT_DIR = "c:/crawler";

        public static final String LOG_DIR = "global.logDir";
        public static final String DEFAULT_LOG_DIR = "/logs";

        public static final String DATA_STORE_DIR = "global.storeDir";
        public static final String DEFAULT_DATA_STORE_DIR = "/data";

        public static final String URL_SEED = "url.seed";
       }
}
