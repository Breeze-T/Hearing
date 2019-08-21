package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduDormitoryDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-17 09:05:21
 */
@Mapper
public interface EduDormitoryDao {

	EduDormitoryDO get(Integer id);
	
	List<EduDormitoryDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduDormitoryDO eduDormitory);
	
	int update(EduDormitoryDO eduDormitory);
	
	int remove(Integer id);
	List<EduDormitoryDO> getEmptylist(Map<String,Object> map);
	List<EduDormitoryDO> getClassDormitorylist(Map<String,Object> map);
}
