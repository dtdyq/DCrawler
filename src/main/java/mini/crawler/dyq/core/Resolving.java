/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.core;

import mini.crawler.dyq.conf.Configurable;
import mini.crawler.dyq.jsoupimp.JsoupVisitable;

/**
 * @author dyq
 * @create 2018-04-15 19:13
 **/
public interface Resolving<T,V> extends Transferable<T,V>,Runnable, Configurable {
    void setVisitable(JsoupVisitable v);
}
