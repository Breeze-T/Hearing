package com.bootdo.edu.service.impl;

import com.bootdo.common.utils.MD5Utils;
import com.bootdo.system.dao.DeptDao;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.dao.UserRoleDao;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.domain.UserRoleDO;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.bootdo.edu.dao.EduTeacherDao;
import com.bootdo.edu.domain.EduTeacherDO;
import com.bootdo.edu.service.EduTeacherService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EduTeacherServiceImpl implements EduTeacherService {
	@Autowired
	private EduTeacherDao eduTeacherDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	UserRoleDao userRoleMapper;
	@Override
	public EduTeacherDO get(String id){
		EduTeacherDO eduTeacherDO=eduTeacherDao.get(id);
		UserDO user = userDao.get(Long.valueOf(eduTeacherDO.getUserId()));
		DeptDO deptDO=deptMapper.get(user.getDeptId());
		if(deptDO==null){
			eduTeacherDO.setDeptName("");
		}else {
			eduTeacherDO.setDeptName(deptDO.getName());
		}
		return eduTeacherDO;
	}
	
	@Override
	public List<EduTeacherDO> list(Map<String, Object> map){
		return eduTeacherDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduTeacherDao.count(map);
	}
	
	@Override
	public int save(EduTeacherDO eduTeacher){
		//插用户表
		UserDO user = new UserDO();
		user.setName(eduTeacher.getName());
		user.setUsername(eduTeacher.getPoliceNumber());//警号作为默认账号
		user.setPassword(MD5Utils.encrypt(user.getUsername(), "000000"));
		user.setDeptId(eduTeacher.getDeptId());
		user.setMobile(eduTeacher.getPhone());
		user.setGmtCreate(new Date());
		user.setStatus(1);
		int count = userDao.save(user);
		//赋予权限
		//查询学生角色id(如果没有则不插)
		RoleDO userRole = userRoleMapper.getByRoleSign("teacher");
		if(userRole != null &&count>0) {
			UserRoleDO ur = new UserRoleDO();
			ur.setUserId(user.getUserId());
			ur.setRoleId(userRole.getRoleId());
			count=userRoleMapper.save(ur);
		}
		eduTeacher.setUserId(user.getUserId()+"");
		eduTeacher.setId(UUID.randomUUID().toString().replace("-",""));
		return eduTeacherDao.save(eduTeacher);
	}
	
	@Override
	public int update(EduTeacherDO eduTeacher){
		return eduTeacherDao.update(eduTeacher);
	}
	@Transactional
	@Override
	public int remove(String id){
		//删除的同时还需删除用户
		EduTeacherDO eduTeacherDO=eduTeacherDao.get(id);
		 userDao.remove(Long.valueOf(eduTeacherDO.getUserId()));
		return eduTeacherDao.remove(id);
	}
	
	@Override
	public int batchRemove(String[] ids){
		return eduTeacherDao.batchRemove(ids);
	}

	/**
	 *
	 * @param userId 教师userid
	 * @param flag true更新教师表
	 * @return
	 */
	public List getTeacherAssess(String  userId,boolean flag){
		int point=50;//大于多少
		int big=3;//去掉几个最高分
		int Small=2;//去掉几个最低分
		List list=new ArrayList();
		//userId="136";
		Map sqlMap=new HashedMap();
		sqlMap.put("userId",userId);
		List <Map>studentCountList = eduTeacherDao.getStudentCount(sqlMap);//查询该老师有多少个班级学员的评分
		Map assess=null;
		String classId;
		int studentCount;
		int count;
		double totalVag=0d;
		for (Map ssmap : studentCountList) {
			classId=ssmap.get("classId")+"";
			studentCount=Integer.valueOf(ssmap.get("studentCount")+"");//班级总人数
			count=Integer.valueOf(ssmap.get("count")+"");//该班级评价表数量
			//班级人数大于50
			if(studentCount>point){
				if(count-big*2<1){
					continue;
				}
				//去掉三个最高分和三个最低分
				sqlMap.put("starIndex",big);
				sqlMap.put("endIndex",count-big*2);
			}else {
				if(count-Small*2<1){
					continue;
				}
				//去掉2个最高分和2个最低分
				sqlMap.put("starIndex",Small);
				sqlMap.put("endIndex",count-Small*2);
			}
			sqlMap.put("classId",classId);
			assess= eduTeacherDao.getTeacherAssess(sqlMap);//查询每个班级的该老师平均分
			totalVag+=Double.valueOf(assess.get("avg")+"");
			list.add(assess);
		}
		if(flag&&list.size()>0){
			double avg=totalVag/list.size();
			avg=(double)Math.round(avg * 100)/100;//四舍五入保留2位小数
			EduTeacherDO eduTeacher=new EduTeacherDO();
			eduTeacher.setUserId(userId);
			eduTeacher.setScore(avg);
			int a=eduTeacherDao.updateScoreByUserId(eduTeacher);
		}
		return list;
	}
	@Override
	public List<EduTeacherDO> workLoadList(Map<String, Object> map){
		return eduTeacherDao.workLoadList(map);
	}
}
