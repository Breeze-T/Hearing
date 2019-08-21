package com.bootdo.edu.service.impl;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.edu.dao.EduDormitoryDao;
import com.bootdo.edu.domain.EduDormitoryDO;
import com.bootdo.edu.service.EduDormitoryService;



@Service
public class EduDormitoryServiceImpl implements EduDormitoryService {
	@Autowired
	private EduDormitoryDao eduDormitoryDao;
	
	@Override
	public EduDormitoryDO get(Integer id){
		return eduDormitoryDao.get(id);
	}
	
	@Override
	public List<EduDormitoryDO> list(Map<String, Object> map){
		return eduDormitoryDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduDormitoryDao.count(map);
	}
	
	@Override
	public int save(EduDormitoryDO eduDormitory){
		UserDO userDO= ShiroUtils.getUser();
		eduDormitory.setCreateUser(userDO.getUserId()+"");
		eduDormitory.setCreateTime(new Date());
		return eduDormitoryDao.save(eduDormitory);
	}
	
	@Override
	public int update(EduDormitoryDO eduDormitory){
		return eduDormitoryDao.update(eduDormitory);
	}
	
	@Override
	public int remove(Integer id){
		return eduDormitoryDao.remove(id);
	}
	
}
