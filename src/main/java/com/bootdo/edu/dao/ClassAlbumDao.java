package com.bootdo.edu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bootdo.edu.domain.ClassAlbumDO;
import com.bootdo.edu.domain.ClassAlbumDetailDO;


public interface ClassAlbumDao {
	public List<ClassAlbumDO> list(@Param("classId")String classId);
	
	public ClassAlbumDetailDO selectByAlbumId(String albumId);
	
	public int save(ClassAlbumDO classAlbumDo);
	
	
	public int update(ClassAlbumDO classAlbumDo);
}
