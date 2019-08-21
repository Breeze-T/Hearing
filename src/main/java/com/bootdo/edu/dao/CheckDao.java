package com.bootdo.edu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.edu.domain.CheckDO;


@Mapper
public interface CheckDao {
	
	
	public List<CheckDO> list(Map<String,Object> map);
	
	public int count(Map<String, Object> map);
	
	public CheckDO get(Long deptId);
	
	public int save(CheckDO checkDO);
	
	public int update(CheckDO checkDO);
	
	public int remove(Long id);
	
}
