package com.bootdo.personal.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.edu.service.EduClassService;
import com.bootdo.personal.service.DataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Breeze on 2019/8/10.
 */

@RequestMapping("/personal/prsData")
@Controller
public class DataController extends BaseController {
    String prefix = "personal/prsData";
    @Autowired
    EduClassService dataService;

    //@RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String data(Model model) {
        return prefix+"/prsData";
    }

    /*@ResponseBody
    @GetMapping("/list")
    //@RequiresPermissions("edu:eduClass:eduClass")
    public PageUtils list (@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        List<Map> dataList = dataService.list(query);
        int total = dataService.count(query);
        PageUtils pageUtils = new PageUtils(dataList, total);
        return pageUtils;
    }*/


}
