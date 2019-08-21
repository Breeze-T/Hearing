package com.bootdo.edu.service;

import com.bootdo.edu.domain.EduTeacherDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-04 10:41:01
 */
public interface EduTeacherService {
	
	EduTeacherDO get(String id);
	
	List<EduTeacherDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduTeacherDO eduTeacher);
	
	int update(EduTeacherDO eduTeacher);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	 List getTeacherAssess(String  userId,boolean flag);
	List<EduTeacherDO> workLoadList(Map<String, Object> map);
}
