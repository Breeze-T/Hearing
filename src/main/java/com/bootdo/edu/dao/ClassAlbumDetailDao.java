package com.bootdo.edu.dao;

import java.util.List;

import com.bootdo.edu.domain.ClassAlbumDetailDO;

public interface ClassAlbumDetailDao {
	
	public int batchInsert(List<ClassAlbumDetailDO> list);
	
	public ClassAlbumDetailDO get(Long id);
	
	public int remove(Long id);
}
