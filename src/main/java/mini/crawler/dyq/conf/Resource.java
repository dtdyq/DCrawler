/*
 * 
 */

package mini.crawler.dyq.conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 资源获取类，可以通过该类获取web资源、本地文件系统资源、类路径资源等
 * @author dyq
 * @create 2018-04-13 20:08
 **/
public class Resource {
    private static final Pattern CLASSPATH_PATTERN = Pattern.compile("classpath:.*",Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_PATTERN = Pattern.compile("https?://.*",Pattern.CASE_INSENSITIVE);
    private static final Pattern FILE_PATTERN = Pattern.compile("[file:|c:|d:|e:|f:|g:|\\.].*",Pattern.CASE_INSENSITIVE);

    public static List<String> getLines(String dir) throws IOException {
        dir = dir.trim();

        if(CLASSPATH_PATTERN.matcher(dir).matches()){
            return loadClasspathResource(dir);
        }
        if(URL_PATTERN.matcher(dir).matches()){
            return loadURLResource(new URL(dir));
        }
        if(FILE_PATTERN.matcher(dir).matches()){
            if(dir.substring(0,4).equalsIgnoreCase("file")){
                dir = dir.substring("file:///".length());
            }
            if(new File(dir).exists())
                return Files.lines(Paths.get(dir)).collect(Collectors.toList());
        }
        return loadClasspathResource(dir);
    }
    private static List<String> loadClasspathResource(String dir) throws IOException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(dir.length()>=10 && dir.substring(0,9).equalsIgnoreCase("classpath")){
            dir = dir.substring(10);
        }
        URL url = loader.getResource(dir);

        return loadURLResource(url);
    }
    private static List<String> loadURLResource(URL url) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),Charset.forName("utf-8")));
        String line;
        while((line = br.readLine())!=null){
            result.add(line);
        }
        br.close();
        return result;
    }
}
