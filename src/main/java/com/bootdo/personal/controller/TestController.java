package com.bootdo.personal.controller;

import com.bootdo.common.controller.BaseController;
import com.bootdo.system.service.TangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Breeze on 2019/8/12.
 */

@RequestMapping("/personal/test")
@Controller
public class TestController extends BaseController {
    String prefix = "personal/test";
    @Autowired
    TangService menuService;

    //@RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model) {
        return prefix+"/test";
    }
}
