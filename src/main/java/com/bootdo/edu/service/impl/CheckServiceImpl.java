package com.bootdo.edu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.edu.dao.CheckDao;
import com.bootdo.edu.dao.CheckDetailDao;
import com.bootdo.edu.domain.CheckDO;
import com.bootdo.edu.service.CheckService;
import com.bootdo.system.domain.DeptDO;
@Service
public class CheckServiceImpl implements CheckService {
	@Autowired
	private CheckDao checkDao;
	@Autowired
	private CheckDetailDao checkDetailDao;
	
	
	@Override
	public List<CheckDO> list(Map<String, Object> map) {
		return checkDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map) {
		return checkDao.count(map);
	}

	@Override
	public CheckDO get(Long deptId) {
		return checkDao.get(deptId);
	}

	@Override
	public int save(CheckDO checkDO) {
		return checkDao.save(checkDO);
	}

	@Override
	public int update(CheckDO checkDO) {
		return checkDao.update(checkDO);
	}

	@Override
	public int checkHasUse(Map<String, Object> map) {
		return checkDetailDao.count(map);
	}

	@Override
	public int remove(Long id) {
		return checkDao.remove(id);
	}

	

}
