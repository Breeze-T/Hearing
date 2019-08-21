package com.bootdo.edu.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassAlbumDO {
	private Long id;
	private Long classId;
	private String albumName;
	private Date createTime;
	private Long createUser;
	private String createUserName;
	private Long updateUser;
	private Date updateTime;
	private String remark;
	private List<ClassAlbumDetailDO> albumDetailList;
	//扩展属性集合
    private final Map<String,Object> map=new HashMap<String, Object>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ClassAlbumDetailDO> getAlbumDetailList() {
		return albumDetailList;
	}
	public void setAlbumDetailList(List<ClassAlbumDetailDO> albumDetailList) {
		this.albumDetailList = albumDetailList;
	}
	public Map<String, Object> getMap() {
		return map;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
