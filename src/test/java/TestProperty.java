import org.junit.Test;

import java.util.Properties;

/**
 * @author dtdyq
 * @create 2018-04-14 22:17
 **/
public class TestProperty {
    @Test
    public void test(){
        Properties properties = new Properties();
        properties.setProperty("java","first");
        properties.setProperty("alan","{java}-great");
        System.out.println(properties.get("alan"));
    }
}
