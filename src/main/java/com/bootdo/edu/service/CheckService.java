package com.bootdo.edu.service;

import java.util.List;
import java.util.Map;

import com.bootdo.edu.domain.CheckDO;


public interface CheckService {
	
	public List<CheckDO> list(Map<String,Object> map);
	
	public int count(Map<String, Object> map);
	
	public CheckDO get(Long deptId);
	
	public int save(CheckDO checkDO);
	
	public int update(CheckDO checkDO);
	
	public int checkHasUse(Map<String, Object> map);
	
	public int remove(Long id);
}
