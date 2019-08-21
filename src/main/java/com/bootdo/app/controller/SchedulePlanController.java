package com.bootdo.app.controller;

import com.bootdo.app.service.AssessService;
import com.bootdo.app.service.SchedulePlanService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@Controller
@RequestMapping("/app")
public class SchedulePlanController extends BaseController{
    @Autowired
    private SchedulePlanService schedulePlanService;

    @ResponseBody
    @RequestMapping("/schedule/list")
    public List<Map<String,Object>> list(String classId) {
        return schedulePlanService.list(classId);
    }
}
