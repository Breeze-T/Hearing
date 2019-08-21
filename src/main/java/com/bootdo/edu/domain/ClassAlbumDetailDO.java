package com.bootdo.edu.domain;

import java.util.HashMap;
import java.util.Map;

public class ClassAlbumDetailDO {
	private Long id;
	private Long albumId;
	private String photoUrl;
	//扩展属性集合
    private final Map<String,Object> map=new HashMap<String, Object>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public Map<String, Object> getMap() {
		return map;
	}
    
}
