package com.bootdo.edu.domain;

public class SeatManage {
	
	private String id;
	private String className;
	private String classId;
	private String classCode;
	private String gradeId;
	private String gradeName;
	private String schoolId;
	private String delFlag;
	private String createUser;
	private String createTime;
	
	public SeatManage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SeatManage(String id, String className, String classCode, String gradeId,
                      String schoolId, String delFlag,
                      String createUser, String createTime, String classId) {
		super();
		this.id = id;
		this.className = className;
		this.classCode = classCode;
		this.gradeId = gradeId;
		this.schoolId = schoolId;
		this.delFlag = delFlag;
		this.createUser = createUser;
		this.createTime = createTime;
		this.classId=classId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}
