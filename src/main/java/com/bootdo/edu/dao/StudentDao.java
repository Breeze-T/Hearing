package com.bootdo.edu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bootdo.edu.domain.StudentDO;
import com.bootdo.system.domain.UserDO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StudentDao {
	public int save(StudentDO studentDo);
	
	public int remove(Long id);
	
	public int update(StudentDO studentDo);

	public List<StudentDO> list(Map<String,Object> map);
	
	public int count(Map<String, Object> map);
	
	public StudentDO get(Long id);
	
	public int batchRemove(Long[] ids);
	
	public int batchRefuse(Long[] ids);
	
	public List<StudentDO> getList(Long[] ids);
	
	public int batchInsert(List<StudentDO> userList);
	//学员分组用到的开始
	public int batchUpdate(List<Map> userList);
	public int updateTempPid(@Param("pIdNew") Long pIdNew,@Param("pIdOld") Long pIdOld);
	public int updatePid(@Param("pIdNew") Long pIdNew,@Param("pIdOld") Long pIdOld);
	public List<Map> getMaplist(Map<String,Object> map);
	//学员分组用到的结束
	//学员统计（根据性别统计学生数量）
	public List<Map<String,Object>> getStudentBySex();
	//根据年龄段统计学生
	public List<Map<String,Object>> getStudentByAge(List<Map<String,String>> list);
	
	public StudentDO profileDetail(@Param("studentId")Long studentId);
	
	//检查数据库中是否存在该警号
	public int checkOnly(@Param("alarmNum")String alarmNum);
	
	
	public List<StudentDO> profileList(Map<String,Object> map);
}
