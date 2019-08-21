package com.bootdo.system.service;

import com.bootdo.system.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface SignCheckService {

	List<Map<String,Object>> list(Map<String, Object> map);
	int count(Map<String, Object> map);
}
