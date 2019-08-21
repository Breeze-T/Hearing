package com.bootdo.system.service.impl;

import com.bootdo.system.dao.SignCheckDao;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.service.SignCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SignCheckServiceImpl implements SignCheckService {

    @Autowired
    SignCheckDao signCheckDao;

    @Override
    public List<Map<String,Object>> list(Map<String, Object> map) {
        List<Map<String,Object>> list = signCheckDao.list(map);
        return list;
    }
    @Override
    public int count(Map<String, Object> map){
        return signCheckDao.count(map);
    }
}
