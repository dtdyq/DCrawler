/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

import mini.crawler.dyq.conf.Resource;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author dtdyq
 * @create 2018-04-13 21:19
 **/
public class TestResource {
    @Test
    public void test() throws IOException {
        List<String> lines = Resource.getLines("./resource.txt");
        System.out.println(lines.size());
        lines.forEach(System.out::println);

        System.out.println(Integer.MAX_VALUE);
    }
}
