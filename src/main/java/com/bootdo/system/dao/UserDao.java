package com.bootdo.system.dao;

import com.bootdo.system.domain.UserDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserDao {

	UserDO get(Long userId);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();
	
	int batchInsert(List<UserDO> userList);

	@Select("SELECT DISTINCT a.* from (SELECT b.id,b.class_name className,'1' type from edu_student a left join edu_class b on a.class_id=b.id where a.user_id=#{userId} " +
			"union all " +
			"SELECT c.id,c.class_name className,'2' type from edu_position a left join edu_teacher b on a.position_user=b.user_id " +
			"left join edu_class c on a.class_id=c.id where a.position_user=#{userId} ) a ORDER BY a.id")
	List<Map<String,Object>> userClassList(@Param("userId")String userId);

	@Select("SELECT id,class_name className,'2' type from edu_class ORDER BY create_time desc")
	List<Map<String,Object>> classAllList();

	@Select("SELECT c.role_id roleId,c.role_sign roleSign,c.role_name roleName from sys_user_role a left join sys_user b on a.user_id=b.user_id " +
			"left join sys_role c on a.role_id=c.role_id " +
			"where a.user_id=#{userId}")
	List<Map<String,Object>> userRoleList(@Param("userId")String userId);

}
