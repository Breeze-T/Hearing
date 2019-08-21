package com.bootdo.edu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.edu.dao.CheckDetailDao;
import com.bootdo.edu.domain.CheckDetailDO;
import com.bootdo.edu.domain.StudentDO;
import com.bootdo.edu.service.CheckDetailService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckDetailServiceImpl implements CheckDetailService {
	@Autowired
	private CheckDetailDao checkDetailDao;
	@Override
	public List<StudentDO> list(Map<String, Object> map) {
		return checkDetailDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return checkDetailDao.count(map);
	}

	@Override
	public int confirm(String studentId, String[] checkIds,String[] remarkArr,String userId) {
		if(remarkArr.length==0){
			remarkArr=new String[1];
		}
		List<CheckDetailDO> detailList = new ArrayList<CheckDetailDO>();
		Date date = new Date();
		for (int i=0;i<checkIds.length;i++) {
			CheckDetailDO checkDetail = new CheckDetailDO();
			checkDetail.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			checkDetail.setStudentId(studentId);
			checkDetail.setCheckId(checkIds[i]);
			checkDetail.setRemark(remarkArr[i]);
			checkDetail.setCreateUser(userId);
			checkDetail.setCreateTime(date);
			detailList.add(checkDetail);
		}
		return checkDetailDao.batchInsert(detailList);
	}

	@Override
	public List<CheckDetailDO> detailList(Map<String, Object> map) {
		return checkDetailDao.detailList(map);
	}

	@Override
	public int remove(String id) {
		return checkDetailDao.remove(id);
	}

	@Override
	public List<Map> studentScoreList(Map<String, Object> map) {
		return checkDetailDao.studentScoreList(map);
	}
	@Override
	@Transactional
	public int saveStudentScore(Map<String, Object> map) {
		List studentList=(List)map.get("studentList");//需要修改的学生
		List scoreList=(List)map.get("scoreList");//需要修改的成绩
		String num=(String)map.get("num");//需要修改的成绩
		int aa=checkDetailDao.batchRemoveScoreByStudentId(studentList,num);//先删除学生的成绩
		int bb=0;
		if(scoreList.size()>0){
			bb=checkDetailDao.batchSaveStudentScore(scoreList,num);//再插入学生的成绩
		}

		//return checkDetailDao.saveStudentScore(map);
		return bb;
	}
	@Override
	public List<Map> getTotalScoreList(Map<String, Object> map) {
		return checkDetailDao.getTotalScoreList(map);
	}

	@Override
	public List<Map> getCheckDetailListNew(Map<String, Object> map) {
		return checkDetailDao.getCheckDetailListNew(map);
	}

	@Override
	public int countCheckDetailListNew(Map<String, Object> map) {
		return checkDetailDao.countCheckDetailListNew(map);
	}

	@Override
	public List<Map> checkDetailTypeNew(Map<String, Object> map) {
		return checkDetailDao.checkDetailTypeNew(map);
	}
}
