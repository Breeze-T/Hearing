package com.bootdo.edu.service;

import com.bootdo.edu.domain.EduDormitoryDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-17 09:05:21
 */
public interface EduDormitoryService {
	
	EduDormitoryDO get(Integer id);
	
	List<EduDormitoryDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(EduDormitoryDO eduDormitory);
	
	int update(EduDormitoryDO eduDormitory);
	
	int remove(Integer id);
}
