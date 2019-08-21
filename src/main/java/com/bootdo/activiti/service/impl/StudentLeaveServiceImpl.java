package com.bootdo.activiti.service.impl;

import com.bootdo.activiti.config.ActivitiConstant;
import com.bootdo.activiti.dao.SalaryDao;
import com.bootdo.activiti.dao.StudentLeaveDao;
import com.bootdo.activiti.domain.SalaryDO;
import com.bootdo.activiti.domain.StudentLeave;
import com.bootdo.activiti.service.SalaryService;
import com.bootdo.activiti.service.StudentLeaveService;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class StudentLeaveServiceImpl implements StudentLeaveService {
	@Autowired
	private StudentLeaveDao studentLeaveDao;
	@Autowired
	private ActTaskServiceImpl actTaskService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    IdentityService identityService;
    @Autowired
    TaskService taskService;


	@Override
	public StudentLeave get(String id) {
		return studentLeaveDao.get(id);
	}

	@Override
	public List<StudentLeave> list(Map<String, Object> map) {
		return studentLeaveDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return studentLeaveDao.count(map);
	}

	@Override
	public int save(StudentLeave studentLeave) {
        try {
            HashMap<String,Object> map = new HashMap<>();
			Double day = Double.parseDouble(studentLeave.getDays()+"."+studentLeave.getHours());
            map.put("days",day);
            map.put("userId",studentLeave.getCreateUser());
            String processInstanceId = actTaskService.startProcess("leaveprocess","staff_student_leave",studentLeave.getId(), "学员请假",map);
            studentLeave.setProcInsId(processInstanceId);
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
			task.getTaskLocalVariables();
			String taskName = task.getName();
			List<String> list = new ArrayList<String>();
			if("大队长".equals(taskName)){
				list = studentLeaveDao.getCaptainByClass("1",studentLeave.getClassId());//获取班级大队长
			}else if("班主任".contentEquals(taskName)){
				list = studentLeaveDao.getHeadMasterByClass("2",studentLeave.getClassId());
			}
			if(list.size() <= 0 || list.isEmpty()){
				list.add("1");//没有审核人指定给管理员
			}
			taskService.setAssignee(task.getId(),list.toString().replace("[","").replace("]",""));
            studentLeave.setStatus("0");
            studentLeave.setStudentId(studentLeave.getCreateUser());
            studentLeaveDao.save(studentLeave);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
	}

	public R update(StudentLeave studentLeave){
		try {
			actTaskService.complete(studentLeave.getTaskId(),studentLeave.getProcInsId(),studentLeave.getRemark(),"审批操作",new HashedMap());
			studentLeaveDao.update(studentLeave);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}

	public List<Map<String,Object>> getHisLeave(){
		List<Map<String,Object>> list = studentLeaveDao.getHisLeave(ShiroUtils.getUser().getUserId().toString());
		return list;
	}

	public void updateSickStatus(String status,String remark,String id){
		studentLeaveDao.updateSickStatus(status,remark,id);
	}
	@Override
	public int remove(String id) {
		return studentLeaveDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return studentLeaveDao.batchRemove(ids);
	}
}
