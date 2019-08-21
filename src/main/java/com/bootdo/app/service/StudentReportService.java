package com.bootdo.app.service;

import com.bootdo.app.dao.StudentReportDao;
import com.bootdo.app.domain.StudentReportDO;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.SFTPUtil;
import com.jcraft.jsch.SftpException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5 0005.
 */
@Service
public class StudentReportService {
    @Autowired
    private StudentReportDao studentReportDao;
    @Autowired
    private StudentCheckService studentCheckService;
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

    public List<Map<String,Object>> getAllClass(){
        return studentReportDao.getAllClass();
    }

    public List<StudentReportDO> list(String classId,String studentName,String id){
        List<StudentReportDO> list = studentReportDao.list(classId,studentName,id);
        for(StudentReportDO studentReportDO : list){
            if(studentReportDO.getPositionType().startsWith("student")){//获取大队长下面的成员
                List<StudentReportDO> list1 = this.findStudent(studentReportDO);//获取中队长
                for(StudentReportDO studentReportDO1 : list1){
                    List<StudentReportDO> list2 = this.findStudent(studentReportDO1);//获取组长
                    for(StudentReportDO studentReportDO2 : list2){//获取组长下的组员
                        List<StudentReportDO> list3 = this.findStudent(studentReportDO2);
                        studentReportDO2.setChildrens(list3);
                    }
                    studentReportDO1.setChildrens(list2);
                }
                studentReportDO.setChildrens(list1);
            }
        }
        return list;
    }
    public List<Map<String,Object>> selectStudentByClass(String classId,String studentName){
        Integer scheduleId = studentCheckService.selectSchedule(classId);
        if(scheduleId == null){
            return new ArrayList<>();
        }
        return studentReportDao.selectStudentByClass(classId,studentName,scheduleId);
    }
    public List<Map<String,Object>> selectClassStudent(String classId,String studentName){
        return studentReportDao.selectClassStudent(classId,studentName);
    }
    public List<StudentReportDO> findStudent(StudentReportDO studentReportDO){
        String id = studentReportDO.getId();
        return studentReportDao.findStudent(id);
    }
    public String saveDiskFile(MultipartFile file) {
        String url = "";
        SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
        sftpUtil.login();
        try {
            String filename = file.getOriginalFilename();
            String prefix = filename.substring(filename.lastIndexOf(".") + 1);
            String fileName = "party" + String.valueOf(new Date().getTime()) + "." + prefix;
            sftpUtil.upload(uploadUrl + uploadFileUrl, fileName, file.getInputStream());
            url = serverUrl + "/uploadFile/" + fileName;
            sftpUtil.logout();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            e.getMessage();
        }
        return url;
    }

    public void deleteSftpFile(String imgUrl){
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.length());
        SFTPUtil sftpUtil = new SFTPUtil(sftpUserName, sftpPassword, sftpHost, sftpPort);
        sftpUtil.login();
        try {
            sftpUtil.delete("/uploadFile/",fileName);
            sftpUtil.logout();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public R updateUserInfo(Map<String,Object> params){
        if(!params.get("oldImg").equals(params.get("photo"))){//新旧照片不相同，则删除老照片
            String oldImgUrl = (String)params.get("oldImg");
            this.deleteSftpFile(oldImgUrl);
        }
        if("男".equals(params.get("sex"))){
            params.replace("sex","M");
        }else if("女".equals(params.get("sex"))){
            params.replace("sex","F");
        }
        if("1".equals(params.get("type"))){//教师
            studentReportDao.updateTeacher(params);
            if("M".equals(params.get("sex"))){
                params.replace("sex",96);
            }else if("F".equals(params.get("sex"))){
                params.replace("sex",97);
            }
            studentReportDao.updateSysUser(params);
            return R.ok("修改成功");
        }else if("2".equals(params.get("type"))){//学生
            studentReportDao.updateStudent(params);
            if("M".equals(params.get("sex"))){
                params.replace("sex",96);
            }else if("F".equals(params.get("sex"))){
                params.replace("sex",97);
            }
            studentReportDao.updateSysUser(params);
            return R.ok("修改成功");
        }else{
            return R.error("无权修改");
        }
    }
    public List<Map<String,Object>> getUserInfo(String userId){
        return studentReportDao.getUserInfo(userId);
    }

    public List<Map<String,Object>> getcheckScore(String userId){
        List<Map<String,Object>> list = studentReportDao.selectUserType(userId);
        List<Map<String,Object>> scroeList = new ArrayList<>();
        Map<String,Object> valueMap = new HashedMap();
        for(Map<String,Object> map : list){
            if("1".equals(map.get("type"))){//教官
                scroeList = studentReportDao.getTeacherScore(userId);
                valueMap.put("type","1");
                scroeList.add(valueMap);
            }else if("2".equals(map.get("type"))){//学生
                scroeList = studentReportDao.getcheckScore(userId);
                Integer score = 100;
                for (Map<String,Object> scoreMap : scroeList){
                    if("1".equals(scoreMap.get("checkType"))){
                        score += Integer.parseInt(scoreMap.get("score").toString());
                    }else if("2".equals(scoreMap.get("checkType"))){
                        score -= Integer.parseInt(scoreMap.get("score").toString());
                    }else{
                        scoreMap.put("score",score.toString());
                    }
                }
                List<Map<String,Object>> ls = new ArrayList<>();
                valueMap.put("photo",scroeList.get(0).get("photo"));
                valueMap.put("score",score);
                ls.add(valueMap);
                Map<String,Object> m = new HashedMap();
                m.put("type","2");
                ls.add(m);
                scroeList = ls;
            }
        }
        return scroeList;
    };

    public List<Map<String,Object>> selectStudent(String userCode){
        return studentReportDao.selectStudent(userCode);
    }
}
