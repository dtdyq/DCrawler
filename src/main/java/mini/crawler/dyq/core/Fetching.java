/*
 * 
 */

package mini.crawler.dyq.core;

import mini.crawler.dyq.conf.Configurable;

/**
 * @author dyq
 * @create 2018-04-14 19:11
 **/
public interface Fetching<T,V> extends Configurable,Transferable<T,V>,Runnable{
}
