/*
 * 
 */

package hadoop.crawler.dyq.core;

import java.util.HashMap;
import java.util.Map;

public class ContentType {
    public final static Map<String,String> contentTypeMap = new HashMap<>();
    static {
        contentTypeMap.put("text/html","html");
        contentTypeMap.put("text/xml","html");

        contentTypeMap.put("image/x-icon","icon");
        contentTypeMap.put("application/x-img","img");
        contentTypeMap.put("image/jpeg","jpeg");
        contentTypeMap.put("image/jpg","jpeg");
        contentTypeMap.put("application/x-jpg","jpg");
        contentTypeMap.put("application/x-png","png");
        contentTypeMap.put("image/tiff","tiff");

        contentTypeMap.put("video/mpeg","mp2v");
        contentTypeMap.put("video/mpeg4","mp4");
        contentTypeMap.put("video/mpg","mpeg");
        contentTypeMap.put("video/avi","avi");

        contentTypeMap.put("text/css","css");
        contentTypeMap.put("application/x-javascript","js");

        contentTypeMap.put("application/pdf","pdf");

        contentTypeMap.put("application/json","json");
    }
    public enum DataType{
        HTML,IMG,VDO,JSON,PDF,OTHER,CSS,JS
    }
    public static synchronized DataType getDataType(String contentType){
        switch (contentType){
            case "text/html":
            case "text/xml":return DataType.HTML;
            case "application/pdf":return DataType.PDF;
            case "application/css":return DataType.CSS;
            case "application/js":return DataType.JS;
            case "application/json":return DataType.JSON;
            case "image/x-icon": case "application/x-img":
            case "image/jpeg": case "image/jpg":
            case "application/x-jpg": case "application/x-png":
            case "image/tiff": return DataType.IMG;
            case "video/mpeg": case "video/mpeg4":
            case "video/mpg" : case "video/avi": return DataType.VDO;
            default:return DataType.OTHER;
        }
    }
    public synchronized static String getType(String contentType){
        return contentTypeMap.get(contentType);
    }
}
