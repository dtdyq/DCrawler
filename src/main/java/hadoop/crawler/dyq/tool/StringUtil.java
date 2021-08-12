/*
 * 
 */

package hadoop.crawler.dyq.tool;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-24 20:04
 **/
public class StringUtil {
    /**
     * ?ж?url??????patterns?е????????
     * @param patterns
     * @param url
     * @return
     */
    public static synchronized boolean match(String[] patterns,String url){
        for(String pattern:patterns){
            if(url.matches(pattern)){
                return true;
            }
        }
        return false;
    }

    /**
     * ??????????????????????
     * @param url
     * @param link
     * @return
     */
    public static synchronized String connectUrl(String url,String link){
        if(url.endsWith("/")){
            url = url.substring(0,url.length()-1);
        }
        if(!link.startsWith("/")){
            link = "/"+link;
        }
        return url+link;
    }
}
