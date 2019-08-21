package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-11 09:30:01
 */
public class EduScheduleDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//课表
	private Integer id;
	//班级
	private String classId;
	//科目id
	private String subjectId;
	//教师的userId
	private String positionUser;
	//锁定标识；0：未锁定，1：已锁定
	private String locking;
	//周几
	private String week;
	//课程的日期到天
	private Date date;
	//课次第几节，一天四节课,1早晨，2上午，3下午，4晚上
	private String section;
	//教室id
	private Integer roomId;

	/**
	 * 设置：课表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：课表
	 */
	public Integer getId() {
		return id;
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
	 * 设置：科目id
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取：科目id
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置：教师的userId
	 */
	public void setPositionUser(String positionUser) {
		this.positionUser = positionUser;
	}
	/**
	 * 获取：教师的userId
	 */
	public String getPositionUser() {
		return positionUser;
	}
	/**
	 * 设置：锁定标识；0：未锁定，1：已锁定
	 */
	public void setLocking(String locking) {
		this.locking = locking;
	}
	/**
	 * 获取：锁定标识；0：未锁定，1：已锁定
	 */
	public String getLocking() {
		return locking;
	}
	/**
	 * 设置：周几
	 */
	public void setWeek(String week) {
		this.week = week;
	}
	/**
	 * 获取：周几
	 */
	public String getWeek() {
		return week;
	}
	/**
	 * 设置：课程的日期到天
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 获取：课程的日期到天
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * 设置：课次第几节，一天四节课,1早晨，2上午，3下午，4晚上
	 */
	public void setSection(String section) {
		this.section = section;
	}
	/**
	 * 获取：课次第几节，一天四节课,1早晨，2上午，3下午，4晚上
	 */
	public String getSection() {
		return section;
	}
	/**
	 * 设置：教室id
	 */
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}
	/**
	 * 获取：教室id
	 */
	public Integer getRoomId() {
		return roomId;
	}
}
