package com.bootdo.activiti.service;

import com.bootdo.activiti.domain.SalaryDO;
import com.bootdo.activiti.domain.StudentLeave;
import com.bootdo.common.utils.R;

import java.util.List;
import java.util.Map;

public interface StudentLeaveService {
	
	StudentLeave get(String id);
	
	List<StudentLeave> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StudentLeave studentLeave);

	public R update(StudentLeave studentLeave);

	public List<Map<String,Object>> getHisLeave();

	public void updateSickStatus(String status,String remark,String id);
	
	int remove(String id);
	
	int batchRemove(String[] ids);


}
