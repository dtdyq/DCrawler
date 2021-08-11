/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.core;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;

import okio.Sink;

import java.util.regex.Pattern;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-23 13:26
 **/
public final class Constant {
    public final static int CRAWLER_DEEP = 3;
    public final static boolean IGNORE_CONTENT_TYPE = false;
    public final static int MAX_BODY_SIZE = 1024*1024*8;

    public final static boolean ONLY_INNER_LINK = false;
    public static final Pattern NOT_NEED_RESOLVE = Pattern.compile(
        ".*(mid|mp2|mp3|mp4|wav|csv|json|php|py|bmp|gif|jpeg|jpg|png|tiff?|avi" +
            "|mov|mpeg|ram|m4v|avi|mkv|rmvb|pdf" +
            "|rm|smil|wmv|swf|wma|zip|rar|gz)$");

    public final static String ROOT_DIR = "/crawler";
    public final static String INIT_URL_DIR = ROOT_DIR+"/init/urlgather.txt";
    public final static String URL_DIR = ROOT_DIR+"/urls";
    public final static String DOC_DIR = ROOT_DIR+"/docs";
    public class NamedOutput{
        public final static String TXT = "txt";
        public final static String SEQ = "seq";
    }

    public final static int EXPECTED_URL_SIZE = 1024*1024*10;
    public final static BloomFilter<String> urlFilter = BloomFilter.create(new Funnel<String>() {
        private static final long serialVersionUID = 1L;

        public void funnel(String s, Sink sink) {
            sink.putString(s, Charsets.UTF_8);
        }

    }, EXPECTED_URL_SIZE, 0.0000001d);
}
