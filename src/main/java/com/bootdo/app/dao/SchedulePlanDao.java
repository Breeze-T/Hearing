package com.bootdo.app.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface SchedulePlanDao {

    @Select("SELECT a.date,d.`name` userName,e.`name` sectionName,e.description," +
            "b.room_name roomName,c.subject_name subjectName from edu_schedule a left join edu_room b on a.room_id=b.id " +
            "left join edu_subject c on a.subject_id=c.id " +
            "left join sys_user d on a.position_user=d.user_id " +
            "left join sys_dict e on (a.section=e.`value` and e.type='section') " +
            "where a.class_id=#{classId} ORDER BY a.date ,e.sort ")
    List<Map<String,Object>> list( @Param("classId") String classId);

}
