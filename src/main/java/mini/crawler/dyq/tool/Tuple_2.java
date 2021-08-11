/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author dyq
 * @create 2018-04-13 22:19
 **/
public class Tuple_2<T,V> implements Serializable{
    public T _1 = null;
    public V _2 = null;
    public Tuple_2(){}
    public Tuple_2(T _1, V _2){
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return "["+_1+","+_2+"]";
    }

    @Override
    public int hashCode() {
        return _1.hashCode() & _2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tuple_2){
           Tuple_2 tmp = (Tuple_2) obj;
           return Objects.equals(_1,tmp._1) && Objects.equals(_2,tmp._2);
        }
        return false;
    }
}
