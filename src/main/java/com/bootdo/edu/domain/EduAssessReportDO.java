package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-26 09:03:31
 */
public class EduAssessReportDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//教师评价表
	private Integer id;
	//
	private String classId;
	//
	private String subjectId;
	//老师userID
	private String userId;
	//课程计划ID
	private String scheduleId;
	//考核类别
	private String assessType;
	//评价内容
	private String assessContent;
	//评价人(学生userID)
	private String assessUser;
	//评价时间
	private Date createTime;

	/**
	 * 设置：教师评价表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：教师评价表
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取：
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置：
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取：
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置：老师userID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：老师userID
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：课程计划ID
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * 获取：课程计划ID
	 */
	public String getScheduleId() {
		return scheduleId;
	}
	/**
	 * 设置：考核类别
	 */
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	/**
	 * 获取：考核类别
	 */
	public String getAssessType() {
		return assessType;
	}
	/**
	 * 设置：评价内容
	 */
	public void setAssessContent(String assessContent) {
		this.assessContent = assessContent;
	}
	/**
	 * 获取：评价内容
	 */
	public String getAssessContent() {
		return assessContent;
	}
	/**
	 * 设置：评价人(学生userID)
	 */
	public void setAssessUser(String assessUser) {
		this.assessUser = assessUser;
	}
	/**
	 * 获取：评价人(学生userID)
	 */
	public String getAssessUser() {
		return assessUser;
	}
	/**
	 * 设置：评价时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：评价时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
