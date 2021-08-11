/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

import mini.crawler.dyq.conf.Configuration;

import java.io.IOException;

/**
 * @author dyq
 * @create 2018-04-14 19:17
 **/
public class MimeUtil {
    private static Configuration configuration;
    static {
        try {
            configuration = new Configuration("mime.config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String extension(String contentType){
        return configuration.getString(contentType,"other");
    }

}
