package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduDormitoryStudentDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-19 08:44:48
 */
@Mapper
public interface EduDormitoryStudentDao {

	EduDormitoryStudentDO get(String userId);
	
	List<EduDormitoryStudentDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduDormitoryStudentDO eduDormitoryStudent);
	
	int update(EduDormitoryStudentDO eduDormitoryStudent);
	
	int remove(String classId);
	
	int batchRemove(String[] userIds);
	int saveBatch(List<EduDormitoryStudentDO> list);
	public List<Map> getMaplist(Map<String,Object> map);
	List<EduDormitoryStudentDO> getClassDormitoryStudentlist(Map<String,Object> map);
	public Map getMapStudentDormitory(Long studentId);
}
