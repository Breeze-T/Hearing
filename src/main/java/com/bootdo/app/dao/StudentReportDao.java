package com.bootdo.app.dao;

import com.bootdo.app.domain.StudentReportDO;
import com.bootdo.edu.domain.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface StudentReportDao {

    List<StudentReportDO> list(@Param("classId")String classId, @Param("studentName")String studentName,@Param("id")String id);

    @Select("SELECT a.id,a.p_id pId,a.photo,a.student_name userName,a.user_id userId,case when a.type='1' then '大队长' when a.type='2' " +
            "then '中队长' when a.type='3' then '组长' else '组员' end positionName,CONCAT('student',a.type) positionType," +
            "case when a.sex='M' then '男' else '女' end sex,a.phone_num phone,a.alarm_num policeNumber" +
            " from edu_student a where a.p_id=#{id}")
    List<StudentReportDO> findStudent(String id);

    public List<Map<String,Object>> selectStudentByClass(@Param("classId") String classId,@Param("studentName")String studentName,@Param("scheduleId")Integer scheduleId);

    public List<Map<String,Object>> selectClassStudent(@Param("classId") String classId,@Param("studentName")String studentName);

    @Select("SELECT * from edu_class where del_flag= '1'")
    List<Map<String,Object>> getAllClass();

    List<Map<String,Object>> getUserInfo(@Param("userId")String userId);

    @Update("update edu_student set student_name=#{userName},sex=#{sex},card_num=#{idCard},phone_num=#{phoneNum}," +
            "unit=#{unit},alarm_num=#{policeNumber},photo=#{photo} where user_id=#{userId}")
    void updateStudent(Map<String,Object> params);

    @Update("update edu_teacher set `name`=#{userName},phone=#{phoneNum},police_number=#{policeNumber},sex=#{sex}," +
            "id_card=#{idCard},company=#{unit},photo=#{photo} where user_id=#{userId}")
    void updateTeacher(Map<String,Object> params);


    @Update("update sys_user set `name`=#{userName},mobile=#{phoneNum},sex=#{sex},birth=#{birth} where user_id=#{userId}")
    void updateSysUser(Map<String,Object> params);

    @Select("SELECT b.check_type checkType,SUM(b.score) score,c.photo,c.student_name from edu_check_detail a left join edu_check b on a.check_id=b.id " +
            "right join edu_student c on a.student_id=c.id " +
            "where c.user_id=#{userId} GROUP BY b.check_type")
    public List<Map<String,Object>> getcheckScore(@Param("userId")String userId);

    @Select("SELECT IFNULL(score,0) score,IFNULL(photo,''),`name` from edu_teacher where user_id=#{userId}")
    public List<Map<String,Object>> getTeacherScore(@Param("userId")String userId);

    @Select("SELECT '1' type,user_id userId from edu_teacher where user_id=#{userId} " +
            "union all " +
            "SELECT '2' type,user_id userId from edu_student where user_id=#{userId}")
    public List<Map<String,Object>> selectUserType(@Param("userId")String userId);

    @Select("SELECT * from edu_student where alarm_num=#{userCode}")
    public List<Map<String,Object>> selectStudent(@Param("userCode")String userCode);
}
