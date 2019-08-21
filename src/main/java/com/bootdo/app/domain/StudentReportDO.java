package com.bootdo.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class StudentReportDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pId;
	private String photo;
	private String sex;
	private String phone;
	private String userName;
	private String userId;
	private String positionName;
	private String positionType;
	private String policeNumber;
	private List<StudentReportDO> childrens;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public List<StudentReportDO> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<StudentReportDO> childrens) {
		this.childrens = childrens;
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
}
