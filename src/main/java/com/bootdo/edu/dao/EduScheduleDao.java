package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduScheduleDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-11 09:30:01
 */
@Mapper
public interface EduScheduleDao {

	EduScheduleDO get(Integer id);
	
	List<Map> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduScheduleDO eduSchedule);
	
	int update(EduScheduleDO eduSchedule);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	List<Map> exitList(EduScheduleDO eduSchedule);
	List<Map> getIdByDateSection(EduScheduleDO eduSchedule);
	int deleteSchedule(EduScheduleDO eduSchedule);
}
