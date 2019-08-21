package com.bootdo.edu.domain;

import java.util.HashMap;
import java.util.Map;

public class StudentDO {
	private Long id;
	//姓名
	private String studentName;
	//性别
	private String sex;
	//身份证号
	private String cardNum;
	//手机号
	private String phoneNum;
	//邮箱
	private String email;
	//单位
	private String unit;
	//职务
	private String duties;
	//警号
	private String alarmNum;
	//班级
	private String classId;
	//照片
	private String photo;
	//状态
	private String status;
	//userid
	private String userId;
	//类型
	private String type;
	//父类id
	private String pId;
	//排序号，区分第几中队，第几组
	private String number;
	private String className;
	private String score;

	//扩展属性集合
    private final Map<String,Object> map=new HashMap<String, Object>();
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public String getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(String alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Map<String, Object> getMap() {
		return map;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
}
