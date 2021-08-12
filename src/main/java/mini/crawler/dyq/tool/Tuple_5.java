/*
 * 
 */

package mini.crawler.dyq.tool;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dyq
 * @create 2018-04-14 23:13
 **/
public class Tuple_5<A,B,C,D,E> implements Serializable{
    public A _1;
    public B _2;
    public C _3;
    public D _4;
    public E _5;
    public Tuple_5(){}

    public Tuple_5(A _1, B _2, C _3, D _4, E _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
    }

    @Override
    public int hashCode() {
        return _1.hashCode() & _2.hashCode() & _3.hashCode() & _4.hashCode() & _5.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tuple_5){
            Tuple_5 other = (Tuple_5) obj;
            return Objects.equals(_1,other._1) && Objects.equals(_2,other._2) &&
                Objects.equals(_3,other._3) && Objects.equals(_4,other._4) && Objects.equals(_5,other._5);
        }
        return false;
    }
}
