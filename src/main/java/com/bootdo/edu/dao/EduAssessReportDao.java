package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduAssessReportDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-26 09:03:31
 */
@Mapper
public interface EduAssessReportDao {

	List<Map> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	List<Map> getClassAssessRatelist(Map<String,Object> map);
	List<Map> listStudentClock(Map<String,Object> map);

	int countStudentClock(Map<String,Object> map);

	List<Map> listSign(Map<String,Object> map);

	int countSign(Map<String,Object> map);

}
