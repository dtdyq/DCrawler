/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

import mini.crawler.dyq.conf.Configuration;

import org.junit.Test;

import java.io.IOException;

/**
 * @author dtdyq
 * @create 2018-04-13 22:59
 **/
public class TestConfiguration {
    @Test
    public void test() throws IOException {
        Configuration configuration = new Configuration("config1.txt");
        configuration.addResource("config2.txt");
        System.out.println(configuration.getDescription("alan"));
    }
}
