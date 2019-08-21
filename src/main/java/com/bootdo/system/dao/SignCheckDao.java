package com.bootdo.system.dao;

import com.bootdo.system.domain.RoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-02 20:24:47
 */
@Mapper
public interface SignCheckDao {

	List<Map<String,Object>> list(Map<String, Object> map);
	int count(Map<String, Object> map);
}
