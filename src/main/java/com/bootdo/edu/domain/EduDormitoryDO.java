package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2018-12-17 09:05:21
 */
public class EduDormitoryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//宿舍表
	private Integer id;
	//宿舍名称
	private String roomName;
	//宿舍位置
	private String roomPlace;
	//宿舍编号
	private String roomCode;
	//宿舍类型性别M：男F：女
	private String roomType;
	//宿舍设备
	private String roomFacility;
	//备注
	private String remark;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;
	//1是可以用,2是停用
	private String state;
	//还可以容纳多少个人
	private Integer liveAmount;
	//现在已住多少个人
	private Integer useCount;

	/**
	 * 设置：宿舍表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：宿舍表
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：宿舍名称
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	/**
	 * 获取：宿舍名称
	 */
	public String getRoomName() {
		return roomName;
	}
	/**
	 * 设置：宿舍位置
	 */
	public void setRoomPlace(String roomPlace) {
		this.roomPlace = roomPlace;
	}
	/**
	 * 获取：宿舍位置
	 */
	public String getRoomPlace() {
		return roomPlace;
	}
	/**
	 * 设置：宿舍编号
	 */
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}
	/**
	 * 获取：宿舍编号
	 */
	public String getRoomCode() {
		return roomCode;
	}
	/**
	 * 设置：宿舍类型性别M：男F：女
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	/**
	 * 获取：宿舍类型性别M：男F：女
	 */
	public String getRoomType() {
		return roomType;
	}
	/**
	 * 设置：宿舍设备
	 */
	public void setRoomFacility(String roomFacility) {
		this.roomFacility = roomFacility;
	}
	/**
	 * 获取：宿舍设备
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
	/**
	 * 设置：还可以容纳多少个人
	 */
	public void setLiveAmount(Integer liveAmount) {
		this.liveAmount = liveAmount;
	}
	/**
	 * 获取：还可以容纳多少个人
	 */
	public Integer getLiveAmount() {
		return liveAmount;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
}
