package com.bootdo.activiti.dao;

import com.bootdo.activiti.domain.SalaryDO;
import com.bootdo.activiti.domain.StudentLeave;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentLeaveDao {

	StudentLeave get(String id);
	
	List<StudentLeave> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StudentLeave studentLeave);

	@Update("update staff_student_leave set status=#{status},remark=#{remark},update_user=#{auditor},update_date=now() where id=#{id}")
	public void update(StudentLeave studentLeave);

	@Select("SELECT a.user_id userId from edu_student a where a.type=#{type} and a.class_id=#{classId}")
	List<String> getCaptainByClass(@Param("type")String type,@Param("classId")String classId);

	@Select("SELECT a.position_user userId from edu_position a where a.position_type in(${type}) and a.class_id=#{classId}")
	List<String> getHeadMasterByClass(@Param("type")String type,@Param("classId")String classId);

	@Update("update staff_student_leave set sick_status=#{status},sick_remark=#{remark} where id=#{id}")
	void updateSickStatus(@Param("status")String status,@Param("remark")String remark,@Param("id")String id);

	@Select("SELECT DATE_FORMAT(a.`start_time`,'%Y-%m-%d %H:%i') startTime," +
			"DATE_FORMAT(a.`end_time`,'%Y-%m-%d %H:%i') endTime,a.days,a.procins_id procInsId,c.student_name studentName," +
			"a.leaveType,a.reason,a.remark,a.`status`,a.sick_status sickStatus,a.sick_remark sickRemark,IFNULL(a.hours,'0') hours," +
			"DATE_FORMAT(a.`create_date`,'%Y-%m-%d %H:%i:%s') createDate " +
			"from staff_student_leave a left join act_hi_procinst b on b.PROC_INST_ID_=a.procins_id " +
			"left join edu_student c on a.create_user=c.user_id " +
			"left join act_hi_taskinst d on a.procins_id=d.PROC_INST_ID_ " +
			"where b.END_ACT_ID_='end' and b.START_ACT_ID_='startLeave' and d.ASSIGNEE_=#{userId}")
	List<Map<String,Object>> getHisLeave(@Param("userId")String userId);

	int remove(String id);
	
	int batchRemove(String[] ids);
}
