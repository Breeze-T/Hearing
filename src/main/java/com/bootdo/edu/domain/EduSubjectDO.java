package com.bootdo.edu.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author lvbin
 * @email 888888@163.com
 * @date 2018-12-06 17:59:49
 */
public class EduSubjectDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//科目表
	private Integer id;
	//科目名称
	private String subjectName;
	//创建人
	private String createUser;
	//创建时间
	private Date createTime;
	//备注
	private String remark;
	//1是启用状态，2是停用
	private String state;
	//是否需要考试，1是需要，0不需要
	private String examFlag;
	//科目成绩权重占比
	private Double rate;
	/**
	 * 设置：科目表
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：科目表
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：科目名称
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取：科目名称
	 */
	public String getSubjectName() {
		return subjectName;
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
	 * 设置：1是启用状态，2是停用
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取：1是启用状态，2是停用
	 */
	public String getState() {
		return state;
	}

	public String getExamFlag() {
		return examFlag;
	}

	public void setExamFlag(String examFlag) {
		this.examFlag = examFlag;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
}
