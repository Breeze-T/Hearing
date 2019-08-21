package com.bootdo.edu.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.common.domain.Tree;
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

import com.bootdo.edu.domain.EduDormitoryStudentDO;
import com.bootdo.edu.service.EduDormitoryStudentService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-19 08:44:48
 */
 
@Controller
@RequestMapping("/edu/eduDormitoryStudent")
public class EduDormitoryStudentController {
	@Autowired
	private EduDormitoryStudentService eduDormitoryStudentService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduDormitoryStudent:eduDormitoryStudent")
	String EduDormitoryStudent(){
	    return "edu/eduDormitoryStudent/eduDormitoryStudent";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduDormitoryStudent:eduDormitoryStudent")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EduDormitoryStudentDO> eduDormitoryStudentList = eduDormitoryStudentService.list(query);
		int total = eduDormitoryStudentService.count(query);
		PageUtils pageUtils = new PageUtils(eduDormitoryStudentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduDormitoryStudent:add")
	String add(){
	    return "edu/eduDormitoryStudent/add";
	}

	@GetMapping("/edit/{userId}")
	@RequiresPermissions("edu:eduDormitoryStudent:edit")
	String edit(@PathVariable("userId") String userId,Model model){
		EduDormitoryStudentDO eduDormitoryStudent = eduDormitoryStudentService.get(userId);
		model.addAttribute("eduDormitoryStudent", eduDormitoryStudent);
	    return "edu/eduDormitoryStudent/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduDormitoryStudent:add")
	public R save( EduDormitoryStudentDO eduDormitoryStudent){
		if(eduDormitoryStudentService.save(eduDormitoryStudent)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduDormitoryStudent:edit")
	public R update( EduDormitoryStudentDO eduDormitoryStudent){
		eduDormitoryStudentService.update(eduDormitoryStudent);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduDormitoryStudent:remove")
	public R remove( String userId){
		if(eduDormitoryStudentService.remove(userId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("edu:eduDormitoryStudent:batchRemove")
	public R remove(@RequestParam("ids[]") String[] userIds){
		eduDormitoryStudentService.batchRemove(userIds);
		return R.ok();
	}
	/**
	 * 根据中队数和组数生成组织架构
	 */
	@ResponseBody
	@RequestMapping("/getStudentGroup")
	public R getStudentGroup(@RequestParam Map map) {
		// 查询列表数据
		Tree<Map> dictList = eduDormitoryStudentService.getStudentGroup(map);
		if(dictList==null){
			R r =R.error();
			return r;
		}
		R r =R.ok();
		r.put("data",dictList);
		return r;
	}
	/**
	 * 从数据库查询组织架构
	 */
	@ResponseBody
	@RequestMapping("/getDataBaseStudentGroup")
	public R getDataBaseStudentGroup(@RequestParam  Map map) {
		// 查询列表数据
		Tree<Map> dictList = eduDormitoryStudentService.getDataBaseStudentGroup(map);
		R r =R.ok();
		r.put("data",dictList);
		return r;
	}
	@GetMapping("/getStudentGroupPage")
	String getStudentGroupPage(String classId,Model model){
		model.addAttribute("classId",classId);
		return "edu/eduDormitoryStudent/eduStudentGroup";
	}
	@PostMapping("/changeGroup")
	@ResponseBody
	public R getStudentGroupPage(Long studentIdOld,Long studentIdNew){
		if(eduDormitoryStudentService.changeGroup(studentIdOld,studentIdNew)){
			return R.ok();
		}
		return R.error();
	}


	@GetMapping("/getStudentDormitoryPage")
	String getStudentDormitoryPage(String classId,String className,Model model){
		model.addAttribute("classId",classId);
		model.addAttribute("className",className);
		return "edu/eduDormitoryStudent/eduDormitoryStudent";
	}
	//获取宿舍分配信息
	@PostMapping("/getDormitoryStudentList")
	@ResponseBody
	public R getDormitoryStudentList(@RequestParam  Map map){
		List<Map> list=eduDormitoryStudentService.getDormitoryStudentList(map);
		if(list==null){
			R r =R.error(1,"宿舍不够使用");
			return r;
		}
		R r =R.ok();
		r.put("data",list);
		return r;
	}
	@PostMapping("/changeStudentDormitory")
	@ResponseBody
	public R changeStudentDormitory(@RequestParam  Map map){
		if(eduDormitoryStudentService.changeStudentDormitory(map)){
			return R.ok();
		}
		return R.error();
	}
	//重置宿舍
	@PostMapping("/setDormitoryStudentList")
	@ResponseBody
	public R setDormitoryStudentList(@RequestParam  Map map){
		List<Map> list=eduDormitoryStudentService.setDormitoryStudentList(map);
		if(list==null){
			R r =R.error(1,"宿舍不够使用");
			return r;
		}
		R r =R.ok();
		r.put("data",list);
		return r;
	}
}
