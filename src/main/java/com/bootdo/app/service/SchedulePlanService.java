package com.bootdo.app.service;

import com.bootdo.app.dao.SchedulePlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Service
public class SchedulePlanService {
    @Autowired
    private SchedulePlanDao schedulePlanDao;

    public List<Map<String,Object>> list(String classId){
        return schedulePlanDao.list(classId);
    }
}
