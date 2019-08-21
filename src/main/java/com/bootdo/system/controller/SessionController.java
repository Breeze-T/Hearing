package com.bootdo.system.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootdo.common.utils.R;
import com.bootdo.system.domain.UserOnline;
import com.bootdo.system.service.SessionService;

@RequestMapping("/sys/online")
@Controller
public class SessionController {
	@Autowired
	SessionService sessionService;

	@GetMapping()
	public String online() {
		return "system/online/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<UserOnline> list(@RequestParam Map<String, Object> params) {
		List<UserOnline> list = sessionService.list();
		String name=params.get("name")+"";
		if(!"".equals(name)){
			String userName="";
			List<UserOnline> listNew = new ArrayList<>();
			for (UserOnline userOnline:list){
				userName=userOnline.getUsername();
				if(name.equals(userName)){
					listNew.add(userOnline);
				}
			}
			return listNew;
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}


}
