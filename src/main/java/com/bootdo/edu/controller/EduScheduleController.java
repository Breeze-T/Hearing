package com.bootdo.edu.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.edu.service.EduClassService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.edu.domain.EduScheduleDO;
import com.bootdo.edu.service.EduScheduleService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-11 09:30:01
 */
 
@Controller
@RequestMapping("/edu/eduSchedule")
public class EduScheduleController {
	@Autowired
	private EduScheduleService eduScheduleService;
	@Autowired
	private EduClassService eduClassService;
	@GetMapping()
	//@RequiresPermissions("edu:eduSchedule:eduSchedule")
	String EduSchedule(String classId,String start,String end,Model model){
		Map eduClass = eduClassService.getMap(classId);
		model.addAttribute("start", eduClass.get("scheduleStart"));
		model.addAttribute("end", eduClass.get("scheduleEnd"));
		model.addAttribute("classId", classId);
	    return "edu/eduSchedule/eduSchedule";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	//@RequiresPermissions("edu:eduSchedule:eduSchedule")
	public List list(@RequestParam Map<String, Object> params){
		//查询列表数据
		List<Map> eduScheduleList = eduScheduleService.list(params);
		return eduScheduleList;
	}
	
	@GetMapping("/add")
	//@RequiresPermissions("edu:eduSchedule:add")
	String add(){
	    return "edu/eduSchedule/add";
	}

	@GetMapping("/edit/{id}")
	//@RequiresPermissions("edu:eduSchedule:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		EduScheduleDO eduSchedule = eduScheduleService.get(id);
		model.addAttribute("eduSchedule", eduSchedule);
	    return "edu/eduSchedule/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	//@RequiresPermissions("edu:eduSchedule:add")
	public R save( EduScheduleDO eduSchedule,String flag){
		Map outMap=eduScheduleService.save(eduSchedule,flag);
		if("0".equals(outMap.get("code"))){
			return R.ok(outMap.get("msg")+"");
		}
		return R.error(outMap.get("msg")+"");
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	//@RequiresPermissions("edu:eduSchedule:edit")
	public R update( EduScheduleDO eduSchedule){
		eduScheduleService.update(eduSchedule);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	//@RequiresPermissions("edu:eduSchedule:remove")
	public R remove( Integer id){
		if(eduScheduleService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 到教学计划班级列表
	 * @return
	 */
	@GetMapping("/eduClassSchedule")
	@RequiresPermissions("edu:eduSchedule:eduClassSchedule")
	String EduClassSchedule(){
		return "edu/eduSchedule/eduClassSchedule";
	}
	/**
	 * 删除课程
	 */
	@ResponseBody
	@RequestMapping("/deleteSchedule")
	//@RequiresPermissions("edu:eduSchedule:edit")
	public R deleteSchedule( EduScheduleDO eduSchedule){
		int aa=eduScheduleService.deleteSchedule(eduSchedule);
		return R.ok();
	}
}
