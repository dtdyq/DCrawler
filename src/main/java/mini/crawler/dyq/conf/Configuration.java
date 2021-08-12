/*
 * 
 */

package mini.crawler.dyq.conf;

import mini.crawler.dyq.tool.ConfigUtil;
import mini.crawler.dyq.tool.Tuple_2;
import mini.crawler.dyq.tool.Tuple_3;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * support config module:
 * key=value
 * key:value
 * key value
 * key    value
 * key[final]:attr which cannot be change
 * the pre line which start with # is the property's notation
 * @author dyq
 * @create 2018-04-13 22:07
 **/
public class Configuration {
    private Resource resource;
    private Map<String, Tuple_3<String,Boolean,String>> config;
    public Configuration(){
        config = new TreeMap<>();
    }
    public Configuration(String resource) throws IOException {
        this();
        loadResource(resource);
    }
    public Configuration loadResource(String resource) throws IOException {
        config = new HashMap<>();
        addResource(resource);
        return this;

    }
    public Configuration addResource(String resource) throws IOException {
        List<String> lines = Resource.getLines(resource);

        StringBuilder pre = new StringBuilder();
        for(String line:lines){
            if(line == null || Pattern.matches("\\s+",line)) continue;
            if(line.startsWith("#")) pre.append(line.substring(1));
            Tuple_2<String,Tuple_3<String,Boolean,String>> v = ConfigUtil.resolveConfigLine(line);
            if(v == null) continue;
            v._2._3 = pre.toString();
            pre = new StringBuilder();
            if(!config.containsKey(v._1) || !config.get(v._1)._2)
                config.put(v._1,v._2);
        }
        return this;
    }
    public Configuration set(String key,String value,Boolean isFinal){
        if(!config.containsKey(key) || !config.get(key)._2)
            this.config.put(key,new Tuple_3<>(value,isFinal,"none"));
        return this;
    }
    public Configuration addDescrition(String key ,String description){
        if(checkKey(key)){
            config.get(key)._3 = description;
        }
        return this;
    }
    public String getString(String key,String defaultValue){
        if(checkKey(key))
            return config.get(key)._1;
        return defaultValue;
    }
    public String[] getStrings(String key,String[] defaultValue){
        if(!checkKey(key)) return defaultValue;
        String tmp = config.get(key)._1;
        String[] result;
        if(tmp.contains(" ")){
            result = tmp.split(" ");
        }else if(tmp.contains(",")){
            result = tmp.split(",");
        }else {
            result = new String[]{tmp};
        }
        return result;
    }
    public int getInt(String key,int defaultValue){
        if(checkKey(key)){
            return Integer.parseInt(config.get(key)._1);
        }
        return defaultValue;
    }
    public long getLong(String key,long defaultValue){
        if(checkKey(key)){
            return Long.parseLong(config.get(key)._1);
        }
        return defaultValue;
    }
    public Class<?> getClass(String key,Class<?> defaultValue) throws ClassNotFoundException {
        if(checkKey(key)){
            return Class.forName(config.get(key)._1);
        }
        return defaultValue;
    }
    public String getDescription(String key){
        if(checkKey(key))
            return config.get(key)._3;
        return null;
    }
    public boolean isFinal(String key){
        if(checkKey(key))
            return config.get(key)._2;
        return false;
    }
    public boolean getBoolean(String key,boolean defaultValue){
        if(checkKey(key)){
            String tmp = config.get(key)._1;
            if(tmp.equals("true")){
                return true;
            }else{
                return false;
            }
        }
        return defaultValue;
    }
    private boolean checkKey(String key){
        return key!=null && config.containsKey(key);
    }

    public void set(String urlSeed, String seeds) {
        set(urlSeed,seeds,false);
    }
}
