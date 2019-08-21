package com.bootdo.edu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.edu.domain.CheckDO;
import com.bootdo.edu.domain.CheckDetailDO;
import com.bootdo.edu.domain.StudentDO;
import com.bootdo.edu.service.CheckDetailService;
import com.bootdo.edu.service.StudentService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/edu/checkDetail")
public class CheckDetailController extends BaseController{
	private String prefix = "edu/eduCheckDetail";
	@Autowired
	private CheckDetailService checkDetailService;
	@Autowired
	private StudentService studentService;
	
	
	@GetMapping()
	@RequiresPermissions("edu:eduCheckDetail:eduCheckDetail")
	public String checkDetail() {
		return prefix + "/checkDetail";
	}
	
	/**
	 *查询 
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduCheckDetail:eduCheckDetail")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<StudentDO> studentList = checkDetailService.list(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	
	@GetMapping("/addPoint/{studentId}")
	@RequiresPermissions("edu:eduCheckDetail:addPoint")
	public String addPoint(@PathVariable("studentId")Long studentId, Model model) {
		model.addAttribute("studentId",studentId);
		return  prefix + "/addPoint";
	}
	
	@GetMapping("/removePoint/{studentId}")
	@RequiresPermissions("edu:eduCheckDetail:removePoint")
	public String removePoint(@PathVariable("studentId")Long studentId, Model model) {
		model.addAttribute("studentId",studentId);
		return  prefix + "/removePoint";
	}
	
	/**
	 * 打分确认
	 */
	@PostMapping("/confirm")
	@ResponseBody
	@RequiresPermissions("edu:eduCheckDetail:addPoint")
	public R confirm(String studentId,@RequestParam("checkIds[]") String[] checkIds,@RequestParam("remarkArr[]") String[] remarkArr) {
		if (checkDetailService.confirm(studentId,checkIds,remarkArr,getUserId().toString())> 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	@GetMapping("/showDetail/{studentId}/{type}")
	public String showDetail(@PathVariable("studentId")Long studentId,@PathVariable("type")String type, Model model) {
		model.addAttribute("studentId",studentId);
		model.addAttribute("checkType", type);
		return  prefix + "/showDetail";
	}
	
	@ResponseBody
	@GetMapping("/detailList")
	public PageUtils detailList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<CheckDetailDO> checkDetail = checkDetailService.detailList(query);
		int total = checkDetailService.count(query);
		PageUtils pageUtils = new PageUtils(checkDetail, total);
		return pageUtils;
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduCheckDetail:remove")
	public R remove(String id) {
		if (checkDetailService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}
	/**
	 *学生成绩页面
	 */
	@GetMapping("/getStudentScoreListPage")
	@RequiresPermissions("edu:eduCheckDetail:eduCheckDetail")
	public String studentScoreList() {
		return prefix + "/studentScoreList";
	}

	/**
	 *查询学生成绩列表
	 */
	@ResponseBody
	@GetMapping("/studentScoreList")
	public PageUtils studentScoreList(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Map> studentList = checkDetailService.studentScoreList(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	/**
	 *保存学生成绩
	 */
	@ResponseBody
	@RequestMapping("/saveStudentScore")
	@RequiresPermissions("edu:eduCheckDetail:saveStudentScore")
	public R  saveStudentScore(@RequestBody Map<String, Object> params) {
		int total = checkDetailService.saveStudentScore(params);
			return R.ok();
	}
	/**
	 * /学生档案考试成绩
	 */
	@PostMapping( "/getTotalScoreList")
	@ResponseBody
	//@RequiresPermissions("edu:eduTeacher:batchRemove")
	public R getTotalScoreList(@RequestParam Map<String, Object> params){
		List list=checkDetailService.getTotalScoreList(params);
		return R.ok(list);
	}
	/**
	 *评价基本信息查询
	 */
	/**
	 *学生成绩页面
	 */
	@GetMapping("/getCheckDetailListNewPage")
	public String getCheckDetailListNewPage() {
		return prefix + "/checkDetailListNewPage";
	}
	@ResponseBody
	@GetMapping("/getCheckDetailListNew")
	public PageUtils getCheckDetailListNew(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<Map> studentList = checkDetailService.getCheckDetailListNew(query);
		int total = checkDetailService.countCheckDetailListNew(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	@GetMapping("/checkDetailStudentNew")
	public String checkDetailStudentNew() {
		return prefix + "/checkDetailStudentNew";
	}
	@RequestMapping("toExcelStudentNew")
	public void toExcelStudentNew(HttpServletResponse response, String Auditor) {
		Map paramMap= JSONObject.parseObject(Auditor);//json字符串转为json对象
		List<StudentDO> list = checkDetailService.list(paramMap);
		HSSFWorkbook workbook = ExcelUtil.makeExcelHead("评价信息统计", 2);
		String[] beanProperty = { "姓名", "班级", "当前分数"};
		workbook = ExcelUtil.makeSecondHead(workbook, beanProperty);
		String[] beanProperty2 = {"studentName", "className", "score"};
		workbook = ExcelUtil.exportExcelData(workbook, list, beanProperty2);
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode("评价信息统计.xls", "UTF-8"));
			response.setContentType("application/x-download");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@GetMapping("/checkDetailTypeNew")
	public String checkDetailTypeNew() {
		return prefix + "/checkDetailTypeNew";
	}
	//素质评价等级统计
	@ResponseBody
	@GetMapping("/getCheckDetailTypeNew")
	public PageUtils getCheckDetailTypeNew(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		List<Map> studentList = checkDetailService.checkDetailTypeNew(params);
		PageUtils pageUtils = new PageUtils(studentList, 0);
		return pageUtils;
	}
	@RequestMapping("toExcel")
	public void toExcel(HttpServletResponse response, String Auditor) {
		Map paramMap= JSONObject.parseObject(Auditor);//json字符串转为json对象
		List<Map> list = checkDetailService.checkDetailTypeNew(paramMap);
		HSSFWorkbook workbook = ExcelUtil.makeExcelHead("素质评价等级统计", 1);
		String[] beanProperty = { "等级", "人数"};
		workbook = ExcelUtil.makeSecondHead(workbook, beanProperty);
		String[] beanProperty2 = {"type", "count"};
		workbook = ExcelUtil.exportExcelMap(workbook, list, beanProperty2);
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode("素质评价等级统计.xls", "UTF-8"));
			response.setContentType("application/x-download");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
