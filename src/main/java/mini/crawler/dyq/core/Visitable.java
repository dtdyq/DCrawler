/*
 * 
 */

package mini.crawler.dyq.core;

import mini.crawler.dyq.conf.Configurable;

/**
 * @author dyq
 * @create 2018-04-15 20:02
 **/
public interface Visitable<T> extends Configurable {
    boolean shouldVisit(String url);
    void visit(T document);
}
