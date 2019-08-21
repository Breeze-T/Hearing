package com.bootdo.edu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.R;
import com.bootdo.edu.domain.CheckDO;
import com.bootdo.edu.service.CheckService;

@Controller
@RequestMapping("/edu/check")
public class CheckController extends BaseController {
	private String prefix = "edu/eduCheck";
	@Autowired
	private CheckService checkService;
	
	@GetMapping()
	@RequiresPermissions("edu:eduCheck:eduCheck")
	public String student() {
		return prefix + "/check";
	}
	
	/**
	 *查询 
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("edu:eduCheck:eduCheck")
	public List<CheckDO> list(@RequestParam Map<String, Object> params) {
		List<CheckDO> checkList = checkService.list(params);
		return checkList;
	}
	
	
	@GetMapping("/add/{pId}")
	@RequiresPermissions("edu:eduCheck:add")
	String add(@PathVariable("pId") Long pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("cName", "无");
		} else {
			CheckDO checkDO = checkService.get(pId);
			model.addAttribute("cName",checkDO.getCheckName());
			model.addAttribute("cType",checkDO.getCheckType());
		}
		return  prefix + "/add";
	}
	
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("edu:eduCheck:add")
	public R save(CheckDO checkDO) {
		checkDO.setCreateUser(getUserId().toString());
		checkDO.setCreateTime(new Date());
		if(checkDO.getIsLeaf().equals("0")) {
			checkDO.setScore(null);
		}
		if (checkService.save(checkDO) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	@GetMapping("/edit/{id}")
	@RequiresPermissions("edu:eduCheck:edit")
	String edit(@PathVariable("id") Long id, Model model) {
		CheckDO checkDO = checkService.get(id);
		model.addAttribute("checkDO", checkDO);
		if(checkDO.getPId().equals(0l)) {
			model.addAttribute("pCheckName", "无");
		}else {
			CheckDO pCheckDO = checkService.get(checkDO.getPId());
			model.addAttribute("pCheckName", pCheckDO.getCheckName());
		}
		return  prefix + "/edit";
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("edu:eduCheck:edit")
	public R update(CheckDO checkDO) {
		if(checkDO.getIsLeaf().equals("0")) {
			checkDO.setScore(null);
		}
		if (checkService.update(checkDO) > 0) {
			return R.ok();
		}
		return R.error();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("edu:eduCheck:remove")
	public R remove(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		map.put("pId", id);
		map2.put("checkId", id);
		if(checkService.count(map)>0) {
			return R.error(1, "包含下级节点,不允许删除！");
		}
		if(checkService.checkHasUse(map2)>0) {
			return R.error(1, "该项已用于学员考核,不允许删除");
		}else {
			if (checkService.remove(id) > 0) {
				return R.ok();
			}
		}
		return R.error();
	}
}
