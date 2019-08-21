package com.bootdo.edu.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bootdo.common.utils.ExcelOpen;
import com.bootdo.common.utils.R;
import com.bootdo.edu.domain.StudentDO;

public interface StudentService{
	public int save(StudentDO studentDo,Long userId);
	public void studentSave(StudentDO studentDo);

	public int remove(Long id,Long userId,String status);
	
	public int update(StudentDO studentDo);

	public List<StudentDO> list(Map<String,Object> map);
	
	public int count(Map<String, Object> map);
	
	
	public StudentDO get(Long id);
	
	public int batchRemove(Long[] ids,String[] userIds);
	
	public int pass(Long id,Long userId);
	
	public int batchPass(Long[] ids,Long userId);
	
	public int refuse(Long id);
	
	public int batchRefuse(Long[] ids);
	
	public R batchImport(ExcelOpen readExcelx) throws Exception;
	
	
	//学员统计（根据性别统计学生数量）
	public List<Map<String,Object>> getStudentBySex();
	
	public List<Map<String,Object>> getStudentByAge(List<Map<String,String>> list);
	//学生档案详情
	public StudentDO profileDetail(@Param("studentId")Long studentId);
	
	public List<StudentDO> profileList(Map<String,Object> map);
}