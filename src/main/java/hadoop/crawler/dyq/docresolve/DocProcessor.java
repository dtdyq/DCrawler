/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package hadoop.crawler.dyq.docresolve;

import hadoop.crawler.dyq.core.Constant;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileAsBinaryOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-27 20:51
 **/
public class DocProcessor extends Configured implements Tool {
    private Job getDocJob() throws IOException {
        Job job = Job.getInstance(getConf());
        job.setJarByClass(DocProcessor.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(DocumentWritable.class);

        job.setMapperClass(DocMapper.class);
        job.setNumReduceTasks(0);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
        return job;
    }
    @Override
    public int run(String[] strings) throws Exception {
        Job job = getDocJob();
        Configuration conf = getConf();
        String docDir = conf.get("crawler.docDir", Constant.DOC_DIR);
        String urlDir = conf.get("crawler.urlDir",Constant.URL_DIR);
        int deep = conf.getInt("crawler.deep",Constant.CRAWLER_DEEP);
        for(int i = 0;i < deep;i++){
            FileInputFormat.addInputPath(job,new Path(urlDir+"/tmp_"+i+"/p*"));
        }
        FileOutputFormat.setOutputPath(job,new Path(docDir));

        MultipleOutputs.addNamedOutput(job, Constant.NamedOutput.TXT, TextOutputFormat.class,
            NullWritable.class, DocumentWritable.class);
        MultipleOutputs.addNamedOutput(job, Constant.NamedOutput.SEQ, SequenceFileAsBinaryOutputFormat.class,
            NullWritable.class, DocumentWritable.class);

        if(job.waitForCompletion(true)){
            return 0;
        }else{
            return 1;
        }
    }
}
