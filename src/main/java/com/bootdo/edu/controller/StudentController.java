package com.bootdo.edu.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.utils.*;
import com.bootdo.edu.service.EduDormitoryStudentService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.edu.domain.StudentDO;
import com.bootdo.edu.service.EduClassService;
import com.bootdo.edu.service.StudentService;

@Controller
@RequestMapping("/edu/student")
public class StudentController extends BaseController  {
	private String prefix = "edu/student";
	@Autowired
	private StudentService studentService;
	@Autowired
	private DictService dictService;
	@Autowired
	private EduClassService eduClassService;
	@Autowired
	private EduDormitoryStudentService eduDormitoryStudentService;
	
	
	
	
	@GetMapping()
	@RequiresPermissions("edu:eduStudent:eduStudent")
	public String student() {
		return "edu/eduStudent/student";
	}
	
	
	/**
	 *查询 
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduStudent:eduStudent")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<StudentDO> studentList = studentService.list(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	
	/**
	 * 新增
	 */
	@GetMapping("/add")
	@RequiresPermissions("edu:eduStudent:add")
	public String add() {
		return "edu/eduStudent/add";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduStudent:add")
	public R save(StudentDO studentDo) {
		if (studentService.save(studentDo,getUserId()) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:remove")
	public R remove(Long studentId,Long userId,String status) {
		if (studentService.remove(studentId,userId,status) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 * 批量删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids,@RequestParam("userIds[]")String[] userIds) {
		if (studentService.batchRemove(ids,userIds)> 0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduStudent:edit")
	public R update(StudentDO studentDo) {
		if (studentService.update(studentDo) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 编辑
	 */
	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduStudent:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		StudentDO student = studentService.get(id);
		model.addAttribute("student", student);
		return "edu/eduStudent/edit";
	}
	
	/**
	 * 加载下拉框
	 */
	@GetMapping("/type")
	@ResponseBody
	public List<DictDO> listType() {
		return dictService.listByType("student_status");
	};
	
	
	/**
	 * 通过
	 */
	@PostMapping("/pass")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:pass")
	public R pass(Long id) {
		if(studentService.pass(id,getUserId())>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量通过
	 */
	@PostMapping("/batchPass")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:pass")
	public R batchPass(@RequestParam("ids[]") Long[] ids) {
		if(studentService.batchPass(ids,getUserId())>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 驳回
	 */
	@PostMapping("/refuse")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:refuse")
	public R refuse(Long id) {
		if(studentService.refuse(id)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 * 批量驳回
	 */
	@PostMapping("/batchRefuse")
	@ResponseBody
	@RequiresPermissions("edu:eduStudent:refuse")
	public R batchRefuse(@RequestParam("ids[]") Long[] ids) {
		if(studentService.batchRefuse(ids)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
  	 * 导出模板
	 * @throws Exception 
  	 */
      @RequestMapping(value = "exportStuModel")
      @RequiresPermissions("edu:eduStudent:export")
      public void exportAssetsModel(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	  //设置表格头
    	  String[] headerArr= {"姓名","性别","身份证号","手机","邮箱","单位","职务","警号","班级"};
    	  List<String> headerList = Arrays.asList(headerArr);
    	  ExportExcel excel = new ExportExcel(null, headerList);
    	  //对必填项表头标红
    	  CellStyle mustCellStyle = excel.getWb().createCellStyle();
    	  mustCellStyle.cloneStyleFrom(excel.getSheet().getRow(0).getCell(0).getCellStyle());
    	  mustCellStyle.setFillForegroundColor(HSSFColor.RED.index);
    	  excel.getSheet().getRow(0).getCell(0).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(1).setCellStyle(mustCellStyle);
    	  //excel.getSheet().getRow(0).getCell(2).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(3).setCellStyle(mustCellStyle);
    	  //excel.getSheet().getRow(0).getCell(4).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(5).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(6).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(7).setCellStyle(mustCellStyle);
    	  excel.getSheet().getRow(0).getCell(8).setCellStyle(mustCellStyle);
    	  //定义性别的下拉内容数组
    	  String[] sexText = {"男","女"};
    	  //获得班级名称作为下拉选项
    	  List<String> classNameList = new ArrayList<String>();
    	  List<Map<String,Object>> classMap= eduClassService.getAllClass(null);
    	  for (Map<String, Object> map : classMap) {
    		  classNameList.add(map.get("resultKey").toString());
    	  }
    	  excel=excel.setCellFormat(excel,1000,9);
    	  excel=excel.setHSSFValidation(excel, classNameList.toArray(new String[classNameList.size()]),"className",1, 999, 8, 8);
    	  excel=excel.setHSSFValidation(excel, sexText,"sex",1, 999, 1, 1);
    	  excel.write(response,"studentImport.xls");
      }
	
	
	/**
    * 批量导入
    */
    @RequestMapping(value = "batchImport")
    @ResponseBody
	public R batchImport(MultipartFile multipartFile) {
    	if (null == multipartFile) {
			return R.error("没有获取到文件信息!");
		}
    	ExcelOpen readExcelx = new ExcelOpen(multipartFile);
    	try {
			readExcelx.openMultipartFile();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error("导入失败!");
		}
    	readExcelx.setSheetNum(0); // 设置读取索引为0的工作表
    	Object[] headRow = readExcelx.readExcelOjbectLine(0);//此方法有问题  待修改 此处自己转换一下
		Object[] headRows = new Object[headRow.length-1];
		for (int i=0;i<headRow.length-1;i++) {
			headRows[i]=headRow[i];
		}
		if(!readExcelx.getWb().getSheetAt(0).getSheetName().equals("Export")) {
			return R.error("请使用系统提供的导入模板！");
		}
		//校验头
		String[] headerArr= {"姓名","性别","身份证号","手机","邮箱","单位","职务","警号","班级"};
		if(headRows.length != headerArr.length) {
			return R.error("请使用正确的导入模板！");
		}
		for(int r=0;r<headRows.length;r++){
			if(!headerArr[r].trim().equals(((String)headRows[r]).trim())){
				return R.error("请选择正确的导入模板！");
			}
		}
		try {
			return  studentService.batchImport(readExcelx);
		} catch (Exception e) {
			return R.error("数据导入出错!");
		}
    	
    }
    
    /**
     * 学员档案
     */
    @GetMapping("/studentProfile")
	@RequiresPermissions("edu:eduStudent:studentProfile")
	public String studentProfile() {
		return "edu/eduStudent/studentProfile";
	}
    
    /**
	 *学员档案列表
	 */
	@ResponseBody
	@GetMapping("/profileList")
	@RequiresPermissions("edu:eduCheckDetail:eduCheckDetail")
	public PageUtils profileList(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<StudentDO> studentList = studentService.profileList(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
    
    /**
     * 学员档案详情
     */
    @GetMapping("/profileDetail/{studentId}")
	public String showDetail(@PathVariable("studentId")Long studentId, Model model) {
    	StudentDO student = studentService.profileDetail(studentId);
		model.addAttribute("student", student);
		Map dormitory=eduDormitoryStudentService.getMapStudentDormitory(studentId);
		model.addAttribute("dormitory", dormitory);
		return  "edu/eduStudent/profileDetail";
	}
    
    
    
    
  //导出在线备课记录为doc文档
  	@RequestMapping("/exportProfile")
  	public String exportDoc(HttpServletRequest request,HttpServletResponse response,String studentId) {
  		StudentDO student = studentService.profileDetail(Long.valueOf(studentId));
  		XWPFDocument doc = new XWPFDocument();//创建doc文档
  		XWPFParagraph p = doc.createParagraph();//创建一个段落
  		p.setAlignment(ParagraphAlignment.CENTER);// 设置段落的对齐方式
  		p.setBorderBottom(Borders.DOUBLE);//设置下边框
  		p.setBorderTop(Borders.DOUBLE);//设置上边框
  		p.setBorderRight(Borders.DOUBLE);//设置右边框
  		p.setBorderLeft(Borders.DOUBLE);//设置左边框
  		XWPFRun r = p.createRun();//创建段落文本
  		r.setText(student.getStudentName()+"档案详情");	
  		r.setBold(true);
  		r.setFontSize(26);//设置字体大小
  		Map<String,String> titleMap = new HashMap<String,String>();
  		titleMap.put("1、姓名", student.getStudentName());
  		if(student.getSex().equals("M")) {
  			titleMap.put("2、性别","男");
  		}else if(student.getSex().equals("F")) {
  			titleMap.put("2、性别","女");
  		}else {
  			titleMap.put("2、性别","");
  		}
  		titleMap.put("3、出生日期", student.getMap().get("birth").toString());
  		titleMap.put("4、手机号", student.getPhoneNum());
  		titleMap.put("5、所在单位", student.getUnit());
  		titleMap.put("6、职务",student.getDuties());
  		titleMap.put("7、警号", student.getAlarmNum());
  		titleMap.put("8、所在班级", student.getMap().get("className").toString());
  		titleMap.put("9、考核得分", student.getMap().get("studentScore").toString());
  		if(Integer.parseInt(student.getMap().get("studentScore").toString())>=95) {
  			titleMap.put("10、考核等级", "优秀");
  		}else if(Integer.parseInt(student.getMap().get("studentScore").toString())>=85&&Integer.parseInt(student.getMap().get("studentScore").toString())<95) {
  			titleMap.put("10、考核等级", "良好");
  		}else if(Integer.parseInt(student.getMap().get("studentScore").toString())>=60&&Integer.parseInt(student.getMap().get("studentScore").toString())<85) {
  			titleMap.put("10、考核等级", "及格");
  		}else{
  			titleMap.put("10、考核等级", "不及格");
  		}
  		Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {  
  		        public int compare(String key1, String key2) {  
  		        	return Integer.parseInt(key1.split("、")[0])-Integer.parseInt(key2.split("、")[0]);
  		        }});
  		sortedMap.putAll(titleMap);
  		for (Entry<String, String> entry : sortedMap.entrySet()) {
  			r = doc.createParagraph().createRun();
  			r.setBold(true);
  			r.setFontSize(12);
  			r.setText(entry.getKey());
  			r = doc.createParagraph().createRun();
  			r.setText(entry.getValue());
  		}
  		try {
  			response.reset();
  			response.setContentType("application/doc");
  			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(student.getStudentName()+"档案详情.doc", "UTF-8"));
  			doc.write(response.getOutputStream());
  		}catch(Exception e) {
  			e.printStackTrace();
  		}
  		return null;
  	}
    
    /**
     * 统计分析
     */
    @GetMapping("/studentCount")
	@RequiresPermissions("edu:eduStudent:count")
	public String studentCount() {
		return "edu/eduStudent/studentCount";
	}
    
    
    /**
	 * 学生男女比例
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStudentBySex")
	public List<Map<String,Object>> getStudentBySex() {
		List<Map<String,Object>> list = studentService.getStudentBySex();
		return list;
	}
	
	/**
	 * 学生年龄分布
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStudentByAge")
	public List<Map<String,Object>> getStudentByAge() {
		List<DictDO> dictList = dictService.listByType("ageRange_set");
		List<Map<String,String>> parList = new ArrayList<Map<String,String>>();
		for (DictDO dictDO : dictList) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("startAge", dictDO.getValue().split("-")[0]);
			map.put("endAge", dictDO.getValue().split("-")[1]);
			parList.add(map);
		}
		List<Map<String,Object>> list = studentService.getStudentByAge(parList);
		return list;
	}
	@RequestMapping("toExcel")
	@RequiresPermissions("edu:eduTeacher:toExcel")
	public void toExcel(HttpServletResponse response, String Auditor,String flag) {
		Map paramMap= JSONObject.parseObject(Auditor);//json字符串转为json对象
		List<StudentDO> eduTeacherList = null;
		if (flag!=null&&"1".equals(flag)){
			eduTeacherList = studentService.profileList(paramMap);//档案列表
		}else {
			eduTeacherList = studentService.list(paramMap);
		}
		HSSFWorkbook workbook = ExcelUtil.makeExcelHead("学员列表", 7);
		String[] beanProperty = { "姓名","警号", "手机号",  "身份证", "单位", "职务", "班级"};
		workbook = ExcelUtil.makeSecondHead(workbook, beanProperty);
		String[] beanProperty2 = {"studentName",  "alarmNum", "phoneNum","cardNum", "unit", "duties", "className"};
		workbook = ExcelUtil.exportExcelData(workbook, eduTeacherList, beanProperty2);
		try {
			OutputStream output = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+URLEncoder.encode("学员列表.xls", "UTF-8"));
			response.setContentType("application/x-download");
			workbook.write(output);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
