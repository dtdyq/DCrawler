
/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

import mini.crawler.dyq.conf.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author dtdyq
 * @create 2018-04-14 19:19
 **/
public class TestMime {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/mime.config");
        List<String> list = Resource.getLines("temp.txt");
        list.stream().map(s->s.replaceAll("\\s+","="))
            .forEach(s-> {
                try {
                    Files.write(Paths.get(file.toURI()),(s+"\n").getBytes(),StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
