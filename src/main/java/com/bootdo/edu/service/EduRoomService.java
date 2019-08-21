package com.bootdo.edu.service;

import com.bootdo.edu.domain.EduRoomDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-10 10:52:54
 */
public interface EduRoomService {
	
	EduRoomDO get(Integer id);
	
	List<EduRoomDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduRoomDO eduRoom);
	
	int update(EduRoomDO eduRoom);
	
	int remove(Integer id);
}
