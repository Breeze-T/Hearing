package com.bootdo.edu.controller;

import java.util.List;
import java.util.Map;

import com.bootdo.common.domain.Tree;
import com.bootdo.system.domain.DeptDO;
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

import com.bootdo.edu.domain.EduSubjectDO;
import com.bootdo.edu.service.EduSubjectService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-06 17:59:49
 */
 
@Controller
@RequestMapping("/edu/eduSubject")
public class EduSubjectController {
	@Autowired
	private EduSubjectService eduSubjectService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduSubject:eduSubject")
	String EduSubject(){
	    return "edu/eduSubject/eduSubject";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduSubject:eduSubject")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EduSubjectDO> eduSubjectList = eduSubjectService.list(query);
		int total = eduSubjectService.count(query);
		PageUtils pageUtils = new PageUtils(eduSubjectList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduSubject:add")
	String add(){
	    return "edu/eduSubject/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduSubject:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		EduSubjectDO eduSubject = eduSubjectService.get(id);
		model.addAttribute("eduSubject", eduSubject);
	    return "edu/eduSubject/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduSubject:add")
	public R save( EduSubjectDO eduSubject){
		if(eduSubjectService.save(eduSubject)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduSubject:edit")
	public R update( EduSubjectDO eduSubject){
		eduSubjectService.update(eduSubject);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduSubject:remove")
	public R remove( Integer id){
		if(eduSubjectService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	/**
	 * 获取学科树形
	 */
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = eduSubjectService.getTree();
		return tree;
	}
	/**
	 * 获取学科树形页面
	 */
	@GetMapping("/treeView")
	String treeView(Model model,String id,String name) {
		model.addAttribute("id",id);
		model.addAttribute("name",name);
		return   "edu/eduSubject/subjectTree";
	}

	/**
	 * 删除
	 */
	@PostMapping( "/getSubjectTeacherList")
	@ResponseBody
	public List remove( String class_id){
		List list=eduSubjectService.getSubjectTeacherList(class_id);
		return list;
	}
	/**
	 * 获取需要考试的科目
	 */
	@PostMapping( "/getSubjectScoreList")
	@ResponseBody
	public List getSubjectScoreList( String class_id){
		List list=eduSubjectService.getSubjectScoreList(class_id);
		return list;
	}
}
