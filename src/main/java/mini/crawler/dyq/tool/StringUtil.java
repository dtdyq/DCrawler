/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

import com.google.common.net.InternetDomainName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dyq
 * @create 2018-04-16 13:03
 **/
public class StringUtil {
    private static Pattern pattern
        = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;{}]+[-A-Za-z0-9+&@#/%=~_|]");

    /**
     * get the url's domain
     * @param url
     * @return
     */
    public static synchronized String getDomainFromUrl(String url){
        String top = "UNKNOW";
        try {
            URL tmp = new URL(url);
            top = tmp.getHost();
            InternetDomainName domainName = InternetDomainName.from(top);
            top = domainName.topPrivateDomain().toString();
        } catch (Exception e) {
        }
        return top;
    }

    /**
     * get all links from Document
     * @param document
     * @return
     */
    public static List<String> getJsoupDocLinks(Tuple_3<String,byte[],String> document) throws IOException {
        List<String> res = new ArrayList<>();
        String html = new String(document._2);
        Document doc = Jsoup.parse(html);

        Elements href = doc.select("a[href]");
        for(Element ele:href){
            String tmp = ele.attr("href");
            if((!tmp.startsWith("http://")) && (!tmp.startsWith("https://"))){
                tmp = connectUrl(document._1,tmp);
            }
            res.add(tmp);
        }
        href = doc.select("[src]");
        for(Element ele:href){
            String tmp = ele.attr("abs:src");
            if((!tmp.startsWith("http://")) && (!tmp.startsWith("https://"))){
                tmp = connectUrl(document._1,tmp);
            }
            res.add(tmp);
        }

        Matcher matcher = pattern.matcher(html);

        while(matcher.find()){
            String tmp = matcher.group();
            if(!res.contains(tmp))
                res.add(tmp);
        }
        return res;
    }

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
