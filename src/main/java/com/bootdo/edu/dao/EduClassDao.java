package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduClassDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-11-29 14:37:11
 */
@Mapper
public interface EduClassDao {

	EduClassDO get(String id);
	Map getMap(String id);
	List<Map> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduClassDO eduClass);
	
	int update(EduClassDO eduClass);
	
	int remove(String id);
	
	int batchRemove(String[] ids);
	
	List<Map<String,Object>> getAllClass(@Param("state") String state);
}
