package com.bootdo.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.edu.dao.EduAssessReportDao;
import com.bootdo.edu.domain.EduAssessReportDO;
import com.bootdo.edu.service.EduAssessReportService;



@Service
public class EduAssessReportServiceImpl implements EduAssessReportService {
	@Autowired
	private EduAssessReportDao eduAssessReportDao;
	

	@Override
	public List<Map> list(Map<String, Object> map){
		return eduAssessReportDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduAssessReportDao.count(map);
	}
	@Override
	public List<Map> getClassAssessRatelist(Map<String, Object> map){
		return eduAssessReportDao.getClassAssessRatelist(map);
	}

	@Override
	public List<Map> listStudentClock(Map<String, Object> map) {
		return eduAssessReportDao.listStudentClock(map);
	}


	@Override
	public int countStudentClock(Map<String, Object> map) {
		return eduAssessReportDao.countStudentClock(map);
	}

	@Override
	public List<Map> listSign(Map<String, Object> map) {
		return eduAssessReportDao.listSign(map);
	}
	@Override
	public int countSign(Map<String, Object> map) {
		return eduAssessReportDao.countSign(map);
	}

}
