/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.urlgather;

import hadoop.crawler.dyq.core.Constant;
import hadoop.crawler.dyq.tool.URLUtil;
import mini.crawler.dyq.tool.StringUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-23 12:15
 **/
public class URLMapper extends Mapper<Text,Text,Text,Text> {
    private boolean ignoreContentType;
    private int maxBodySize;
    private boolean onlyInnerLink;
    private String[] domains;
    private Document doc;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        Configuration configuration = context.getConfiguration();
        //��ȡ���ò���
        ignoreContentType = configuration.getBoolean("crawler.ignoreContentType", Constant.IGNORE_CONTENT_TYPE);
        maxBodySize = configuration.getInt("crawler.maxBodySize",Constant.MAX_BODY_SIZE);
        onlyInnerLink = configuration.getBoolean("crawler.onlyInnerLink",Constant.ONLY_INNER_LINK);
        domains = configuration.getStrings("crawler.domains");

    }

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String url = value.toString();
        if(Constant.NOT_NEED_RESOLVE.matcher(url).matches()){
            return;
        }
        //��ȡ�����ĵ�
        try {
            doc = Jsoup.connect(url)
                .maxBodySize(maxBodySize).ignoreContentType(ignoreContentType).get();
        }catch(Exception e){
            doc = Jsoup.parse("<html>none</html>");
        }

        //��ȡ�ĵ���������
        ArrayList<String> links = URLUtil.getAllLinks(doc);

        for(String link:links){

            if(!URLUtil.urlComplete(link)){
                link = StringUtil.connectUrl(url,link);
            }
            //�������ڵ�url���뵽��¡�������У�����url�������
            if(URLUtil.containUrl(link))
                continue;
            URLUtil.addUrl(link);

            if (onlyInnerLink) {
                if (URLUtil.isInnerLink(domains,link)) {
                    context.write(key,new Text(link));
                }
            } else {
                context.write(key,new Text(link));
            }
        }

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        super.cleanup(context);

    }
}
