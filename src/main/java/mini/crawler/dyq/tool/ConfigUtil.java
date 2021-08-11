/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package mini.crawler.dyq.tool;

/**
 * @author dyq
 * @create 2018-04-13 22:20
 **/
public class ConfigUtil {
    /**
     * resolve configuration line to key and value
     * @see minic.dtdyq.conf.Configuration
     * @param line
     * @return
     */
    public static Tuple_2<String,Tuple_3<String,Boolean,String>> resolveConfigLine(String line){
        line = line.trim();

        String[] tmp = new String[2];
        if(line.contains("=")){
            tmp[0] = line.substring(0,line.indexOf("="));
            tmp[1] = line.substring(line.indexOf("=")+1);
        }
        if(tmp[0]!=null && tmp[1] != null){
            String tmpKey = tmp[0];
            String value = tmp[1].trim();

            Tuple_2<String,Boolean> key = keyResolve(tmpKey);
            return new Tuple_2<>(key._1,new Tuple_3<>(value,key._2,"none"));

        }
        return null;
    }

    /**
     * resolve key to different part
     * eg:key[final] to Tuple_2:[key,true]
     * @param key
     */
    private static Tuple_2<String,Boolean> keyResolve(String key){
        key = key.trim();
        Tuple_2<String,Boolean> result = new Tuple_2<>();
        if(key.endsWith("[final]")){
            result._1 = key.substring(0,key.length()-7);
            result._2 = true;
        }else{
            result._2 = false;
            result._1 = key;
        }
        return result;
    }
}
