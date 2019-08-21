package com.bootdo.edu.service.impl;

import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.edu.dao.EduRoomDao;
import com.bootdo.edu.domain.EduRoomDO;
import com.bootdo.edu.service.EduRoomService;



@Service
public class EduRoomServiceImpl implements EduRoomService {
	@Autowired
	private EduRoomDao eduRoomDao;
	
	@Override
	public EduRoomDO get(Integer id){
		return eduRoomDao.get(id);
	}
	
	@Override
	public List<EduRoomDO> list(Map<String, Object> map){
		return eduRoomDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduRoomDao.count(map);
	}
	
	@Override
	public int save(EduRoomDO eduRoom){
		UserDO userDO= ShiroUtils.getUser();
		eduRoom.setCreateUser(userDO.getUserId()+"");
		eduRoom.setCreateTime(new Date());
		return eduRoomDao.save(eduRoom);
	}
	
	@Override
	public int update(EduRoomDO eduRoom){
		return eduRoomDao.update(eduRoom);
	}
	
	@Override
	public int remove(Integer id){
		return eduRoomDao.remove(id);
	}
	
}
