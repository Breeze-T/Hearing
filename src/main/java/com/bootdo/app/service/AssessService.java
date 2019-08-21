
package com.bootdo.app.service;

import com.bootdo.app.dao.AssessDao;
import com.bootdo.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Service
public class AssessService {
    @Autowired
    private AssessDao assessDao;

    public List<Map<String,Object>> list(String userId,String classId,String date){
        if("".equals(date) || date == null){
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            date = sim.format(new Date(System.currentTimeMillis()));
        }
        return assessDao.list(userId,classId,date);
    }
    public void insertAssess(String classId,String subjectId,String userId,String scheduleId,String assessType,String assessContent,String assessUser){
        assessDao.insertAssess(classId,subjectId,userId,scheduleId,assessType,assessContent,assessUser);
    }
    public List<Map<String,Object>> getAssessStatus(String classId,String date){
        return assessDao.getAssessStatus(classId,date);
    }
    public List<Map<String,Object>> getAssessStatusDetail(String assessUser,String date){
        return assessDao.getAssessStatusDetail(assessUser,date);
    }
    public List<Map<String,Object>> getNoAssessStatusDetail(String classId,String assessUser,String date){
        return assessDao.getNoAssessStatusDetail(classId,assessUser,date);
    }

    public List<Map<String,Object>> getAssessDetail(String userId,String classId,String subjectId,String scheduleId){
        return assessDao.getAssessDetail(userId,classId,subjectId,scheduleId);
    }

    public void insertMessageRemind(String scheduleId,String remindUser,String userId){
        assessDao.insertMessageRemind(scheduleId,remindUser,userId);
    }
    public List<Map<String,Object>> getRemindAssess(String userId){
        return assessDao.getRemindAssess(userId);
    }

    public R updateRemindAssess(String id){
        try {
            assessDao.updateRemindAssess(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
