/*
 * 
 */

import mini.crawler.dyq.tool.ConfigUtil;

import org.junit.Test;

/**
 * @author dtdyq
 * @create 2018-04-13 22:49
 **/
public class TestStringUtil {
    @Test
    public void testResolveConfigLine(){
        String s = "alan[final]{desc}=rain";
        System.out.println(ConfigUtil.resolveConfigLine(s));
    }
}
