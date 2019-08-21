package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduPositionDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-03 15:48:36
 */
@Mapper
public interface EduPositionDao {

	EduPositionDO get(Long id);
	
	List<EduPositionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduPositionDO eduPosition);
	int saveSubject(EduPositionDO eduPosition);

	int update(EduPositionDO eduPosition);
	
	int remove(String id);
	int removeSubject(String id);

	int batchRemove(Long[] ids);
	List<Map> getPositionByClassId(String classId);
	List<Map> getSubjectByClassId(String classId);
}
