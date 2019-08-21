package com.bootdo.activiti.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class StudentLeave extends  TaskDO implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	//编号
	private String id;
	//流程实例ID
	private String procInsId;
	private String classId;
	//天数
	private Integer days;
	private Integer hours;
	//开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String startTime;
	//结束时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String endTime;
	//请假理由
	private String reason;
	//审核意见
	private String remark;
	//审核状态（0:待审核，1:同意，2:不同意）
	private String status;
	//销假状态
	private String sickStatus;
	//销假审核意见
	private String sickRemark;
	//请假类型，从数据字典表中获取
	private String leaveType;
	//学生id
	private String studentId;

	private String studentName;
	//创建时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	private String createUser;

	private String auditor;//审核人
	//更新者
	private String updateUser;
	//更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSickStatus() {
		return sickStatus;
	}

	public void setSickStatus(String sickStatus) {
		this.sickStatus = sickStatus;
	}

	public String getSickRemark() {
		return sickRemark;
	}

	public void setSickRemark(String sickRemark) {
		this.sickRemark = sickRemark;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "StudentLeave{" +
				"id='" + id + '\'' +
				", procInsId='" + procInsId + '\'' +
				", days=" + days +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", reason='" + reason + '\'' +
				", remark='" + remark + '\'' +
				", status='" + status + '\'' +
				", sickStatus='" + sickStatus + '\'' +
				", sickRemark='" + sickRemark + '\'' +
				", leaveType='" + leaveType + '\'' +
				", studentId='" + studentId + '\'' +
				", createDate=" + createDate +
				", createUser='" + createUser + '\'' +
				", updateUser='" + updateUser + '\'' +
				", updateDate=" + updateDate +
				'}';
	}
}
