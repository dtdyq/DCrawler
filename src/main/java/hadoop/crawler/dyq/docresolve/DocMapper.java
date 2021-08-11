/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.docresolve;

import crawler.dtdyq.core.Constant;
import crawler.dtdyq.core.ContentType;
import crawler.dtdyq.core.DefaultProcessor;
import crawler.dtdyq.core.Processable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-27 16:40
 **/
public class DocMapper extends Mapper<Text,Text,NullWritable,DocumentWritable>{
    private MultipleOutputs<NullWritable,DocumentWritable> multipleOutputs;
    private boolean ignoreContentType;
    private int maxBodySize;
    private Processable processable;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        Configuration conf = context.getConfiguration();
        multipleOutputs = new MultipleOutputs<>(context);
        ignoreContentType = conf.getBoolean("crawler.ignoreContentType", Constant.IGNORE_CONTENT_TYPE);
        maxBodySize = conf.getInt("crawler.maxBodySize",Constant.MAX_BODY_SIZE);
        Class<?> clazz;
        try {
            clazz = Class.forName(conf.get("crawler.processClass"));
        } catch (Exception e) {
            clazz = DefaultProcessor.class;
        }
        try {
            processable = (Processable) clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {

        }
    }

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String url = value.toString();

        if(processable.shouldProcess(url)){
            Connection.Response response = null;
            try {
                 response = Jsoup.connect(url)
                    .maxBodySize(maxBodySize).timeout(0)
                     .ignoreHttpErrors(true)
                     .method(Connection.Method.GET).ignoreContentType(ignoreContentType).execute();
            }catch (Exception e){}
            if(response!=null && response.statusCode() == 200) {
                DocumentWritable doc;
                try {
                    doc = processable.process(response);
                } catch (Exception e) {
                    doc = new DocumentWritable("<html>none</html>".getBytes(), "missing");
                }
                processOutput(url,doc,multipleOutputs);
                doc = null;
            }
        }
    }
    private String generateName(String  preffix, String url, String suffix){
        StringBuilder stringBuilder = new StringBuilder(preffix);
        stringBuilder.append("/");
        url = url.replace("/","\\").replace(" ","_");
        stringBuilder.append(url).append("-").append(suffix);
        return stringBuilder.toString();
    }
    private void processOutput(String url,DocumentWritable doc, MultipleOutputs<NullWritable, DocumentWritable> multipleOutputs) throws IOException, InterruptedException {
        String contentType = doc.getContentType();
        ContentType.DataType dataType = ContentType.getDataType(contentType);
        String type = ContentType.getType(contentType);
        switch (dataType){
            case HTML:{
                multipleOutputs.write(Constant.NamedOutput.TXT,NullWritable.get(),
                    doc.getUTF8(),generateName("/html",url,type));
            }break;
            case JSON:{
                multipleOutputs.write(Constant.NamedOutput.TXT,NullWritable.get(),
                    doc.getUTF8(),generateName("/json",url,type));
            }break;
            case JS:{
                multipleOutputs.write(Constant.NamedOutput.TXT,NullWritable.get(),
                    doc.getUTF8(),generateName("/js",url,type));
            }break;
            case CSS:{
                multipleOutputs.write(Constant.NamedOutput.TXT,NullWritable.get(),
                    doc.getUTF8(),generateName("/css",url,type));
            }break;
            case IMG:{
                multipleOutputs.write(Constant.NamedOutput.SEQ,NullWritable.get(),
                    doc.getBytes(),generateName("/img",url,type));
            }break;
            case PDF:{
                multipleOutputs.write(Constant.NamedOutput.SEQ,NullWritable.get(),
                    doc.getBytes(),generateName("/pdf",url,type));
            }break;
            case VDO:{
                multipleOutputs.write(Constant.NamedOutput.SEQ,NullWritable.get(),
                    doc.getBytes(),generateName("/video",url,type));
            }break;
            case OTHER:{
                multipleOutputs.write(Constant.NamedOutput.SEQ,NullWritable.get(),
                    doc.getBytes(),generateName("/other",url,type));
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}
