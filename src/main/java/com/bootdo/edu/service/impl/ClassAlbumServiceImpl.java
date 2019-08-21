package com.bootdo.edu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bootdo.common.utils.SFTPUtil;
import com.bootdo.edu.dao.ClassAlbumDao;
import com.bootdo.edu.dao.ClassAlbumDetailDao;
import com.bootdo.edu.domain.ClassAlbumDO;
import com.bootdo.edu.domain.ClassAlbumDetailDO;
import com.bootdo.edu.service.ClassAlbumService;
@Service
public class ClassAlbumServiceImpl implements ClassAlbumService {

	@Autowired
	private ClassAlbumDao classAlbumDao;
	@Autowired
	private ClassAlbumDetailDao classAlbumDetailDao;

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
	
	@Override
	public List<ClassAlbumDO> list(String classId) {
		return classAlbumDao.list(classId);
	}

	@Override
	@Transactional
	public void add(ClassAlbumDO classAlbum, MultipartHttpServletRequest request) throws Exception {
		List<String> pathList = new ArrayList<String>();
		String fileName = "";
		String sftpFileName = "";
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
		//插入相册
		classAlbumDao.save(classAlbum);
		//上传照片到ftp
		MultiValueMap<String,MultipartFile> map=request.getMultiFileMap();
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		for(int i=0;i<map.size();i++) {
			fileList.add(map.get("file"+i).get(0));
		}
		for(MultipartFile resource:fileList) {
			fileName = resource.getOriginalFilename();
			sftpFileName = (String) fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
			String upLoadFileName = "classAlbum"+((UUID.randomUUID().toString()).replace("-", ""))+sftpFileName;
			sftpUtil.upload(uploadUrl + uploadFileUrl, upLoadFileName, resource.getInputStream());
			pathList.add(serverUrl + "/uploadFile/" + upLoadFileName);
		}
		sftpUtil.logout();
		//插入子表
		List<ClassAlbumDetailDO> detailList = new ArrayList<ClassAlbumDetailDO>();
		for (String url : pathList) {
			ClassAlbumDetailDO classAlbumDetail = new ClassAlbumDetailDO();
			classAlbumDetail.setAlbumId(classAlbum.getId());
			classAlbumDetail.setPhotoUrl(url);
			detailList.add(classAlbumDetail);
		}
		classAlbumDetailDao.batchInsert(detailList);
	}
	@Transactional
	public List<ClassAlbumDetailDO> editAdd(Long id,MultipartHttpServletRequest request)throws Exception{
		List<String> pathList = new ArrayList<String>();
		String fileName = "";
		String sftpFileName = "";
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
		//上传照片到ftp
		MultiValueMap<String,MultipartFile> map=request.getMultiFileMap();
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		for(int i=0;i<map.size();i++) {
			fileList.add(map.get("file"+i).get(0));
		}
		for(MultipartFile resource:fileList) {
			fileName = resource.getOriginalFilename();
			sftpFileName = (String) fileName.subSequence(fileName.lastIndexOf("."), fileName.length());
			String upLoadFileName = "classAlbum"+((UUID.randomUUID().toString()).replace("-", ""))+sftpFileName;
			sftpUtil.upload(uploadUrl + uploadFileUrl, upLoadFileName, resource.getInputStream());
			pathList.add(serverUrl + "/uploadFile/" + upLoadFileName);
		}
		sftpUtil.logout();
		//插入子表
		List<ClassAlbumDetailDO> detailList = new ArrayList<ClassAlbumDetailDO>();
		for (String url : pathList) {
			ClassAlbumDetailDO classAlbumDetail = new ClassAlbumDetailDO();
			classAlbumDetail.setAlbumId(id);
			classAlbumDetail.setPhotoUrl(url);
			detailList.add(classAlbumDetail);
		}
		classAlbumDetailDao.batchInsert(detailList);
		return detailList;
		
	}
	
	@Transactional
	public void delete(Long detailId)throws Exception{
		//根据id查询该对象并获取url
		ClassAlbumDetailDO classAlbumDetail = classAlbumDetailDao.get(detailId);
		String photoUrl = classAlbumDetail.getPhotoUrl();
		//删除该条记录
		classAlbumDetailDao.remove(detailId);
		//删除ftp上的图片
		String fileName = photoUrl.substring(photoUrl.lastIndexOf("/")+1);
		SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
		sftpUtil.login();
		sftpUtil.delete(uploadUrl + uploadFileUrl, fileName);
		sftpUtil.logout();
	}

	@Override
	public int update(ClassAlbumDO classAlbumDo) {
		return classAlbumDao.update(classAlbumDo);
	}
}
