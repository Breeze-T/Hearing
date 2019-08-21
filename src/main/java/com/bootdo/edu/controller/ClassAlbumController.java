package com.bootdo.edu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.R;
import com.bootdo.edu.domain.ClassAlbumDO;
import com.bootdo.edu.domain.ClassAlbumDetailDO;
import com.bootdo.edu.service.ClassAlbumService;
import com.bootdo.edu.service.EduClassService;

@Controller
@RequestMapping("/edu/classAlbum")
public class ClassAlbumController extends BaseController {
	@Autowired
	private ClassAlbumService classAlbumService;
	@Autowired
	private EduClassService classService;
	
	
	@GetMapping()
	@RequiresPermissions("edu:eduClassAlbum:eduClassAlbum")
	public String classAlbum(Model model, String classId) {
		model.addAttribute("classId", classId);
		return "edu/eduClassAlbum/classAlbum";
	}
	
	
	/**
	 *列表
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduClassAlbum:eduClassAlbum")
	public List<ClassAlbumDO> list(String classId) {
		// 查询列表数据
		List<ClassAlbumDO> list = classAlbumService.list(classId);
		return list;
	}
	
	/**
	 *新增
	 */
	@ResponseBody
	@RequestMapping("/add")
	public R add(ClassAlbumDO classAlbum,MultipartHttpServletRequest request) {
		classAlbum.setCreateTime(new Date());
		classAlbum.setCreateUser(getUserId());
		try {
			classAlbumService.add(classAlbum,request);
		}catch(Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return R.ok();
	}
	
	
	/**
	 *编辑添加
	 */
	@ResponseBody
	@RequestMapping("/editAdd")
	public List<ClassAlbumDetailDO> editAdd(Long id,MultipartHttpServletRequest request) {
		List<ClassAlbumDetailDO> list = new ArrayList<ClassAlbumDetailDO>();
		try {
			list=classAlbumService.editAdd(id,request);
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<ClassAlbumDetailDO>();
		}
		return list;
	}
	
	/**
	 *确认
	 */
	@ResponseBody
	@RequestMapping("/confirm")
	public R confirm(ClassAlbumDO classAlbum) {
		classAlbum.setUpdateUser(getUserId());
		classAlbum.setUpdateTime(new Date());
		if(classAlbumService.update(classAlbum)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 *编辑删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public R delete(Long detailId) {
		try {
			classAlbumService.delete(detailId);
		}catch(Exception e) {
			e.printStackTrace();
			return R.error();
		}
		return R.ok();
	}
	/**
	 *加载班级下拉查询条件
	 * state是all的时候查询所有的，否则只查询未结业 的班级
	 */
	@ResponseBody
	@GetMapping("/classChoose")
	public List<Map<String, Object>> classChoose(String state) {
		return classService.getAllClass(state);
	}
}
