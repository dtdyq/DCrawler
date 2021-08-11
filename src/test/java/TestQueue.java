import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author dtdyq
 * @create 2018-04-15 13:21
 **/
public class TestQueue {
    @Test
    public void test() throws IOException, InterruptedException {

    }
    @Test
    public void testQueue() throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        queue.offer("java");
        queue.offer("python");
        queue.offer("cpp");
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }
}
