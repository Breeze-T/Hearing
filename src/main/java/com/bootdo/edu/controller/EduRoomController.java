package com.bootdo.edu.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
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

import com.bootdo.edu.domain.EduRoomDO;
import com.bootdo.edu.service.EduRoomService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-10 10:52:54
 */
 
@Controller
@RequestMapping("/edu/eduRoom")
public class EduRoomController {
	@Autowired
	private EduRoomService eduRoomService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduRoom:eduRoom")
	String EduRoom(){
	    return "edu/eduRoom/eduRoom";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduRoom:eduRoom")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EduRoomDO> eduRoomList = eduRoomService.list(query);
		int total = eduRoomService.count(query);
		PageUtils pageUtils = new PageUtils(eduRoomList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("edu:eduRoom:add")
	String add(){
	    return "edu/eduRoom/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduRoom:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		EduRoomDO eduRoom = eduRoomService.get(id);
		model.addAttribute("eduRoom", eduRoom);
	    return "edu/eduRoom/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduRoom:add")
	public R save( EduRoomDO eduRoom){
		if(eduRoomService.save(eduRoom)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduRoom:edit")
	public R update( EduRoomDO eduRoom){
		eduRoomService.update(eduRoom);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduRoom:remove")
	public R remove( Integer id){
		if(eduRoomService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	/**
	 * 删除
	 */
	@PostMapping( "/getAllRoomList")
	@ResponseBody
	public List getAllRoomList( String class_id){
		Map<String, Object> params=new HashedMap();
		params.put("state","1");
		List<EduRoomDO> eduRoomList =eduRoomService.list(params);
		return eduRoomList;
	}
}
