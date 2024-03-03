package org.jojo.shell.translate.utils;

import org.jojo.shell.translate.filters.YoudaoDictionaryFilter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author jinxin
 * @version 0.1.0
 * @date 2024/3/2 10:34
 * @since 1.0.0
 */
public class JsonUtil {

    private JsonUtil(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * 解析JSONObject成字符串
     * @param jsonObject JSONObject对象
     * @return 返回解析后的字符串
     */
    public static String parseJSONObject(JSONObject jsonObject,String prefix, Map<String, YoudaoDictionaryFilter> filterMap) {
        StringBuilder returnStr = new StringBuilder();
        StringBuilder prefixBuilder = new StringBuilder(prefix);
        prefixBuilder.append("\t");
        //获取所有key并遍历
        for (String key : jsonObject.keySet()) {
            if(filterMap.containsKey(key) && Boolean.TRUE.equals(filterMap.get(key).needFilter())){
                continue;
            }
            //获取key对应的value
            Object value = jsonObject.get(key);
            //判断value的类型
            if (value instanceof JSONObject v) {
                //如果是JSONObject类型，递归调用parseJSONObject方法
                returnStr.append(prefix).append(key).append(": ").append("\n");
                returnStr.append(parseJSONObject(v,prefixBuilder.toString(),filterMap));
            } else if(value instanceof JSONArray array){
                returnStr.append(prefix).append(key).append(": ").append("\n");
                returnStr.append(parseJSONArray(array,prefixBuilder.toString(),filterMap));
            }else{
                if(filterMap.containsKey(key)){
                    returnStr.append(prefixBuilder).append(filterMap.get(key).getDescription()).append(": ");
                }
                else {
                    returnStr.append(prefixBuilder).append(key).append(": ");
                }
                if(value instanceof ArrayList<?> list){
                    list.forEach(item -> returnStr.append(item).append(", "));
                }else{
                    returnStr.append(value).append("\n");
                }
            }
        }
        return returnStr.toString();
    }

    public static String parseJSONArray(JSONArray jsonArray,String prefix, Map<String,YoudaoDictionaryFilter> filterMap){
        StringBuilder returnJson = new StringBuilder();
        StringBuilder prefixBuilder = new StringBuilder(prefix);
        prefixBuilder.append("\t");
        for (Object item : jsonArray) {
            if (item instanceof JSONObject v) {
                returnJson.append(parseJSONObject(v,prefix,filterMap));
            }
            else if(item instanceof JSONArray array){
                returnJson.append(prefixBuilder).append(parseJSONArray(array,prefix,filterMap));
            }else {
                returnJson.append(prefixBuilder).append(item.toString()).append("\n");
            }
        }
        return returnJson.toString();
    }

    public static String parseJSONObject(JSONObject jsonObject, Map<String, YoudaoDictionaryFilter> filterMap) {
        return parseJSONObject(jsonObject, "", filterMap);
    }
}
