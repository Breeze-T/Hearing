package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduTeacherDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-04 10:41:01
 */
@Mapper
public interface EduTeacherDao {

	EduTeacherDO get(String id);
	
	List<EduTeacherDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduTeacherDO eduTeacher);
	
	int update(EduTeacherDO eduTeacher);
	int updateScoreByUserId(EduTeacherDO eduTeacher);
	int remove(String id);
	
	int batchRemove(String[] ids);
	Map getTeacherAssess(Map<String, Object> map);
	List<Map> getStudentCount(Map<String, Object> map);
	List<EduTeacherDO> workLoadList(Map<String, Object> map);
}
