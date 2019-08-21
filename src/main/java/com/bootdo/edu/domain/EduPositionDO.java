package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-03 15:48:36
 */
public class EduPositionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//班级-职位表
	private Long id;
	//职位id,字典表
	private String positionId;
	//科目ID字典表
	private String subjectId;
	//班级表
	private String classId;
	//职位人userid
	private String positionUser;
	//岗位code
	private String positionType;
	//职位类型0是其他岗位,1是教官,2班主任
	private String positionCode;
	//职位名称
	private String positionName;
	//
	private Date createTime;

	/**
	 * 设置：班级-职位表
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：班级-职位表
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：职位id,字典表
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	/**
	 * 获取：职位id,字典表
	 */
	public String getPositionId() {
		return positionId;
	}
	/**
	 * 设置：科目ID字典表
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取：科目ID字典表
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置：班级表
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取：班级表
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置：职位人userid
	 */
	public void setPositionUser(String positionUser) {
		this.positionUser = positionUser;
	}
	/**
	 * 获取：职位人userid
	 */
	public String getPositionUser() {
		return positionUser;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
