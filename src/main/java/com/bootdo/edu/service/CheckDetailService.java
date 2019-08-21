package com.bootdo.edu.service;

import java.util.List;
import java.util.Map;

import com.bootdo.edu.domain.CheckDetailDO;
import com.bootdo.edu.domain.StudentDO;

public interface CheckDetailService {
	public List<StudentDO> list(Map<String,Object> map);

	public int count(Map<String, Object> map);
	
	public int confirm(String studentId,String[] checkIds,String[] remarkArr,String userId);
	
	public List<CheckDetailDO> detailList(Map<String,Object> map);
	
	public int remove(String id);
	//获取学生课程成绩列表
	public List<Map> studentScoreList(Map<String,Object> map);
	//保存学生课程成绩
	int saveStudentScore(Map<String, Object> map);
	//获取学生每次考核的总成绩
	List<Map> getTotalScoreList(Map<String,Object> map);
	List<Map> getCheckDetailListNew(Map<String,Object> map);
	int countCheckDetailListNew(Map<String, Object> map);

	List<Map> checkDetailTypeNew(Map<String,Object> map);
}
