package com.youdao.aicloud.translate.enums;

/**
 * @author jinxin
 * @version 0.1.0
 * @date 2024/3/2 00:16
 * @since 1.0.0
 */
public enum YoudaoTranslateParams {

    Q("q", "要翻译的文本"),
    FROM("from", "源语言"),

    TO("to", "目标语言");

    private final String key;
    private final String description;

    private YoudaoTranslateParams(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey(){
        return this.key;
    }

    public String getDescription(){
        return this.description;
    }
}
