package com.cn.kin.translator.resource;

/**
 * Created by Kin on 17.5.25.
 */

public class RESURL {

    // 每日词图地址,来自金山词霸
    public static final String DAILY_SENTENCE = "http://open.iciba.com/dsapi";

//    Demo
//    {
//        "sid":"2625",
//            "tts":"http:\/\/news.iciba.com\/admin\/tts\/2017-06-11-day",
//            "content":"It's okay to have flaws, which make you real.",
//            "note":"\u6709\u70b9\u7f3a\u70b9\u6ca1\u5173\u7cfb\uff0c\u8fd9\u6837\u624d\u771f\u5b9e\u3002",
//            "love":"2936",
//            "translation":"\u8bcd\u9738\u5c0f\u7f16\uff1a\u6211\u7684\u4f18\u70b9\u662f\uff1a\u6211\u5f88\u5e05\u3002\u6211\u7684\u7f3a\u70b9\u662f\uff1a\u6211\u5e05\u7684\u4e0d\u660e\u663e\u3002\u2014\u2014\u5f20\u4f1f\u300a\u7231\u60c5\u516c\u5bd3\u300b",
//            "picture":"http:\/\/cdn.iciba.com\/news\/word\/20170611.jpg",
//            "picture2":"http:\/\/cdn.iciba.com\/news\/word\/big_20170611b.jpg",
//            "caption":"\u8bcd\u9738\u6bcf\u65e5\u4e00\u53e5",
//            "dateline":"2017-06-11","s_pv":"0","sp_pv":"0",
//            "tags":[{"id":null,"name":null}],
//        "fenxiang_img":"http:\/\/cdn.iciba.com\/web\/news\/longweibo\/imag\/2017-06-11.jpg"
//    }

    /**
     * 必应词典base url
     * 拼接参数为：
     * 1. Word，必选，可以为单词或者词组，需要encode
     * 2. Sample，可选，例句功能，默认为true，utf-8编码格式
     */
    public static final String BING_BASE = "http://xtk.azurewebsites.net/BingDictService.aspx";

}
