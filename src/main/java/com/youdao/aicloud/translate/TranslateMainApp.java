package com.youdao.aicloud.translate;

import com.youdao.aicloud.translate.filters.YoudaoDictionaryFilter;
import com.youdao.aicloud.translate.utils.AuthV3Util;
import com.youdao.aicloud.translate.utils.HttpUtil;
import com.youdao.aicloud.translate.utils.JsonUtil;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** 网易有道智云翻译服务api调用demo api接口: <a href="https://openapi.youdao.com/api">...</a> */
public class TranslateMainApp {

    public static final List<YoudaoDictionaryFilter> filters = new ArrayList<>();

    static {
        filters.add(new YoudaoDictionaryFilter("","pic_dict",true));
    }

    private static final String APP_KEY = "06161a9345f0830c"; // 您的应用ID
    private static final String APP_SECRET = "b8X7lAa2C08OKq58npOnlsfJjeMsUik2"; // 您的应用密钥

    public static void main(String[] args) throws NoSuchAlgorithmException {
        //检查参数
        checkParams(args);

        if(args.length==1){
            System.out.println(useYoudaoDictionary(args, filters));
            System.exit(1);
        }
        if(args.length>=2){
            if("1".equals(args[1])){
                System.out.println(useYoudaoTranslate(args));
                System.exit(1);
            }
            if("2".equals(args[1])){
                System.out.println(useYoudaoDictionary(args, filters));
                System.exit(1);
            }
        }
    }

    static void checkParams(String[] args){
        if(args==null || args.length==0){
            System.out.println("error params.");
            System.exit(1);
        }else{
            if(args.length>=2){
                try {
                    int arg1 = Integer.parseInt(args[1]);
                    if (arg1>2){
                        throw new NumberFormatException();
                    }
                }catch (NumberFormatException e){
                    System.out.println("error params. The second params must be 1 or 2. 1 for YoudaoTranslate, 2 for YoudaoDictionary.");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * 使用有道翻译
     * @param args 参数
     * @return 有道翻译结果
     * @throws NoSuchAlgorithmException 鉴权参数异常
     */
    public static String useYoudaoTranslate(String[] args) throws NoSuchAlgorithmException {
        // 添加请求参数
        Map<String, String[]> params = createRequestParamsForTranslate(args);
        // 添加鉴权相关参数
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] bytes = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String useYoudaoDictionary(String[] args, List<YoudaoDictionaryFilter> filters) {
        // 添加请求参数
        Map<String, String> params = createRequestParams(args);
        // 请求api服务
        byte[] bytes = HttpUtil.doPost("https://dict.youdao.com/jsonapi_s?doctype=json&jsonversion=4", null, params, "application/json","");
        return handleResult(new String(bytes, StandardCharsets.UTF_8));
    }

    public static String handleResult(String resultStr) {
        //处理filters
        Map<String, YoudaoDictionaryFilter> filterMap = filters.stream().collect(Collectors.toMap(YoudaoDictionaryFilter::getFieldName, Function.identity()));
        JSONObject jsonObject = new JSONObject(resultStr);
        return JsonUtil.parseJSONObject(jsonObject,filterMap);
    }

    /**
     * 创建参数
     * @param args 传入参数
     * @return http请求参数
     */
    private static Map<String, String> createRequestParams(String[] args) {

        Map<String, String> returnMap= new HashMap<>();
        /*
         * 取值参考文档:
         *  有道翻译: https://ai.youdao.com/DOCSIRMA/html/transapi/trans/api/wbfy/index.html#section-9
         *  有道词典: https://ai.youdao.com/DOCSIRMA/html/dictionary/api/ydcd/index.html
         */
        returnMap.put("q", args[0]);

        if(args.length==1 || "2".equals(args[1])){
            returnMap.put("le","en");
            returnMap.put("t","1");
            returnMap.put("client","web");
            returnMap.put("keyfrom","webdict");
        }
        return returnMap;
    }
    private static Map<String, String[]> createRequestParamsForTranslate(String[] args) {

        Map<String, String[]> returnMap= new HashMap<>();
        /*
         * 取值参考文档:
         *  有道翻译: https://ai.youdao.com/DOCSIRMA/html/transapi/trans/api/wbfy/index.html#section-9
         *  有道词典: https://ai.youdao.com/DOCSIRMA/html/dictionary/api/ydcd/index.html
         */
        String q = args[0];
        String from = "auto";
        String to = "en";

        returnMap.put("q", new String[]{q});
        returnMap.put("from", new String[]{from});
        returnMap.put("to", new String[]{to});

        return returnMap;
    }

}
