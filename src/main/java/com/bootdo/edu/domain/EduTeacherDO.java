package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-04 10:41:01
 */
public class EduTeacherDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//教官表
	private String id;
	//用户id
	private String userId;
	//姓名
	private String name;
	//手机号
	private String phone;
	//警号
	private String policeNumber;
	//性别
	private String sex;
	//身份证
	private String idCard;
	//职级
	private String postGrade;
	//职务
	private String post;
	//单位
	private String company;
	//教官类型
	private String teacherType;
	// 部门
	private Long deptId;
	private String deptName;
	private String state;
	private Double score;
	private String workLoad;
	/**
	 * 设置：教官表
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：教官表
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：警号
	 */
	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
	/**
	 * 获取：警号
	 */
	public String getPoliceNumber() {
		return policeNumber;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * 设置：身份证
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 获取：身份证
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * 设置：职级
	 */
	public void setPostGrade(String postGrade) {
		this.postGrade = postGrade;
	}
	/**
	 * 获取：职级
	 */
	public String getPostGrade() {
		return postGrade;
	}
	/**
	 * 设置：职务
	 */
	public void setPost(String post) {
		this.post = post;
	}
	/**
	 * 获取：职务
	 */
	public String getPost() {
		return post;
	}
	/**
	 * 设置：单位
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 获取：单位
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * 设置：教官类型
	 */
	public void setTeacherType(String teacherType) {
		this.teacherType = teacherType;
	}
	/**
	 * 获取：教官类型
	 */
	public String getTeacherType() {
		return teacherType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getWorkLoad() {
		return workLoad;
	}

	public void setWorkLoad(String workLoad) {
		this.workLoad = workLoad;
	}
}
