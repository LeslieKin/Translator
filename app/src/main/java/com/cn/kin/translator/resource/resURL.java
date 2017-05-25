package com.cn.kin.translator.resource;

/**
 * Created by Kin on 17.5.25.
 */

public class resURL {
    // 有道词典APP KEY
    public static final String YOUDAO_KEY = "940655212";


    // 有道词典BASE URL
    public static final String YOUDAO_URL = "http://fanyi.youdao.com/openapi.do?keyfrom=marktonytranslator";

    // 每日词图地址
    public static final String DAILY_SENTENCE = "http://open.iciba.com/dsapi";

    /**
     * 必应词典base url
     * 拼接参数为：
     * 1. Word，必选，可以为单词或者词组，需要encode
     * 2. Sample，可选，例句功能，默认为true，utf-8编码格式
     */
    public static final String BING_BASE = "http://xtk.azurewebsites.net/BingDictService.aspx";

}
