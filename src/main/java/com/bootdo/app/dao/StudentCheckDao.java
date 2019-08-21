package com.bootdo.app.dao;

import com.bootdo.edu.domain.ClassAlbumDO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface StudentCheckDao {

    List<Map<String,Object>> list(@Param("checkType")String checkType,@Param("pId")String pId);

    @Select("insert into edu_check_detail(id,student_id,user_id,check_id,score,create_user,create_time,remark) " +
            "values(#{id},#{studentId},#{userId},#{checkId},#{score},#{createUser},now(),#{remark})")
    public void insertStudentDetail(@Param("id")String id,@Param("studentId")String studentId,@Param("userId")String userId,
                                    @Param("checkId")String checkId,@Param("score")String score,@Param("remark")String remark,@Param("createUser")String createUser);
    @Insert("insert into edu_student_clock(class_id,opt_user,schedule_id,opt_time,student_id,status,type,remark) values(#{classId},#{optUser},#{scheduleId},now()," +
            "#{studentId},#{status},#{type},#{remark})")
    public void insertStudentClock(@Param("classId")String classId,@Param("optUser")String optUser,@Param("scheduleId")String scheduleId,@Param("studentId")String studentId,
                                   @Param("status")String status,@Param("type")String type,@Param("remark")String remark);
    @Update("update edu_student_clock set status=#{status},remark=#{remark},update_date=now(),update_user=#{optUser} where id=#{clockId}")
    public void updateStudentClock(@Param("clockId")String clockId,@Param("status")String status,@Param("remark")String remark,@Param("optUser")String optUser);

    @Select("SELECT a.id from edu_schedule a left join sys_dict b on (a.section=b.`value`) where b.type='section' and " +
            "DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i') BETWEEN DATE_FORMAT(CONCAT(a.date,' ',left(b.description,5)),'%Y-%m-%d %H:%i')" +
            "and DATE_FORMAT(CONCAT(a.date,' ',right(b.description,5)),'%Y-%m-%d %H:%i') and a.class_id=#{classId}")
    public Integer selectSchedule(@Param("classId")String classId);

    @Insert("insert into edu_random_check(check_name,class_id,field_name,create_user,create_time) values(#{checkName},#{classId},#{fieldName},#{createUser},now())")
    public void insertRandomCheck(@Param("classId")String classId,@Param("fieldName")String fieldName,@Param("checkName")String checkName,@Param("createUser")String createUser);

    @Select("select id,check_name checkName,class_id classId,field_name fieldName,create_user createUser from edu_random_check where class_id=#{classId} ORDER BY create_time desc")
    public List<Map<String,Object>> selecteRandomCheck(@Param("classId")String classId);

    public List<Map<String,Object>> selectStudentByRandomCheck(@Param("classId")String classId,@Param("checkId")String checkId);

    @Insert("insert into edu_student_randomclock(check_id,class_id,opt_user,opt_time,student_id,status,remark) " +
            "values(#{checkId},#{classId},#{optUser},now(),#{studentId},#{status},#{remark})")
    public void insertStudentByRandomCheck(@Param("classId")String classId,@Param("checkId")String checkId,
                                           @Param("optUser")String optUser,@Param("studentId")String studentId,
                                           @Param("status")String status,@Param("remark")String remark);
}
