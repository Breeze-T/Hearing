package com.bootdo.app.service;

import com.bootdo.app.dao.StudentCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Service
public class StudentCheckService {
    @Autowired
    private StudentCheckDao studentCheckDao;

    public List<Map<String,Object>> list(String checkType,String pId){
        return studentCheckDao.list(checkType,pId);
    }

    public void insertStudentDetail(String id,String studentId,String userId,
                                    String checkId,String score,String remark,String createUser){
        studentCheckDao.insertStudentDetail(id,studentId,userId,checkId,score,remark,createUser);
    }
    public void insertStudentClock(String classId,String optUser,String scheduleId,String studentId,String status,String type,String remark){
        studentCheckDao.insertStudentClock(classId,optUser,scheduleId,studentId,status,type,remark);
    }
    public void updateStudentClock(String clockId,String status,String remark,String optUser){
        studentCheckDao.updateStudentClock(clockId,status,remark,optUser);
    }

    public Integer selectSchedule(String classId){
        return studentCheckDao.selectSchedule(classId);
    }

    public List<Map<String,Object>> selecteRandomCheck(String classId){
        return studentCheckDao.selecteRandomCheck(classId);
    }
    public void insertRandomCheck(String classId,String fieldName,String checkName,String userId){
        studentCheckDao.insertRandomCheck(classId,fieldName,checkName,userId);
    }

    public List<Map<String,Object>> selectStudentByRandomCheck(String classId,String checkId){
        return studentCheckDao.selectStudentByRandomCheck(classId,checkId);
    }
    public void insertStudentByRandomCheck(String classId,String checkId,String optUser,String studentId,
                                           String status,String remark){
        studentCheckDao.insertStudentByRandomCheck(classId,checkId,optUser,studentId,status,remark);
    }
}
