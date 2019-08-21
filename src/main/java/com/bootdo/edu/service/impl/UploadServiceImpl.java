package com.bootdo.edu.service.impl;

import com.bootdo.common.utils.SFTPUtil;
import com.bootdo.edu.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {


	@Value("${sftpUserName}")
    private String sftpUserName;
    @Value("${sftpPassword}")
    private String sftpPassword;
    @Value("${sftpHost}")
    private String sftpHost;
    @Value("${sftpPort}")
    private Integer sftpPort;
    @Value("${serverUrl}")
    private String serverUrl;
    @Value("${uploadUrl}")
    private String uploadUrl;
    @Value("${uploadFileUrl}")
    private String uploadFileUrl;

	public String uploadFile(MultipartFile file) {
		String url = "";
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
		try {
			String filename = file.getOriginalFilename();
			String prefix = filename.substring(filename.lastIndexOf(".") + 1);
			String fileName = ((UUID.randomUUID().toString()).replace("-", ""))+prefix;
			sftpUtil.upload(uploadUrl + uploadFileUrl, fileName, file.getInputStream());
			url = serverUrl + uploadUrl + uploadFileUrl + fileName;
			sftpUtil.logout();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return url;
	}
	public String upLoadFileList(List<MultipartFile> resourceList) {
		String fileName = "";
		String sftpFileName = "";
		String urls="";
		try {
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
			for(MultipartFile resource:resourceList) {
				fileName = resource.getOriginalFilename();
				sftpFileName = (String) fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
				sftpFileName = ((UUID.randomUUID().toString()).replace("-", ""))+sftpFileName;
				sftpUtil.upload(uploadUrl + uploadFileUrl, sftpFileName, resource.getInputStream());
				String url= serverUrl+uploadFileUrl + sftpFileName;
				urls+=url+",";
			}
			urls=urls.substring(0,urls.length()-1);
			sftpUtil.logout();
		}catch (Exception e) {
			e.printStackTrace();
		}

		return urls;
	}

	
	@Transactional
	public void delete(String photoUrl)throws Exception{
		//删除ftp上的图片
		String fileName = photoUrl.substring(photoUrl.lastIndexOf("/")+1);
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
		sftpUtil.delete(uploadUrl + uploadFileUrl, fileName);
		sftpUtil.logout();
	}

}
