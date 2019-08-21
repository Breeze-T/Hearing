package com.bootdo.edu.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.edu.dao.EduPositionDao;
import com.bootdo.edu.domain.EduPositionDO;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.bootdo.edu.dao.EduClassDao;
import com.bootdo.edu.domain.EduClassDO;
import com.bootdo.edu.service.EduClassService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EduClassServiceImpl implements EduClassService {
	@Autowired
	private EduClassDao eduClassDao;
	@Autowired
	private EduPositionDao eduPositionDao;
	@Override
	public EduClassDO get(String id){
		return eduClassDao.get(id);
	}
	@Override
	public Map getMap(String id){
		return eduClassDao.getMap(id);
	}
	@Override
	public List<Map> list(Map<String, Object> map){
		return eduClassDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduClassDao.count(map);
	}
    @Transactional
	@Override
	public int save(EduClassDO eduClass,String positionString){
		UserDO userDO=ShiroUtils.getUser();
		eduClass.setCreateUser(userDO.getUserId()+"");
		eduClass.setCreateTime(new Date());
		int c=eduClassDao.save(eduClass);
		String id=eduClass.getId();
		//保存岗位
		String bzr=savePosition(id,positionString);
		eduClass.setClassAdviser(bzr);
		c=eduClassDao.update(eduClass);
		return 1;
	}
	public String savePosition(String id,String positionString){
		//获取岗位人员集合
		String bzr="";
		List <Map>list=(List)JSONArray.parseArray(positionString);
		for (Map map:list){
			//判断某个岗位是不是多个人
			String userStr=map.get("positionUser")+"";
			if(userStr.indexOf(",")>0){
				String[] array = userStr.split(",");
				List<String> userStrList = Arrays.asList(array);
				for (String userId:userStrList){
					EduPositionDO eduPositionDO=new EduPositionDO();
					String positionCode=map.get("positionCode")+"";
					if("jg".equals(positionCode)){
						eduPositionDO.setPositionType("1");//1是
					}else{
						eduPositionDO.setPositionType("0");
					}
					eduPositionDO.setPositionCode(positionCode);//positionCode
					eduPositionDO.setPositionName(map.get("positionName")+"");//positionCode
					eduPositionDO.setPositionId(map.get("positionId")+"");//positionCode
					eduPositionDO.setClassId(id);
					eduPositionDO.setPositionUser(userId);
					eduPositionDao.save(eduPositionDO);
				}
			}else {
				EduPositionDO eduPositionDO=new EduPositionDO();
				String positionCode=map.get("positionCode")+"";
				if("jg".equals(positionCode)){
					eduPositionDO.setPositionType("1");//1是
				}else if("bzr".equals(positionCode)){
					eduPositionDO.setPositionType("2");//2是班主任
					bzr=userStr;
				}else{
					eduPositionDO.setPositionType("0");
				}
				eduPositionDO.setPositionCode(positionCode);//positionCode
				eduPositionDO.setPositionName(map.get("positionName")+"");//positionCode
				eduPositionDO.setPositionId(map.get("positionId")+"");//positionCode
				eduPositionDO.setClassId(id);
				eduPositionDO.setPositionUser(userStr);
				eduPositionDao.save(eduPositionDO);
			}

		}
		return bzr;
	};
	@Transactional
	@Override
	public int update(EduClassDO eduClass,String positionString){
		UserDO userDO=ShiroUtils.getUser();
		eduClass.setUpdateUser(userDO.getUserId()+"");
		eduClass.setUpdateTime(new Date());
		String id=eduClass.getId();
		int del=eduPositionDao.remove(id);
		//保存岗位
		String bzr=savePosition(id,positionString);
		eduClass.setClassAdviser(bzr);
		eduClassDao.update(eduClass);
			return 1;

	}
	
	@Override
	public int remove(String id){
		return eduClassDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return eduClassDao.batchRemove(ids);
	}

	@Override
	public List<Map> getPositionByClassId( String classId) {
		return eduPositionDao.getPositionByClassId(classId);
	}
	@Override
	public List<Map> getSubjectByClassId( String classId) {
		return eduPositionDao.getSubjectByClassId(classId);
	}
    @Transactional
	@Override
	public int updateSubject(EduClassDO eduClass,String positionString){
		String id=eduClass.getId();
		//保存岗位
		int del=eduPositionDao.removeSubject(id);
		//获取岗位人员集合
		List <Map>list=(List)JSONArray.parseArray(positionString);
		for (Map map:list){
			//判断某个岗位是不是多个人
			String userStr=map.get("subjectId")+"";
			if(userStr.indexOf(",")>0) {
				String[] array = userStr.split(",");
				List<String> userStrList = Arrays.asList(array);
				for (String subjectId : userStrList) {
					EduPositionDO eduPositionDO=new EduPositionDO();
					eduPositionDO.setSubjectId(subjectId);
					eduPositionDO.setClassId(id);
					eduPositionDO.setPositionUser(map.get("positionUser")+"");
					eduPositionDao.saveSubject(eduPositionDO);
				}
			}else {
				EduPositionDO eduPositionDO=new EduPositionDO();
				eduPositionDO.setSubjectId(userStr);
				eduPositionDO.setClassId(id);
				eduPositionDO.setPositionUser(map.get("positionUser")+"");
				eduPositionDao.saveSubject(eduPositionDO);
			}


		}
		return 1;
	}


	@Override
	public List<Map<String, Object>> getAllClass(String state) {
		return eduClassDao.getAllClass(state);
	}
	@Override
	public int updateScheduleDate(EduClassDO eduClass){
		//保存时间
		return eduClassDao.update(eduClass);

	}
}