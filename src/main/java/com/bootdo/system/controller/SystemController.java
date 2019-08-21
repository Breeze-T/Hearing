package com.bootdo.system.controller;

import com.bootdo.app.domain.CPUModel;
import com.bootdo.app.util.SystemMonitor;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/10 0010.
 */
@RequestMapping("/sys/system")
@Controller
public class SystemController {

    @RequestMapping("/index")
    public String openSystem(Model model){
        System.out.println("###########################服务器情况:");
        try {
            CPUModel cpuModel = SystemMonitor.cpu();
            List<Map<String,Object>> fileList = SystemMonitor.file();
            List<Map<String,Object>> netList = SystemMonitor.net();
            model.addAttribute("memory", SystemMonitor.memory());
            model.addAttribute("cpu", cpuModel);
            System.out.println("###########################情况:"+cpuModel.getCpuPercCombinedStyle());
            model.addAttribute("cpuSize", cpuModel.getCpuPercList().size());
            model.addAttribute("file", fileList);
            model.addAttribute("fileSize", fileList.size());
            model.addAttribute("net", netList);
            model.addAttribute("netSize", netList.size());
            return "system/monitor/index";
        } catch (SigarException e) {
            e.printStackTrace();
            return "error/error";
        }catch (Exception exception) {
            exception.printStackTrace();
            return "error/error";
        }
    }
}
