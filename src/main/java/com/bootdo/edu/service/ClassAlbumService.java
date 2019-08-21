package com.bootdo.edu.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bootdo.edu.domain.ClassAlbumDO;
import com.bootdo.edu.domain.ClassAlbumDetailDO;

public interface ClassAlbumService {
	public List<ClassAlbumDO> list(String classId);
	
	public void add(ClassAlbumDO classAlbum,MultipartHttpServletRequest request)throws Exception;
	
	
	public List<ClassAlbumDetailDO> editAdd(Long id,MultipartHttpServletRequest request)throws Exception;
	
	public void delete(Long detailId)throws Exception;
	
	public int update(ClassAlbumDO classAlbumDo);
	
	
}
