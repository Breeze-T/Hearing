package com.bootdo.edu.service;

import com.bootdo.common.domain.Tree;
import com.bootdo.edu.domain.EduSubjectDO;
import com.bootdo.system.domain.DeptDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-06 17:59:49
 */
public interface EduSubjectService {
	
	EduSubjectDO get(Integer id);
	
	List<EduSubjectDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduSubjectDO eduSubject);
	
	int update(EduSubjectDO eduSubject);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	Tree<DeptDO> getTree();

	List<Map> getSubjectTeacherList(String classId);
	List<Map> getSubjectScoreList(String classId);
}
