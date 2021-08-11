/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.core;

import hadoop.crawler.dyq.docresolve.DocumentWritable;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-26 16:19
 **/
public class DefaultProcessor implements Processable {
    @Override
    public boolean shouldProcess(String url) {
        return true;
    }

    @Override
    public DocumentWritable process(Connection.Response response) throws IOException {
        Document doc = response.parse();
        DocumentWritable res = new DocumentWritable();
        String contentType = response.contentType();
        if(contentType.indexOf("; ")!=-1){
            contentType = contentType.split("; ")[0];
        }
        res.contentType(contentType);
        ByteBuffer bb = Charset.forName("utf-8").encode(doc.toString());
        res.data(bb.array());
        return res;
    }
}
