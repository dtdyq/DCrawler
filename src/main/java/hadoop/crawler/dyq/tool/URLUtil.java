/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.tool;

import com.google.common.net.InternetDomainName;

import crawler.dtdyq.core.Constant;

import org.apache.hadoop.conf.Configuration;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-23 14:14
 **/
public class URLUtil {
    private static Pattern pattern
        = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;{}]+[-A-Za-z0-9+&@#/%=~_|]");

    /**
     * 获取url的主域名
     * @param url
     * @return
     */
    public static synchronized String getDomainFromUrl(String url){
        String top = "";
        try {
            URL tmp = new URL(url);
            top = tmp.getHost();
            InternetDomainName domainName = InternetDomainName.from(top);
            top = domainName.topPrivateDomain().name();
        } catch (Exception e) {
        }
        return top;
    }

    /**
     * 判断给定url是否是内部链接
     * @param domains
     * @param url
     * @return
     */
    public static synchronized boolean isInnerLink(String[] domains,String url){
        String tmp = getDomainFromUrl(url);
        for(String domain:domains){
            if(domain.equalsIgnoreCase(tmp)){
                return true;
            }
        }
        return false;
    }

    /**
     * 提取初始urls中的所有主域名
     * @param conf
     * @return
     */
    public static synchronized List<String> getDomains(Configuration conf){
        String[] strs = conf.getStrings("crawler.initUrl");
        List<String> res = new ArrayList<String>();
        for(String s:strs){
            String tmp = getDomainFromUrl(s);
            if(!tmp.equals("")){
                res.add(tmp);
            }
        }
        return res;
    }

    /**
     * 判断url是否完整
     * @param surl
     * @return
     */
    public static synchronized boolean urlComplete(String surl){
        try {
            URL url = new URL(surl);
            String host = url.getHost();
            if(host == null){
                return false;
            }
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 判断url是否已经包含在url库中
     * @param url
     * @return
     */
    public static synchronized boolean containUrl(String url){
        return Constant.urlFilter.mightContain(url);
    }

    /**
     * 将url加入url库中
     * @param url
     */
    public static synchronized void addUrl(String url){
        Constant.urlFilter.put(url);
    }

    /**
     * 获取Document中的所有链接
     * @param doc
     * @return
     */
    public static synchronized ArrayList<String> getAllLinks(Document doc){
        ArrayList<String> res = new ArrayList<>();
        Elements href = doc.select("a[href]");
        for(Element ele:href){
            res.add(ele.attr("href"));
        }
        href = doc.select("[src]");
        for(Element ele:href){
            res.add(ele.attr("abs:src"));
        }

        String html = doc.toString();
        Matcher matcher = pattern.matcher(html);

        while(matcher.find()){
            String tmp = matcher.group();
            if(!res.contains(tmp))
                res.add(tmp);
        }
        return res;
    }
}
