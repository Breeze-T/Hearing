package com.bootdo.edu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import com.bootdo.edu.domain.EduTeacherDO;
import com.bootdo.edu.service.EduTeacherService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-04 10:41:01
 */
 
@Controller
@RequestMapping("/edu/eduTeacher")
public class EduTeacherController {
	@Autowired
	private EduTeacherService eduTeacherService;
	@Autowired
	private DictService dictService;
	@GetMapping()
	@RequiresPermissions("edu:eduTeacher:eduTeacher")
	String EduTeacher(Model model){
		List<DictDO> dictDOS = dictService.listByType("teacher_type");
		model.addAttribute("oaNotifyTypes",dictDOS);
	    return "edu/eduTeacher/eduTeacher";
	}
	
	@ResponseBody
	@GetMapping("/list")
	//@RequiresPermissions("edu:eduTeacher:eduTeacher")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EduTeacherDO> eduTeacherList = eduTeacherService.list(query);
		int total = eduTeacherService.count(query);
		PageUtils pageUtils = new PageUtils(eduTeacherList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduTeacher:add")
	String add(Model model){
		List<DictDO> dictDOS = dictService.listByType("teacher_type");
		model.addAttribute("oaNotifyTypes",dictDOS);
		return "edu/eduTeacher/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduTeacher:edit")
	String edit(@PathVariable("id") String id,Model model){
		EduTeacherDO eduTeacher = eduTeacherService.get(id);
		List<DictDO> dictDOS = dictService.listByType("teacher_type");
		model.addAttribute("oaNotifyTypes",dictDOS);
		model.addAttribute("eduTeacher", eduTeacher);
	    return "edu/eduTeacher/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduTeacher:add")
	public R save( EduTeacherDO eduTeacher){
		if(eduTeacherService.save(eduTeacher)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduTeacher:edit")
	public R update( EduTeacherDO eduTeacher){
		eduTeacherService.update(eduTeacher);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduTeacher:remove")
	public R remove( String id){
		if(eduTeacherService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

	//教师评分列表
	@GetMapping("/eduTeacherAssessList")
	@RequiresPermissions("edu:eduTeacher:eduTeacherAssessList")
	String eduTeacherAssessList(Model model){
		List<DictDO> dictDOS = dictService.listByType("teacher_type");
		model.addAttribute("oaNotifyTypes",dictDOS);
		return "edu/eduTeacher/eduTeacherAssessList";
	}
	/**
	 * /教师评分页面
	 */
	@GetMapping("/getTeacherAssessPage/{id}")
	@RequiresPermissions("edu:eduTeacher:getTeacherAssessPage")
	String getTeacherAssessPage(@PathVariable("id") String id,Model model){
		model.addAttribute("id",id);
		return "edu/eduTeacher/eduTeacherAssess";
	}
	/**
	 * /教师评分详情
	 */
	@PostMapping( "/getTeacherAssess")
	@ResponseBody
	//@RequiresPermissions("edu:eduTeacher:batchRemove")
	public R getTeacherAssess(String userId){
		List list=eduTeacherService.getTeacherAssess(userId,false);
		return R.ok(list);
	}
	//教师工作量报表
	@GetMapping("/workLoadListPage")
	@RequiresPermissions("edu:eduTeacher:workLoadListPage")
	String workLoadListPage(Model model){
		List<DictDO> dictDOS = dictService.listByType("teacher_type");
		model.addAttribute("oaNotifyTypes",dictDOS);
		return "edu/eduTeacher/eduTeacherWorkLoadList";
	}
	@ResponseBody
	@GetMapping("/workLoadList")
	//@RequiresPermissions("edu:eduTeacher:eduTeacher")
	public PageUtils workLoadList(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<EduTeacherDO> eduTeacherList = eduTeacherService.workLoadList(query);
		int total = eduTeacherService.count(query);
		PageUtils pageUtils = new PageUtils(eduTeacherList, total);
		return pageUtils;
	}
	@RequestMapping("toExcel")
	@RequiresPermissions("edu:eduTeacher:toExcel")
	public void toExcel(HttpServletResponse response, String Auditor) {
		Map paramMap= JSONObject.parseObject(Auditor);//json字符串转为json对象
		List<EduTeacherDO> eduTeacherList = eduTeacherService.workLoadList(paramMap);
		HSSFWorkbook workbook = ExcelUtil.makeExcelHead("教官工作量统计表", 7);
		String[] beanProperty = { "姓名", "手机号", "警号", "身份证", "职级", "职务","教官类型", "工作量"};
		workbook = ExcelUtil.makeSecondHead(workbook, beanProperty);
		String[] beanProperty2 = {"name", "phone", "policeNumber", "idCard", "postGrade", "post", "teacherType",  "workLoad"};
		workbook = ExcelUtil.exportExcelData(workbook, eduTeacherList, beanProperty2);
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode("教官工作量统计表.xls", "UTF-8"));
			response.setContentType("application/x-download");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
