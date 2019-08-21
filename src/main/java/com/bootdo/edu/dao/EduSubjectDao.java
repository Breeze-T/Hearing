package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduSubjectDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-06 17:59:49
 */
@Mapper
public interface EduSubjectDao {

	EduSubjectDO get(Integer id);
	
	List<EduSubjectDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduSubjectDO eduSubject);
	
	int update(EduSubjectDO eduSubject);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	List<Map> getSubjectTeacherList(String classId);
	List<Map> getSubjectScoreList(String classId);
}
