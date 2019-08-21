package com.bootdo.edu.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.edu.dao.EduScheduleDao;
import com.bootdo.edu.domain.EduScheduleDO;
import com.bootdo.edu.service.EduScheduleService;



@Service
public class EduScheduleServiceImpl implements EduScheduleService {
	@Autowired
	private EduScheduleDao eduScheduleDao;
	
	@Override
	public EduScheduleDO get(Integer id){
		return eduScheduleDao.get(id);
	}
	
	@Override
	public List<Map> list(Map<String, Object> map){
		return eduScheduleDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduScheduleDao.count(map);
	}
	
	@Override
	public Map save(EduScheduleDO eduSchedule,String flag){
		Map outMap=new HashedMap();
		outMap.put("code", "0");
		outMap.put("msg", "操作成功");
		//先判断其他班有没有
		List<Map> schedules=eduScheduleDao.exitList(eduSchedule);
		if(schedules.size() > 0 && !schedules.isEmpty()){
			String msg="";
			if("1".equals(flag)){
				msg="该教师当前课次已在"+schedules.get(0).get("class_name")+"安排了"+schedules.get(0).get("subject_name")+"课程!";
			}else {
				msg="该教室已被"+schedules.get(0).get("class_name")+"安排了"+schedules.get(0).get("subject_name")+"课程!";
			}
			outMap.put("code","1");
			outMap.put("msg",msg);

			return outMap;
		}
		//再判断是插入还是更新
		schedules=eduScheduleDao.getIdByDateSection(eduSchedule);
		String id="";
		if(schedules.size() > 0 && !schedules.isEmpty()){
			id=schedules.get(0).get("id")+"";
		}
		if("".equals(id)){
			//没有查到插入
			eduScheduleDao.save(eduSchedule);
		}else {
			//更新
			eduSchedule.setId(Integer.valueOf(id));
			eduScheduleDao.update(eduSchedule);
		}
		return outMap;
	}
	
	@Override
	public int update(EduScheduleDO eduSchedule){
		return eduScheduleDao.update(eduSchedule);
	}
	
	@Override
	public int remove(Integer id){
		return eduScheduleDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return eduScheduleDao.batchRemove(ids);
	}

	@Override
	public List<Map> exitList(EduScheduleDO eduSchedule) {
		return eduScheduleDao.exitList(eduSchedule);
	}
	@Override
	public int deleteSchedule(EduScheduleDO eduSchedule){
		return eduScheduleDao.deleteSchedule(eduSchedule);
	}
}
