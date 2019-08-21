package com.bootdo.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Breeze on 2019/7/13.
 */
public class TangDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long menuId;
    // 父菜单ID，一级菜单为0
    private Long parentId;
    private String name;
    private String action;
    private Integer thesort;
    private Date time;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return menuId;
    }

    public void setId(Long id) {
        this.menuId = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getThesort() {
        return thesort;
    }

    public void setThesort(Integer thesort) {
        this.thesort = thesort;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TangDO{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", action='" + action + '\'' +
                ", orderNum=" + thesort +
                ", createTime=" + time +
                '}';
    }
}