package com.bootdo.edu.controller;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.edu.domain.EduTeacherDO;
import com.bootdo.system.service.SignCheckService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/edu/sign")
public class SignCheckController extends BaseController {
	private String prefix = "edu/sign";
	@Autowired
	SignCheckService signCheckService;

	@RequestMapping("/index")
	public String openSign(Model model) {

		return prefix + "/index";
	}
	@ResponseBody
	@GetMapping("/list")
	//@RequiresPermissions("edu:eduTeacher:eduTeacher")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String,Object>> list = signCheckService.list(query);
		int total = signCheckService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	

}
