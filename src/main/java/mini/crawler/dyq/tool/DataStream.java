/*
 * 
 */

package mini.crawler.dyq.tool;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author dyq
 * @create 2018-04-15 14:00
 **/
public class DataStream<T>{
    private ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
    public boolean empty(){
        return queue.isEmpty();
    }
    public T read(){
        return queue.poll();
    }
    public void write(T val){
        queue.offer(val);
    }
    public int size(){
        return queue.size();
    }
}
