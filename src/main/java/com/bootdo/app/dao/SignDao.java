package com.bootdo.app.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface SignDao {

    List<Map<String,Object>> list(@Param("userId") String userId, @Param("date") String date);

    @Insert("insert into edu_sign(user_id,sign_time,sign_date,longitude,latitude,sign_addr,remark) " +
            "values(#{userId},now(),DATE_FORMAT(NOW(),'%Y-%m-%d'),#{longitude},#{latitude},#{signAddr},#{remark})")
    public void insertSign(@Param("userId") String userId, @Param("longitude") String longitude, @Param("latitude") String latitude,
                             @Param("signAddr") String signAddr,
                             @Param("remark") String remark);

}
