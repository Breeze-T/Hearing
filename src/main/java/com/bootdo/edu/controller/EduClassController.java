package com.bootdo.edu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
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

import com.bootdo.edu.domain.EduClassDO;
import com.bootdo.edu.service.EduClassService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 
 * @author lvbin
 * @email 1992lcg@163.com
 * @date 2018-11-29 14:37:11
 */
 
@Controller
@RequestMapping("/edu/eduClass")
public class EduClassController {
	@Autowired
	private EduClassService eduClassService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduClass:eduClass")
	String EduClass(){
	    return "edu/eduClass/eduClass";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduClass:eduClass")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<Map> eduClassList = eduClassService.list(query);
		int total = eduClassService.count(query);
		PageUtils pageUtils = new PageUtils(eduClassList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduClass:add")
	String add(){
	    return "edu/eduClass/add";
	}
	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		int total = eduClassService.count(params);
		return total==0;
	}
	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduClass:edit")
	String edit(@PathVariable("id") String id,Model model){
		Map eduClass = eduClassService.getMap(id);
		model.addAttribute("eduClass", eduClass);
	    return "edu/eduClass/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduClass:add")
	public R save( EduClassDO eduClass,String positionString){
		if(eduClassService.save(eduClass,positionString)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduClass:edit")
	public R update( EduClassDO eduClass,String positionString){
		eduClassService.update(eduClass,positionString);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduClass:remove")
	public R remove( String id){
		if(eduClassService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}

	/**
	 * 添加班级的时候获取职位列表
	 */
	@ResponseBody
	@GetMapping("/getPositionByClassId")
	public List<Map> getPositionByClassId(String classId) {
		// 查询列表数据
		List<Map> dictList = eduClassService.getPositionByClassId(classId);
		return dictList;
	}
	/**
	 * 设置学科的时候，获取职位对应的学科列表
	 */
	@ResponseBody
	@GetMapping("/getSubjectByClassId")
	public List<Map> getSubjectByClassId(String classId) {
		// 查询列表数据
		List<Map> dictList = eduClassService.getSubjectByClassId(classId);
		return dictList;
	}
	/**
	 * 设置学科页面
	 */
	@GetMapping("/editSubject/{id}")
	@RequiresPermissions("edu:eduClass:editSubject")
	String editSubject(@PathVariable("id") String id,Model model){
		Map eduClass = eduClassService.getMap(id);
		model.addAttribute("eduClass", eduClass);
		return "edu/eduClass/editSubject";
	}
	/**
	 * 修改设置学科
	 */
	@ResponseBody
	@RequestMapping("/updateSubject")
	@RequiresPermissions("edu:eduClass:editSubject")
	public R updateSubject( EduClassDO eduClass,String positionString){
		eduClassService.updateSubject(eduClass,positionString);
		return R.ok();
	}
	/**
	 * 保存课程开始和结束时间
	 */
	@ResponseBody
	@RequestMapping("/updateScheduleDate")
	public R updateScheduleDate( EduClassDO eduClass){
		eduClassService.updateScheduleDate(eduClass);
		return R.ok();
	}
	@RequestMapping("toExcel")
	public void toExcel(HttpServletResponse response, String Auditor) {
		Map paramMap= JSONObject.parseObject(Auditor);//json字符串转为json对象
		List<Map> list = eduClassService.list(paramMap);
		HSSFWorkbook workbook = ExcelUtil.makeExcelHead("班级列表", 3);
		String[] beanProperty = { "班级名称", "班主任", "创建人", "创建时间"};
		workbook = ExcelUtil.makeSecondHead(workbook, beanProperty);
		String[] beanProperty2 = {"className", "classAdviser", "createUser", "createTime"};
		workbook = ExcelUtil.exportExcelMap(workbook, list, beanProperty2);
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode("班级列表.xls", "UTF-8"));
			response.setContentType("application/x-download");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
