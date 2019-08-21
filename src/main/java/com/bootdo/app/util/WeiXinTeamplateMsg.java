package com.bootdo.app.util;

import com.bootdo.common.utils.JSONUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/12/20 0020.
 */
public class WeiXinTeamplateMsg implements Serializable {
    private static final long serialVersionUID = 8038149984818112449L;
    private String touser; //接收者的openId
    private String template_id; //模板id
    private String form_id;
    private TemplateItem data; //数据
    private String page; //跳转链接
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public static WeiXinTeamplateMsg New() {
        return new WeiXinTeamplateMsg();
    }
    private WeiXinTeamplateMsg() {
        this.data = new TemplateItem();
    }
    public String getTouser() {
        return touser;
    }
    public WeiXinTeamplateMsg setTouser(String touser) {
        this.touser = touser;
        return this;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public WeiXinTeamplateMsg setTemplate_id(String template_id) {
        this.template_id = template_id;
        return this;
    }
    public String getForm_id() {
        return form_id;
    }

    public WeiXinTeamplateMsg setForm_id(String url) {
        this.form_id = url;
        return this;
    }
    public TemplateItem getData() {
        return data;
    }
    public WeiXinTeamplateMsg add(String key, String value, String color){
        data.put(key, new Item(value, color));
        return this;
    }
    public WeiXinTeamplateMsg add(String key, String value){
        data.put(key, new Item(value));
        return this;
    }
    public String build() {
        return JSONUtils.beanToJson(this);
    }
    public class TemplateItem extends HashMap<String, Item> {
        private static final long serialVersionUID = -3728490424738325020L;
        public TemplateItem() {}
        public TemplateItem(String key, Item item) {
            this.put(key, item);
        }
    }
    public class Item {
        private Object value;
        private String color;
        public Object getValue() {
            return value;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public String getColor() {
            return color;
        }
        public void setColor(String color) {
            this.color = color;
        }
        public Item(Object value) {
            this(value, "#999");
        }
        public Item(Object value, String color) {
            this.value = value;
            this.color = color;
        }
    }
}
