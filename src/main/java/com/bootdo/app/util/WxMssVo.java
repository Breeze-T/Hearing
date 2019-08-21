package com.bootdo.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//模版id
    private String page = "index";//默认跳到小程序首页
    private String form_id;//收集到的用户formid
    private String emphasis_keyword = "keyword1.DATA";//放大那个推送字段
    private Map<String, TemplateData> data;//推送文字

}
