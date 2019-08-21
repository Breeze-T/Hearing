package com.bootdo.app.dao;

import com.bootdo.edu.domain.ClassAlbumDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Mapper
public interface ClassPhotoDao {

    List<ClassAlbumDO> list(@Param("classId") String classId);

}
