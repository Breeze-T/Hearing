package com.bootdo.app.controller;

import com.bootdo.app.service.ClassPhotoService;
import com.bootdo.edu.domain.ClassAlbumDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24 0024.
 */
@Controller
@RequestMapping("/app")
public class ClassPhotoController {

    @Autowired
    private ClassPhotoService classPhotoService;
    @ResponseBody
    @RequestMapping("/classPhoto/list")
    public List<ClassAlbumDO> list(String classId) {
        return classPhotoService.list(classId);
    }
}
