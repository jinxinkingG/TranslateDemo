package org.jojo.shell.translate.constant;

import org.jojo.shell.translate.filters.YoudaoFilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class YoudaoTranslateFilterMapConstant {

    private YoudaoTranslateFilterMapConstant() throws IllegalAccessException {
        throw new IllegalAccessException("YoudaoTranslateFilterMapConstant is a Constant class");
    }
    private static final Map<String, YoudaoFilter> youdaoTranslateFilterMap = initMap();

    private static Map<String, YoudaoFilter> initMap(){
        Map<String, YoudaoFilter> map = new HashMap<>();
        map.put("pic_dict", new YoudaoFilter("", "pic_dict", true));
        return Collections.unmodifiableMap(map);
    }

    public static Map<String, YoudaoFilter> getFilters(){
        return youdaoTranslateFilterMap;
    }
}
