package com.youdao.aicloud.translate;

import com.youdao.aicloud.translate.utils.AuthV3Util;
import com.youdao.aicloud.translate.utils.HttpUtil;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/** 网易有道智云翻译服务api调用demo api接口: <a href="https://openapi.youdao.com/api">...</a> */
public class TranslateMainApp {

    private static final String APP_KEY = "06161a9345f0830c"; // 您的应用ID
    private static final String APP_SECRET = "b8X7lAa2C08OKq58npOnlsfJjeMsUik2"; // 您的应用密钥

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // 添加请求参数
        Map<String, String[]> params = createRequestParams(args);
        // 添加鉴权相关参数
        AuthV3Util.addAuthParams(APP_KEY, APP_SECRET, params);
        // 请求api服务
        byte[] result = HttpUtil.doPost("https://openapi.youdao.com/api", null, params, "application/json");
        //处理返回结果
        String resultStr = new String(result, StandardCharsets.UTF_8);
        String handleResult = handleResult(resultStr);
        // 打印返回结果
        System.out.println(handleResult);
        System.exit(1);
    }

    /**
     * 处理返回结果
     * @param resultStr
     * @return 可读文本
     */
    public static String handleResult(String resultStr) {
        JSONObject jsonObject = new JSONObject(resultStr);
        String returnJsonStr = "";
        return returnJsonStr;
    }

    private static Map<String, String[]> createRequestParams(String[] args) {
        /*
         * note: 将下列变量替换为需要请求的参数
         * 取值参考文档: https://ai.youdao.com/DOCSIRMA/html/transapi/trans/api/wbfy/index.html#section-9
         */
        String q = args[0];
        String from = "auto";
        String to = "en";
        // String vocabId = "您的用户词表ID";

        return new HashMap<>() {
            {
                put("q", new String[]{q});
                put("from", new String[]{from});
                put("to", new String[]{to});
                // put("vocabId", new String[]{vocabId});
            }
        };
    }
}
