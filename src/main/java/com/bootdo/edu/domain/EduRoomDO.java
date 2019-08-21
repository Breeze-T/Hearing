package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-10 10:52:54
 */
public class EduRoomDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//房间表
	private Integer id;
	//房间名称
	private String roomName;
	//房间位置
	private String roomPlace;
	//房间编号
	private String roomCode;
	//房间用途1教室，2是训练场地
	private String roomType;
	//
	private String subjectId;
	//
	private String subjectName;
	//房间设备
	private String roomFacility;
	//备注
	private String remark;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;
	//1是可以用,2是停用
	private String state;

	/**
	 * 设置：房间表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：房间表
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：房间名称
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	/**
	 * 获取：房间名称
	 */
	public String getRoomName() {
		return roomName;
	}
	/**
	 * 设置：房间位置
	 */
	public void setRoomPlace(String roomPlace) {
		this.roomPlace = roomPlace;
	}
	/**
	 * 获取：房间位置
	 */
	public String getRoomPlace() {
		return roomPlace;
	}
	/**
	 * 设置：房间编号
	 */
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	/**
	 * 获取：房间编号
	 */
	public String getRoomCode() {
		return roomCode;
	}
	/**
	 * 设置：房间用途1教室，2是训练场地
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	/**
	 * 获取：房间用途1教室，2是训练场地
	 */
	public String getRoomType() {
		return roomType;
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
	 * 设置：
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取：
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置：房间设备
	 */
	public void setRoomFacility(String roomFacility) {
		this.roomFacility = roomFacility;
	}
	/**
	 * 获取：房间设备
	 */
	public String getRoomFacility() {
		return roomFacility;
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
	 * 设置：1是可以用,2是停用
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：1是可以用,2是停用
	 */
	public String getState() {
		return state;
	}
}
