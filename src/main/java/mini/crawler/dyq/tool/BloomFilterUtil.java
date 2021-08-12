/*
 * 
 */

package mini.crawler.dyq.tool;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

/**
 * @author dyq
 * @create 2018-04-16 16:02
 **/
public class BloomFilterUtil {
    public final static BloomFilter<String> urlFilter = BloomFilter.create(new Funnel<String>() {
        @Override
        public void funnel(String s, PrimitiveSink primitiveSink) {
            primitiveSink.putString(s,Charsets.UTF_8);
        }
    },1024*1024,0.0001);
    public static void add2UrlFilter(String url){
        urlFilter.put(url);
    }
    public static boolean containsUrl(String url){
        return urlFilter.mightContain(url);
    }
}
