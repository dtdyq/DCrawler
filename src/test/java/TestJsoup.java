import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author dtdyq
 * @create 2018-04-15 21:01
 **/
public class TestJsoup {
    @Test
    public void test() throws IOException {
        //text/html; charset=utf-8
        Connection.Response response = Jsoup.connect("https://www.douban.com").ignoreContentType(true).execute();
        System.out.println(response.contentType());
        System.out.println(response.charset());

        for(Map.Entry<String,String> e:response.headers().entrySet()){
            System.out.println(e.getKey()+"----"+e.getValue());
        }
        File file = new File("e:/test.html");
        file.createNewFile();
        BufferedOutputStream out = new BufferedOutputStream(
            new FileOutputStream(file)
        );
        out.write(response.bodyAsBytes());
    }
}
