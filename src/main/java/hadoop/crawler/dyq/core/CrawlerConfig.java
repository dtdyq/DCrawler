/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.core;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;

import java.util.List;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-24 12:54
 **/
public class CrawlerConfig implements Configurable{
    private Configuration conf = new Configuration();

    public void setConf(Configuration configuration) {
        this.conf = configuration;
    }

    public Configuration getConf() {
        return conf;
    }

    /**
     * ������ȡ���
     * @param deep
     * @return
     */
    public CrawlerConfig setCrawlerDeep(int deep){
        conf.setInt("crawler.deep",deep);
        return this;
    }

    /**
     * �������й���Ŀ¼�ĸ�·��
     * @param rootDir
     * @return
     */
    public CrawlerConfig setRootDir(String rootDir){
        conf.set("crawler.rootDir",rootDir);
        return this;
    }

    /**
     * ����ץȡ����url�洢·��
     * @param urlDir
     * @return
     */
    public CrawlerConfig setUrlDir(String urlDir){
        this.conf.set("crawler.urlDir",urlDir);
        return this;
    }

    /**
     * ���ץȡ����Document��������Ϊ��洢Ŀ¼
     * @param docDir
     * @return
     */
    public CrawlerConfig setDocDir(String docDir){
        this.conf.set("crawler.docDir",docDir);
        return this;
    }

    /**
     * �������ӳ�ʱ����
     * @param timeout
     * @return
     */
    public CrawlerConfig setTimeout(int timeout){
        this.conf.setInt("crawler.timeout",timeout);
        return this;
    }

    /**
     * ���������ʼ����
     * @param initUrl
     * @return
     */
    public CrawlerConfig setInitUrl(String[] initUrl){
        this.conf.setStrings("crawler.initUrl",initUrl);
        return this;
    }

    /**
     * �����Ƿ������ҳ����
     * @param b
     * @return
     */
    public CrawlerConfig setIgnoreContentType(boolean b){
        this.conf.setBoolean("crawler.ignoreContentType",b);
        return this;
    }

    /**
     * ����ץȡҳ��Ĵ�С
     * @param size
     * @return
     */
    public CrawlerConfig setMaxBodySize(int size){
        this.conf.setInt("crawler.maxBodySize",size);
        return this;
    }

    /**
     * �����Ƿ�ֻץȡ�ڲ�����(ͬһ��������)
     * @param b
     * @return
     */
    public CrawlerConfig setOnlyInnerLink(boolean b){
        this.conf.setBoolean("crawler.onlyInnerLink",b);
        return this;
    }

    /**
     * ����url�ͽ��������
     * @param clazz
     * @return
     */
    public CrawlerConfig setProcessClass(String clazz){
        this.conf.set("crawler.processClass",clazz);
        return this;
    }

    /**
     * ����������
     * @param domains
     */
    public void setDomains(List<String> domains){
        String[] tmp = new String[domains.size()];
        tmp = domains.toArray(tmp);
        conf.setStrings("crawler.domains",tmp);
    }
}
