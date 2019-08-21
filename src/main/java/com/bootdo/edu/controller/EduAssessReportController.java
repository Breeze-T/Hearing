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

import com.bootdo.edu.domain.EduAssessReportDO;
import com.bootdo.edu.service.EduAssessReportService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-26 09:03:31
 */
 
@Controller
@RequestMapping("/edu/eduAssessReport")
public class EduAssessReportController {
	@Autowired
	private EduAssessReportService eduAssessReportService;
	@Autowired
	private EduClassService eduClassService;
	@GetMapping()
	@RequiresPermissions("edu:eduAssessReport:eduAssessReport")
	String EduAssessReport(Model model,String classId,String teacherUserId){
			model.addAttribute("classId",classId);
			model.addAttribute("teacherUserId",teacherUserId);
	    return "edu/eduAssessReport/eduAssessReport";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduAssessReport:eduAssessReport")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map> eduAssessReportList = eduAssessReportService.list(query);
		int total = eduAssessReportService.count(query);
		PageUtils pageUtils = new PageUtils(eduAssessReportList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduAssessReport:add")
	String add(){
	    return "edu/eduAssessReport/add";
	}

	@GetMapping("/getClassAssessRatelistPage")
	@RequiresPermissions("edu:eduAssessReport:getClassAssessRatelistPage")
	String getClassAssessRatelistPage(){
		return "edu/eduAssessReport/eduClassAssessRateList";
	}
	@ResponseBody
	@GetMapping("/getClassAssessRatelist")
	public PageUtils getClassAssessRatelist(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map> eduAssessReportList = eduAssessReportService.getClassAssessRatelist(query);
		int total = eduClassService.count(query);
		PageUtils pageUtils = new PageUtils(eduAssessReportList, total);
		return pageUtils;
	}
	//考勤
	@GetMapping("/listStudentClockPage")
	@RequiresPermissions("edu:eduAssessReport:listStudentClockPage")
	String listStudentClockPage(){
		return "edu/eduAssessReport/eduStudentClockReport";
	}
	@ResponseBody
	@GetMapping("/listStudentClock")
	public PageUtils listStudentClock(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map> eduAssessReportList = eduAssessReportService.listStudentClock(query);
		int total = eduAssessReportService.countStudentClock(query);
		PageUtils pageUtils = new PageUtils(eduAssessReportList, total);
		return pageUtils;
	}
	//签到
	@GetMapping("/listSignPage")
	@RequiresPermissions("edu:eduAssessReport:listSignPage")
	String listSignPage(){
		return "edu/eduAssessReport/eduListSignPage";
	}

	@ResponseBody
	@GetMapping("/listSign")
	public PageUtils listSign(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map> eduAssessReportList = eduAssessReportService.listSign(query);
		int total = eduAssessReportService.countSign(query);
		PageUtils pageUtils = new PageUtils(eduAssessReportList, total);
		return pageUtils;
	}
}
