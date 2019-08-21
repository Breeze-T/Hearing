package com.bootdo.app.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/10 0010.
 */
public class CPUModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String CpuPercIdleStyle = "width:10%;";//当前cpu的空闲率样式
    private String CpuPercCombinedStyle;//当前cpu的占用率样式
    private String CpuPercIdle;//当前cpu的空闲率
    private String CpuPercCombined;//当前cpu的占用率
    private List<Map<String,Object>> CpuPercList;//每个CPU 情况

    public String getCpuPercIdle() {
        return CpuPercIdle;
    }

    public void setCpuPercIdle(String cpuPercIdle) {
        CpuPercIdle = cpuPercIdle;
    }

    public String getCpuPercCombined() {
        return CpuPercCombined;
    }

    public void setCpuPercCombined(String cpuPercCombined) {
        CpuPercCombined = cpuPercCombined;
    }

    public List<Map<String, Object>> getCpuPercList() {
        return CpuPercList;
    }

    public void setCpuPercList(List<Map<String, Object>> cpuPercList) {
        CpuPercList = cpuPercList;
    }

    public String getCpuPercIdleStyle() {
        return CpuPercIdleStyle;
    }

    public void setCpuPercIdleStyle(String cpuPercIdleStyle) {
        CpuPercIdleStyle = cpuPercIdleStyle;
    }

    public String getCpuPercCombinedStyle() {
        return CpuPercCombinedStyle;
    }

    public void setCpuPercCombinedStyle(String cpuPercCombinedStyle) {
        CpuPercCombinedStyle = cpuPercCombinedStyle;
    }
}
