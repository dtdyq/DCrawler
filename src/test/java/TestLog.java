import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;

/**
 * @author dtdyq
 * @create 2018-04-14 17:18
 **/
public class TestLog {
    public static void main(String[] args){
        PropertyConfigurator.configure(new File("target/classes/log4j.properties").getAbsolutePath());
        Logger logger = Logger.getLogger(TestLog.class);
        logger.info("info");
        logger.error("error");
        logger.fatal("fatal");
        logger.debug("debug");
    }
}
