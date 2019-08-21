package com.bootdo.app.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface AssessDao {

    List<Map<String,Object>> list(@Param("userId") String userId,@Param("classId")String classId,@Param("date")String date);

    @Insert("insert into edu_assess(class_id,subject_id,user_id,schedule_id,assess_type,assess_content,assess_user,create_time) " +
            "values(#{classId},#{subjectId},#{userId},#{scheduleId},#{assessType},#{assessContent},#{assessUser},now())")
    public void insertAssess(@Param("classId")String classId,@Param("subjectId")String subjectId,@Param("userId")String userId,@Param("scheduleId")String scheduleId,
                             @Param("assessType")String assessType,@Param("assessContent")String assessContent,@Param("assessUser")String assessUser);

    public List<Map<String,Object>> getAssessStatus(@Param("classId")String classId,@Param("date")String date);

    public List<Map<String,Object>> getAssessStatusDetail(@Param("assessUser")String assessUser,@Param("date")String date);

    public List<Map<String,Object>> getNoAssessStatusDetail(@Param("classId")String classId,@Param("assessUser")String assessUser,@Param("date")String date);

    @Insert("insert into message_remind(linked_id,remind_user,create_user,create_time) values(#{scheduleId},#{remindUser},#{userId},now())")
    public void insertMessageRemind(@Param("scheduleId")String scheduleId,@Param("remindUser")String remindUser,@Param("userId")String userId);

    @Select("SELECT a.assess_content assessContent,b.`name` assessName,c.student_name studentName,c.photo,c.sex " +
            "from edu_assess a left join sys_dict b on a.assess_type=b.id " +
            "right join edu_student c on a.assess_user=c.user_id and a.subject_id=#{subjectId} and a.class_id=c.class_id " +
            "and a.user_id=#{userId} and a.schedule_id=#{scheduleId} where c.class_id=#{classId} order by a.id desc")
    public List<Map<String,Object>> getAssessDetail(@Param("userId")String userId,@Param("classId")String classId,
                                                    @Param("subjectId")String subjectId,@Param("scheduleId")String scheduleId);

    public List<Map<String,Object>> getRemindAssess(@Param("userId")String userId);

    @Update("update message_remind set status=1 where id = #{id}")
    public void updateRemindAssess(@Param("id")String id);
}
