/*
 * 
 */

package mini.crawler.dyq.tool;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dyq
 * @create 2018-04-13 23:11
 **/
public class Tuple_3<K,T,V> implements Serializable{
    public K _1;
    public T _2;
    public V _3;
    public Tuple_3(){}
    public Tuple_3(K _1, T _2, V _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Override
    public int hashCode() {
        return _1.hashCode() & _2.hashCode() & _3.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tuple_3){
            Tuple_3 tmp = (Tuple_3) obj;
            return Objects.equals(_1,tmp._1) && Objects.equals(_2,tmp._2) && Objects.equals(_3,tmp._3);
        }
        return false;
    }

    @Override
    public String toString() {
        return "["+_1+","+_2+","+_3+"]";
    }
}
