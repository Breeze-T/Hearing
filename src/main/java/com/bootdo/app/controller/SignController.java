package com.bootdo.app.controller;

import com.bootdo.app.service.AssessService;
import com.bootdo.app.service.SignService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.R;
import com.bootdo.edu.service.EduTeacherService;
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
public class SignController extends BaseController{
    @Autowired
    private SignService signService;

    @ResponseBody
    @RequestMapping("/sign/list")
    public List<Map<String,Object>> list(String userId,String date) {
        List<Map<String,Object>> list = signService.list(userId,date);
        return list;
    }

    @ResponseBody
    @RequestMapping("/sign/insertSign")
    public R insertSign(String latitude,String longitude,String userId,String signAddr,String remark){
        try {
            //签到信息录入
            System.out.println("longitude:"+longitude+"#latitude:"+latitude+"#userId:"+userId+"#signAddr:"+signAddr+"#remark:"+remark);
            signService.insertSign(userId,longitude,latitude,signAddr,remark);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

}
