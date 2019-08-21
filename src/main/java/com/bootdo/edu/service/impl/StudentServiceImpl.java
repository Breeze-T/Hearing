package com.bootdo.edu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootdo.common.utils.ExcelOpen;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.StringUtil;
import com.bootdo.edu.dao.EduClassDao;
import com.bootdo.edu.dao.StudentDao;
import com.bootdo.edu.domain.StudentDO;
import com.bootdo.edu.service.StudentService;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.dao.UserRoleDao;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.domain.UserRoleDO;
@Service
public class StudentServiceImpl implements StudentService{
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private EduClassDao eduClassDao;
	@Autowired
    UserRoleDao userRoleMapper;

	@Override
	@Transactional
	public int save(StudentDO studentDo,Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int count = 0;
		//插用户表
		UserDO user = new UserDO();
		user.setName(studentDo.getStudentName());
		user.setUsername(studentDo.getAlarmNum());//警号作为默认账号
		user.setPassword(MD5Utils.encrypt(user.getUsername(), "000000"));
		//user.setDeptId(19L);
		user.setEmail(studentDo.getEmail());
		user.setMobile(studentDo.getPhoneNum());
		user.setGmtCreate(new Date());
		if(studentDo.getSex().equals("M")) {
			user.setSex(96L);
		}else if(studentDo.getSex().equals("F")) {
			user.setSex(97L);
		}
		user.setUserIdCreate(userId);
		user.setStatus(1);
		String birth = getBirAgeSex(studentDo.getCardNum()).get("birthday");
		try {
			user.setBirth(sdf.parse(birth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		count = userDao.save(user);
		//赋予权限
		//查询学生角色id(如果没有则不插)
		RoleDO userRole = userRoleMapper.getByRoleSign("student");
		if(userRole != null &&count>0) {
			 UserRoleDO ur = new UserRoleDO();
			 ur.setUserId(user.getUserId());
	         ur.setRoleId(userRole.getRoleId());
	         count=userRoleMapper.save(ur);
		}
		//插入学生表
		if(count>0) {
			studentDo.setStatus("1");
			studentDo.setUserId(user.getUserId().toString());
			count = studentDao.save(studentDo);
		}
		return count;
	}
	public void studentSave(StudentDO studentDo) {
		studentDao.save(studentDo);
	}

	@Override
	@Transactional
	public int remove(Long id,Long userId,String status) {
		int result = 0;
		if(status.equals("1")) {
			//删除学生的同时还需删除用户
			result = userDao.remove(userId);
			if(result>0) {
				result = studentDao.remove(id);
			}
		}else{
			result = studentDao.remove(id);
		}
		return result; 
	}

	@Override
	@Transactional
	public int update(StudentDO studentDo) {
		//int result = 0;
		//修改学生的同时修改用户账号（目前仅需修改邮箱） 邮箱也不需要填写吧
	/*	UserDO user = new UserDO();
		user.setUserId(Long.parseLong(studentDo.getUserId()));
		user.setEmail(studentDo.getEmail());
		userDao.update(user);*/
		int result =studentDao.update(studentDo);
		return result; 
	}

	@Override
	public List<StudentDO> list(Map<String, Object> map) {
		return studentDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return studentDao.count(map);
	}

	@Override
	public StudentDO get(Long id) {
		return studentDao.get(id);
	}

	@Override
	@Transactional
	public int batchRemove(Long[] ids, String[] userIds) {
		int result = 0;
		if(userIds.length >0) {
			Long[] uIds = new Long[userIds.length];
			for (int i=0;i<userIds.length;i++) {
				uIds[i]=Long.parseLong(userIds[i]);
			}
			result = userDao.batchRemove(uIds);
			if(result>0) {
				result = studentDao.batchRemove(ids);
			}
		}else {
			result = studentDao.batchRemove(ids);
		}
		return result;
	}

	@Override
	@Transactional
	public int pass(Long id,Long userId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		//插用户表
		StudentDO studentDo = studentDao.get(id);
		UserDO user = new UserDO();
		user.setName(studentDo.getStudentName());
		user.setUsername(studentDo.getAlarmNum());//警号作为默认账号
		user.setPassword(MD5Utils.encrypt(user.getUsername(), "000000"));
		//user.setDeptId(19L);
		user.setEmail(studentDo.getEmail());
		user.setMobile(studentDo.getPhoneNum());
		user.setGmtCreate(new Date());
		if(studentDo.getSex().equals("M")) {
			user.setSex(96L);
		}else if(studentDo.getSex().equals("F")) {
			user.setSex(97L);
		}
		user.setUserIdCreate(userId);
		user.setStatus(1);
		try {
			String birth = getBirAgeSex(studentDo.getCardNum()).get("birthday");
			user.setBirth(sdf.parse(birth));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result = userDao.save(user);
		//赋予权限
		//查询学生角色id(如果没有则不插)
		RoleDO userRole = userRoleMapper.getByRoleSign("student");
		if(userRole != null&&result>0) {
			  UserRoleDO ur = new UserRoleDO();
			  ur.setUserId(user.getUserId());
              ur.setRoleId(userRole.getRoleId());
              result=userRoleMapper.save(ur);
		}
		//修改学生表状态
		if(result>0) {
			StudentDO student =new StudentDO();
			student.setId(id);
			student.setStatus("1");
			student.setUserId(user.getUserId().toString());
			studentDao.update(student);
		}
		return result;
	}

	@Override
	public int refuse(Long id) {
		StudentDO student =new StudentDO();
		student.setId(id);
		student.setStatus("2");
		return studentDao.update(student);
	}

	@Override
	@Transactional
	public int batchPass(Long[] ids,Long userId) {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		List<StudentDO> stuList = studentDao.getList(ids);
		List<UserDO> userList = new ArrayList<UserDO>();
		List<UserRoleDO> userRoleList = new ArrayList<UserRoleDO>();
		for (StudentDO studentDO : stuList) {
			UserDO user = new UserDO();
			user.setName(studentDO.getStudentName());
			user.setUsername(studentDO.getAlarmNum());//警号作为默认账号
			user.setPassword(MD5Utils.encrypt(user.getUsername(), "000000"));
			user.setEmail(studentDO.getEmail());
			user.setMobile(studentDO.getPhoneNum());
			user.setGmtCreate(date);
			if(studentDO.getSex().equals("M")) {
				user.setSex(96L);
			}else if(studentDO.getSex().equals("F")) {
				user.setSex(97L);
			}
			user.setUserIdCreate(userId);
			user.setStatus(1);
			try {
				String birth = getBirAgeSex(studentDO.getCardNum()).get("birthday");
				user.setBirth(sdf.parse(birth));
			} catch (ParseException e) {
				e.printStackTrace();
				return -1;
			}
			user.setStatus(1);
			userList.add(user);
		}
		result = userDao.batchInsert(userList);//批量插入用户
		//将返回的主键赋值给学生
		//查询角色为学生的roleid(没有则不插)
		RoleDO role = userRoleMapper.getByRoleSign("student");
		if(result>0) {
			for (int i=0;i<stuList.size();i++) {
				UserRoleDO userRole = new UserRoleDO();
				userRole.setRoleId(role.getRoleId());
				userRole.setUserId(userList.get(i).getUserId());
				userRoleList.add(userRole);
				stuList.get(i).setUserId(userList.get(i).getUserId().toString());
				stuList.get(i).setStatus("1");
				result = studentDao.update(stuList.get(i));
			}
		}
		if(null !=role && userRoleList.size()>0 && result>0) {
			result = userRoleMapper.batchSave(userRoleList);
		}
		return result;
	}

	@Override
	public int batchRefuse(Long[] ids) {
		return studentDao.batchRefuse(ids);
	}

	@Override
	public R batchImport(ExcelOpen readExcelx) throws Exception {
		Date createDate = new Date();
		List<StudentDO> studentList = new ArrayList<StudentDO>();
		List<UserDO> userList = new ArrayList<UserDO>();
		Map<String,String> classMap = exchangeResult(eduClassDao.getAllClass(null));
		
		// 总行数
		int count = getCount(readExcelx);
		if(count >= 1){
			Object[] rows;
			for(int i=1;i<=count;i++) {
				rows = readExcelx.readExcelLine(i);
				int checkResult = checkRows(rows);
				if(checkResult == 1) {
					return R.error("第"+(i)+"行有必填项为空！");
				}
				if(checkResult == 2) {
					return R.error("第"+(i)+"行身份证号格式不正确！");
				}
				if(checkResult == 3) {
					return R.error("第"+(i)+"行手机号格式不正确！");
				}
				if(checkResult == 4) {
					return R.error("第"+(i)+"行邮箱格式不正确！");
				}
				if(checkOnly(rows[7].toString(),studentList)==2) {
					return R.error("导入的数据第"+(i)+"行警号重复！");
				}
				if(checkOnly(rows[7].toString(),studentList)==3) {
					return R.error("第"+(i)+"行警号数据库中已存在！");
				}
				StudentDO student = new StudentDO();
				student.setStudentName(rows[0].toString());
				if(rows[1].toString().equals("男")) {
					student.setSex("M");
				}else {
					student.setSex("F");
				}
				student.setCardNum(rows[2].toString());
				student.setPhoneNum(rows[3].toString());
				student.setEmail(rows[4].toString());
				student.setUnit(rows[5].toString());
				student.setDuties(rows[6].toString());
				student.setAlarmNum(rows[7].toString());
				student.setClassId(classMap.get(rows[8]));
				student.setStatus("1");
				UserDO user = new UserDO();
				user.setGmtCreate(createDate);
				user.setName(student.getStudentName());
				user.setUsername(student.getAlarmNum());//警号作为默认账号
				user.setPassword(MD5Utils.encrypt(user.getUsername(), "000000"));
				user.setEmail(student.getEmail());
				user.setStatus(1);
				studentList.add(student);
				userList.add(user);
			}
			try {
				//往数据库插入
				if(insertUserAndStu(userList,studentList)>0) {
					return R.error("导入成功！");
				}else {
					return R.error("导入失败！");
				}
				}catch(Exception e) {
					e.printStackTrace();
					return R.error("导入失败！");
			}
		}else {
			return R.error("导入的文件没有数据！");
		}
	}
	
	
	
	//获得真实的输入行数
	public int getCount(ExcelOpen readExcelx) {
		int count = 0;
		Workbook workBook = readExcelx.getWb();
		Sheet sheet = workBook.getSheetAt(0);
		for(int i=1;i<readExcelx.getRowCount();i++) {
			Row row = sheet.getRow(i);
			if(StringUtil.isEmpty(row.getCell(0).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(1).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(2).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(3).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(4).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(5).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(6).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(7).getStringCellValue())&&
			   StringUtil.isEmpty(row.getCell(8).getStringCellValue())
			   ) {
				count=(i-1);
				break;
			}
		}
		return count;
	}
	//0:正常1：有空值2：身份证号格式错误3：手机号格式错误4：邮箱格式错误
	public int checkRows(Object[] rows) {
		 int tag = 0;
		 if((null == rows[0]||StringUtil.isEmpty(rows[0]))||(null == rows[1]||StringUtil.isEmpty(rows[1]))||(null == rows[3]||StringUtil.isEmpty(rows[3]))||(null == rows[5]||StringUtil.isEmpty(rows[5]))||(null == rows[6]||StringUtil.isEmpty(rows[6]))||(null == rows[7]||StringUtil.isEmpty(rows[7]))||(null == rows[8]||StringUtil.isEmpty(rows[8]))) {
			 tag=1;
			 return tag;
		 }
		 if(!isIDNumber(rows[2].toString())) {
			 tag=2;
			 return tag;
		 }
		 if(!isPhone(rows[3].toString())) {
			 tag=3;
			 return tag;
		 }
		 if(null != rows[4]&&StringUtil.isNotEmpty(rows[4])) {
			 if(!rows[4].toString().matches("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)+")) {
				 tag=4;
				 return tag; 
			 }
		 }
		 return tag;
	}
	
	public boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
           return true;  //不做必填校验
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾


        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + 
                                "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("身份证号异常:" + IDNumber);
                    return false;
                }
            }

        }
        return matches;
    }
	
	
	
	public boolean isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	        //System.out.println("手机号应为11位数");
	        return false;
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        if (!isMatch) {
	        	return false;
	        	//System.out.println("手机号格式错误");
	        }
	        return true;
	    }
	}
	
	
	//将查询结果的两列转换为键值对
	public Map<String,String> exchangeResult(List<Map<String,Object>> list){
		 Map<String, String> resultMap = new HashMap<String, String>();
		 for (Map<String, Object> map:list){
			 resultMap.put(map.get("resultKey").toString(), map.get("resultValue").toString());
		 }
		return resultMap;
	}
	
	@Transactional
	public int insertUserAndStu(List<UserDO> userList,List<StudentDO> studentList) throws Exception{
		int result=0;
		result = userDao.batchInsert(userList);//插入用户后自动获得主键
		List<UserRoleDO> userRoleList = new ArrayList<UserRoleDO>();
		RoleDO role = userRoleMapper.getByRoleSign("student");
		if(null != role) {
			for (int i=0;i<userList.size();i++) {
				UserRoleDO userRole = new UserRoleDO();
				userRole.setRoleId(role.getRoleId());
				userRole.setUserId(userList.get(i).getUserId());
				userRoleList.add(userRole);
			}
		}
		result = userRoleMapper.batchSave(userRoleList);
		if(result>0) {
			for (int i=0;i<studentList.size();i++) {
				studentList.get(i).setUserId(userList.get(i).getUserId().toString());
			}
			result=studentDao.batchInsert(studentList);
		}
		return result;
	}
	
	//从身份证号码截取生日 性别 和年龄（一代身份证和二代身份证皆可使用）
	 public Map<String, String> getBirAgeSex(String certificateNo) {
	        String birthday = "";
	        String age = "";
	        String sexCode = "";
	        int year = Calendar.getInstance().get(Calendar.YEAR);
	        char[] number = certificateNo.toCharArray();
	        boolean flag = true;
	        if (number.length == 15) {
	            for (int x = 0; x < number.length; x++) {
	                if (!flag) return new HashMap<String, String>();
	                flag = Character.isDigit(number[x]);
	            }
	        } else if (number.length == 18) {
	            for (int x = 0; x < number.length - 1; x++) {
	                if (!flag) return new HashMap<String, String>();
	                flag = Character.isDigit(number[x]);
	            }
	        }
	        if (flag && certificateNo.length() == 15) {
	            birthday = "19" + certificateNo.substring(6, 8) + "-"
	                    + certificateNo.substring(8, 10) + "-"
	                    + certificateNo.substring(10, 12);
	            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
	            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
	        } else if (flag && certificateNo.length() == 18) {
	            birthday = certificateNo.substring(6, 10) + "-"
	                    + certificateNo.substring(10, 12) + "-"
	                    + certificateNo.substring(12, 14);
	            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
	            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
	        }
	        Map<String, String> map = new HashMap<String, String>();
	        map.put("birthday", birthday);
	        map.put("age", age);
	        map.put("sexCode", sexCode);
	        return map;
	 }
	 //学员统计（根据性别统计学生数量）
	@Override
	public List<Map<String, Object>> getStudentBySex() {
		return studentDao.getStudentBySex();
	}

	@Override
	public List<Map<String, Object>> getStudentByAge(List<Map<String, String>> list) {
		return studentDao.getStudentByAge(list);
	}
	
	//学生档案详情
	@Override
	public StudentDO profileDetail(Long studentId) {
		return studentDao.profileDetail(studentId);
	}
	
	
	//检查警号是否唯一 返回1正常  返回2 导入的数据有重复警号  返回3 该条数据警号数据库中已存在
	public int checkOnly(String alarmNum,List<StudentDO> stuList) {
		for (StudentDO studentDO : stuList) {
			if(alarmNum.equals(studentDO.getAlarmNum())) {
				return 2;
			}
		}
		if(studentDao.checkOnly(alarmNum)>=1) {
			return 3;
		}
		return 1;
	}
	@Override
	public List<StudentDO> profileList(Map<String, Object> map) {
		return studentDao.profileList(map);
	}
}
