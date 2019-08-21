package com.bootdo.edu.controller;

import java.util.List;
import java.util.Map;

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

import com.bootdo.edu.domain.EduDormitoryDO;
import com.bootdo.edu.service.EduDormitoryService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-17 09:05:21
 */
 
@Controller
@RequestMapping("/edu/eduDormitory")
public class EduDormitoryController {
	@Autowired
	private EduDormitoryService eduDormitoryService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduDormitory:eduDormitory")
	String EduDormitory(){
	    return "edu/eduDormitory/eduDormitory";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduDormitory:eduDormitory")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EduDormitoryDO> eduDormitoryList = eduDormitoryService.list(query);
		int total = eduDormitoryService.count(query);
		PageUtils pageUtils = new PageUtils(eduDormitoryList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduDormitory:add")
	String add(){
	    return "edu/eduDormitory/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduDormitory:edit")
	//@RequiresPermissions("${pathName}:${classname}:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		EduDormitoryDO eduDormitory = eduDormitoryService.get(id);
		model.addAttribute("eduDormitory", eduDormitory);
	    return "edu/eduDormitory/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduDormitory:add")
	public R save( EduDormitoryDO eduDormitory){
		if(eduDormitoryService.save(eduDormitory)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduDormitory:edit")
	public R update( EduDormitoryDO eduDormitory){
		eduDormitoryService.update(eduDormitory);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduDormitory:remove")
	public R remove( Integer id){
		if(eduDormitoryService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
}
