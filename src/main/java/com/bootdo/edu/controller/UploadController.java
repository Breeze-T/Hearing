package com.bootdo.edu.controller;

import com.bootdo.common.utils.R;
import com.bootdo.edu.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供商表
 * 
 * @author lvbin
 * @email 123456@163.com
 * @date 2019-05-07 10:36:10
 */
 
@Controller
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	private UploadService uploadService;

	/**
	 * 上传图片
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upLoadFileList")
	@ResponseBody
	public R uploadFile(MultipartHttpServletRequest request) throws Exception{
		MultiValueMap<String,MultipartFile> multiFileMap=request.getMultiFileMap();
		List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		for(int i=0;i<multiFileMap.size();i++) {
			if(multiFileMap.get("file"+i)!=null){
				fileList.add(multiFileMap.get("file"+i).get(0));
			}
		}
		String urls = "";
		if(!fileList.isEmpty()) {
			urls = uploadService.upLoadFileList(fileList);
		}
		return R.error(0,urls);
	}
	/**
	 * 上传图片
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upLoadFile")
	@ResponseBody
	public R uploadFiles(@RequestParam("file")MultipartFile[] file) throws Exception{
		String urls = "";
		return R.error(0,urls);
	}

	
}
