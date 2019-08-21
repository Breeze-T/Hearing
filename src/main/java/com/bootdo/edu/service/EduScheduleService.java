package com.bootdo.edu.service;

import com.bootdo.edu.domain.EduScheduleDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-11 09:30:01
 */
public interface EduScheduleService {
	
	EduScheduleDO get(Integer id);
	
	List<Map> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	Map save(EduScheduleDO eduSchedule,String flag);
	
	int update(EduScheduleDO eduSchedule);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	List<Map> exitList(EduScheduleDO eduSchedule);
	int deleteSchedule(EduScheduleDO eduSchedule);
}
