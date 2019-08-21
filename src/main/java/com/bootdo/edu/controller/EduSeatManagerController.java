package com.bootdo.edu.controller;

import com.bootdo.common.utils.R;
import com.bootdo.edu.domain.SeatTab;
import com.bootdo.edu.service.SearchSeatService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-19 08:44:48
 */
 
@Controller
@RequestMapping("/edu/seatManager")
public class EduSeatManagerController {

	@Autowired
	private SearchSeatService searchSeatService;
	
	@GetMapping()
	String EduDormitoryStudent(){
	    return "edu/eduSeatManager/seatManager";
	}

	/**
	 * 查询座位表
	 * @return
	 */
	@RequestMapping("/searchSeatList")
	@ResponseBody
	public List<SeatTab> searchSeatList(String gradeId, String classId) {
		return searchSeatService.searchSeatList(gradeId,classId);
	}

	/**
	 * 查询所在班级学生表
	 * @return
	 */
	@RequestMapping("/serchStudt")
	@ResponseBody
	public List<Map> serchStudtList(String classId) {
		List<Map> list = searchSeatService.serchStudtList(classId);
		return list;
	}

	/**
	 * 保存列表
	 * @return
	 */
	@RequestMapping("/saveSeat")
	@ResponseBody
	public R  saveSeatList(String seats,String line,String gradeId,String classId,String row) {
		seats=seats.substring(0,seats.length()-1);
		String[] seatArray= seats.split(",");
		String[] seat;
		SeatTab seatTab;
		searchSeatService.delSeatList(gradeId, classId);
		for (int i = 0; i < seatArray.length; i++) {
			seat=seatArray[i].split(":");
			if(!StringUtils.isNullOrEmpty(seat[1])&&!seat[1].equals("\"\"")){
				seatTab=new SeatTab();
				seatTab.setClassId(classId);
				seatTab.setId(UUID.randomUUID().toString());
				seatTab.setLine(line);
				seatTab.setSeat((i+1)+"");
				seatTab.setRow(row);
				seatTab.setStudentId(seat[1].replaceAll("\"",""));
				searchSeatService.saveSeat(seatTab);
			}
		}
		return  R.ok();
	}
}
