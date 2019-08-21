package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-19 08:44:48
 */
public class EduDormitoryStudentDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//学生的userId
	private String userId;
	//宿舍id
	private String dormitoryId;
	//班级
	private String classId;
	//编号
	private String number;
	//学生id
	private String studentId;
	//默认1有效，0是删除
	private String state;
	//
	private String studentName;
	/**
	 * 设置：学生的userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：学生的userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：宿舍id
	 */
	public void setDormitoryId(String dormitoryId) {
		this.dormitoryId = dormitoryId;
	}
	/**
	 * 获取：宿舍id
	 */
	public String getDormitoryId() {
		return dormitoryId;
	}
	/**
	 * 设置：班级
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置：编号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：编号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：学生id
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：学生id
	 */
	public String getStudentId() {
		return studentId;
	}
	/**
	 * 设置：默认1有效，0是删除
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：默认1有效，0是删除
	 */
	public String getState() {
		return state;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}
