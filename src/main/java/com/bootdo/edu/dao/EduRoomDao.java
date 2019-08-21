package com.bootdo.edu.dao;

import com.bootdo.edu.domain.EduRoomDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-10 10:52:54
 */
@Mapper
public interface EduRoomDao {

	EduRoomDO get(Integer id);
	
	List<EduRoomDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(EduRoomDO eduRoom);
	
	int update(EduRoomDO eduRoom);
	
	int remove(Integer id);
}
