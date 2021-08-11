/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dyq
 * @create 2018-04-14 23:09
 **/
public class Tuple_4<A,B,C,D> implements Serializable{
    public A _1;
    public B _2;
    public C _3;
    public C _4;
    public Tuple_4(){};
    public Tuple_4(A _1, B _2, C _3, C _4) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
    }

    @Override
    public int hashCode() {
        return _1.hashCode() & _2.hashCode() & _3.hashCode() & _4.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tuple_4){
            Tuple_4 other = (Tuple_4) obj;
            return Objects.equals(_1,other._1) && Objects.equals(_2,other._2) &&
                Objects.equals(_3,other._3) && Objects.equals(_4,other._4);
        }
        return false;
    }

}
