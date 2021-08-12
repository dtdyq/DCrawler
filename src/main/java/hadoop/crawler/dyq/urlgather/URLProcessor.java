/*
 * 
 */

package hadoop.crawler.dyq.urlgather;

import hadoop.crawler.dyq.core.Constant;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * @author hadoop.crawler.dyq
 * @create 2018-03-23 15:23
 **/
public class URLProcessor extends Configured implements Tool{

    public Job getUrlJob() throws IOException {

        Job job = Job.getInstance(getConf());
        job.setJarByClass(URLProcessor.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setMapperClass(URLMapper.class);
        job.setNumReduceTasks(0);

        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        return job;
    }

    public int run(String[] strings) throws Exception {
        Configuration conf = getConf();

        FileSystem fs = FileSystem.get(conf);
        String[] urls = conf.getStrings("crawler.initUrl");
        String urlDir = conf.get("crawler.urlDir", Constant.URL_DIR);

        Path initUrlDir = new Path(Constant.INIT_URL_DIR);
        FSDataOutputStream out = fs.create(initUrlDir,true);
        int len = urls.length;
        for(int i = 0;i < len;i++){
            out.write((i+"\t"+urls[i]+"\n").getBytes());
        }
        out.close();
        fs.delete(new Path(urlDir),true);

        int deep = getConf().getInt("crawler.deep", Constant.CRAWLER_DEEP);
        int res = 0;
        for(int i = 0;i < deep;i++){
            Job tmp = getUrlJob();
            if(i == 0) {
                FileInputFormat.setInputPaths(tmp, new Path(Constant.INIT_URL_DIR));
            }else{
                FileInputFormat.setInputPaths(tmp,new Path(urlDir+"/tmp_"+(i-1)));
            }
            FileOutputFormat.setOutputPath(tmp, new Path(urlDir+"/tmp_"+i));
            if(tmp.waitForCompletion(true)){
                res = 0;
            }else{
                res = 1;
            }
        }

        return res;
    }
}
