package com.bootdo.edu.service;

import com.bootdo.common.domain.Tree;
import com.bootdo.edu.domain.EduDormitoryStudentDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-19 08:44:48
 */
public interface EduDormitoryStudentService {
	
	EduDormitoryStudentDO get(String userId);
	
	List<EduDormitoryStudentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduDormitoryStudentDO eduDormitoryStudent);
	
	int update(EduDormitoryStudentDO eduDormitoryStudent);
	
	int remove(String userId);
	
	int batchRemove(String[] userIds);
	Tree<Map> getStudentGroup(Map map);
	Boolean changeGroup(Long classIdOld,Long classIdNew);

	Tree<Map> getDataBaseStudentGroup(Map map);
	int getDormitoryStudent(Map map);
	List<Map> getDormitoryStudentList(Map map);
	Boolean changeStudentDormitory(Map map);
	List<Map> setDormitoryStudentList(Map map);
	Map getMapStudentDormitory(Long studentId);
}
