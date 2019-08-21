package com.bootdo.app.service;

import com.bootdo.app.dao.ClassPhotoDao;
import com.bootdo.edu.domain.ClassAlbumDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Service
public class ClassPhotoService {
    @Autowired
    private ClassPhotoDao classPhotoDao;

    public List<ClassAlbumDO> list(String classId){
        return classPhotoDao.list(classId);
    }
}
