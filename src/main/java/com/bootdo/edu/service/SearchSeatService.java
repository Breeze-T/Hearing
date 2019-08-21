package com.bootdo.edu.service;

import com.bootdo.edu.dao.SearchSeatDao;
import com.bootdo.edu.domain.SeatTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchSeatService {
	
	@Autowired
	private SearchSeatDao searchSeatDao;
	
	public List<SeatTab> searchSeatList(String gradeId, String classId) {
		return searchSeatDao.searchSeatList(gradeId,classId);
	}
	
	public void delSeatList(String gradeId,String classId) {
		searchSeatDao.delSeatList(gradeId,classId);
	}
	
	public List<Map> serchStudtList(String classId) {
		return searchSeatDao.serchStudtList(classId);
	}
	
	public int saveSeat(SeatTab seatTab) {
		return searchSeatDao.insert(seatTab);
	}

}
