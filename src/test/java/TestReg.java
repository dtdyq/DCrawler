import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author dtdyq
 * @create 2018-04-13 20:30
 **/
public class TestReg {
    @Test
    public void test(){
        String s = "https?.*";
        Pattern pattern = Pattern.compile(s);
        System.out.println(pattern.matcher("https://www.bau.co").matches());
    }
}
