package com.bootdo.edu.service;

import com.bootdo.edu.domain.EduClassDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-11-29 14:37:11
 */
public interface EduClassService {
	
	EduClassDO get(String id);
	Map getMap(String id);
	List<Map> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduClassDO eduClass,String positionString);
	
	int update(EduClassDO eduClass,String positionString);
	int updateScheduleDate(EduClassDO eduClass);
	int remove(String id);
	
	int batchRemove(String[] ids);
	List<Map> getPositionByClassId(String classId);
	List<Map> getSubjectByClassId(String classId);

	int updateSubject(EduClassDO eduClass,String positionString);
	//查询所有的班级名和id供导入导出使用 state是all的时候查询所有的，否则只查询未结业 的班级
	List<Map<String,Object>> getAllClass(String state);
}
