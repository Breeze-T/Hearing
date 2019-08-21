package com.bootdo.edu.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {

	public String uploadFile(MultipartFile file)throws Exception;
	public String upLoadFileList(List<MultipartFile> resourceList)throws Exception;

	

	public void delete(String photoUrl)throws Exception;
	

	
}
