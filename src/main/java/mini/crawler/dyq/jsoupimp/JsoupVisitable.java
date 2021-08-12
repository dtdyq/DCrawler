/*
 * 
 */

package mini.crawler.dyq.jsoupimp;

import mini.crawler.dyq.conf.Configuration;
import mini.crawler.dyq.core.Constant;
import mini.crawler.dyq.core.Visitable;
import mini.crawler.dyq.tool.MimeUtil;
import mini.crawler.dyq.tool.StringUtil;
import mini.crawler.dyq.tool.Tuple_3;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author dyq
 * @create 2018-04-15 20:22
 **/
public class JsoupVisitable implements Visitable<Tuple_3<String,byte[],String>> {
    private Logger logger = Logger.getLogger(JsoupVisitable.class);

    private String storeDir;

    private String contentType(String tmp){

        String result;
        if(tmp.contains("; ")){
            result = tmp.split("; ")[0];
        }else if(tmp.contains(";")){
            result = tmp.split(";")[0];
        }else{
            result = tmp;
        }
        return result;
    }
    @Override
    public boolean shouldVisit(String url) {
        return true;
    }

    @Override
    public void visit(Tuple_3<String,byte[],String> document) {
        if(document._2.length == 0)return;

        logger.debug("visiting document:"+document._1);
        String url = document._1;
        String contentType = contentType(document._3);
        String extension = MimeUtil.extension(contentType);

        Path path = Paths.get(storeDir + "/" +extension);
        logger.debug("create the path:"+path.toString());
        if(!Files.exists(path)){
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path file = Paths.get(path.toString(),generateName(url,extension));
        logger.debug("write to file:"+file);
        logger.debug("response data size:"+document._2.length);
        try {
            Files.copy(new ByteArrayInputStream(document._2),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.debug("durable document finish");
    }
    private String generateName(String url,String extension){
        return StringUtil.getDomainFromUrl(url)+"_"+System.currentTimeMillis()+"."+extension;
    }
    @Override
    public void configure(Configuration conf) throws Exception {
        String rootDir = conf.getString(Constant.Global.ROOT_DIR,Constant.Global.DEFAULT_ROOT_DIR);
        storeDir = rootDir+conf.getString(Constant.Global.DATA_STORE_DIR,Constant.Global.DEFAULT_DATA_STORE_DIR);
    }
}
