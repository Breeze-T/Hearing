package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2018-11-29 14:37:11
 */
public class EduClassDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//班级设置表
	private String id;
	//班级名称
	private String className;
	//哪一年的哪一届，如2018
	private String grade;
	//班主任id
	private String classAdviser;
	//大队长id
	private String highCaptain;
	//中队长id
	private String mediumCaptain;
	//组长id
	private String groupLeader;
	//删除标记
	private String delFlag;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;
	//创建人
	private String updateUser;
	//创建时间
	private Date updateTime;
	//备注
	private String remark;
	//课程开始时间
	private Date scheduleStart;
	//课程结束时间
	private Date scheduleEnd;
	/**
	 * 设置：班级设置表
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：班级设置表
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：班级名称
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取：班级名称
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置：哪一年的哪一届，如2018
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 获取：哪一年的哪一届，如2018
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：班主任id
	 */
	public void setClassAdviser(String classAdviser) {
		this.classAdviser = classAdviser;
	}
	/**
	 * 获取：班主任id
	 */
	public String getClassAdviser() {
		return classAdviser;
	}
	/**
	 * 设置：大队长id
	 */
	public void setHighCaptain(String highCaptain) {
		this.highCaptain = highCaptain;
	}
	/**
	 * 获取：大队长id
	 */
	public String getHighCaptain() {
		return highCaptain;
	}
	/**
	 * 设置：中队长id
	 */
	public void setMediumCaptain(String mediumCaptain) {
		this.mediumCaptain = mediumCaptain;
	}
	/**
	 * 获取：中队长id
	 */
	public String getMediumCaptain() {
		return mediumCaptain;
	}
	/**
	 * 设置：组长id
	 */
	public void setGroupLeader(String groupLeader) {
		this.groupLeader = groupLeader;
	}
	/**
	 * 获取：组长id
	 */
	public String getGroupLeader() {
		return groupLeader;
	}
	/**
	 * 设置：删除标记
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标记
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建人
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：创建人
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	/**
	 * 设置：创建时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	public Date getScheduleStart() {
		return scheduleStart;
	}

	public void setScheduleStart(Date scheduleStart) {
		this.scheduleStart = scheduleStart;
	}

	public Date getScheduleEnd() {
		return scheduleEnd;
	}

	public void setScheduleEnd(Date scheduleEnd) {
		this.scheduleEnd = scheduleEnd;
	}
}
