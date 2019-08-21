package com.bootdo.edu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.edu.domain.CheckDetailDO;
import com.bootdo.edu.domain.StudentDO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CheckDetailDao {
	
	public List<StudentDO> list(Map<String,Object> map);
	public int count(Map<String, Object> map);
	public int batchInsert(List<CheckDetailDO> detailList);
	public List<CheckDetailDO> detailList(Map<String,Object> map);
	public int remove(String id);
	public List<Map> studentScoreList(Map<String,Object> map);
	int batchSaveStudentScore(@Param("list") List list,@Param("num") String num);
	int batchRemoveScoreByStudentId(@Param("list") List list,@Param("num") String num);
	//获取学生每次考核的总成绩
	List<Map> getTotalScoreList(Map<String,Object> map);
	List<Map> getCheckDetailListNew(Map<String,Object> map);

	int countCheckDetailListNew(Map<String, Object> map);

	List<Map> checkDetailTypeNew(Map<String,Object> map);
}
