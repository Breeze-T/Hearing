package com.bootdo.app.controller;

import com.bootdo.app.service.StudentCheckService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */

@Controller
@RequestMapping("/app")
public class StudentCheckController extends BaseController{
    @Autowired
    private StudentCheckService studentCheckService;

    @ResponseBody
    @RequestMapping("/studentCheck/list")
    public List<Map<String,Object>> list(String checkType, String pId) {
        if("".equals(pId) || pId == null){
            pId = "0";
        }
        return studentCheckService.list(checkType,pId);
    }

    @ResponseBody
    @RequestMapping("/studentCheck/insertStudentDetail")
    public R insertStudentDetail(String studentId,String userId,
                                 String[] checkId,String score,String[] remark){
        UserDO user = ShiroUtils.getUser();
        if(remark.length==0){
            remark=new String[1];
            remark[0]="";
        }
        try {
            for(int i=0;i<checkId.length;i++){
                String id = UUID.randomUUID().toString().replace("-","");
                studentCheckService.insertStudentDetail(id,studentId,userId,checkId[i],score,remark[i],user.getUserId()+"");
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /****
     * 学员考勤录入接口
     * @param classId
     * @param optUser
     * @param studentId
     * @param status
     * @param type
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentCheck/insertStudentClock")
    public R insertStudentClock(String classId,String optUser,String studentId,String status,String type,String remark,String clockId){
        try {
            if("1".equals(type)){
                Integer scheduleId = studentCheckService.selectSchedule(classId);
                if(scheduleId == null || "".equals(scheduleId)){
                    return R.error("不在上课时间");
                }else{
                    if(!"".equals(clockId) && clockId != null){
                        studentCheckService.updateStudentClock(clockId,status,remark,optUser);
                    }else{
                        studentCheckService.insertStudentClock(classId,optUser,scheduleId.toString(),studentId,status,type,remark);
                    }
                    return R.ok();
                }
            }else{
                studentCheckService.insertStudentClock(classId,optUser,"",studentId,status,type,remark);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /***
     * 插入随机点名主题信息
     * @param classId
     * @param fieldName
     * @param checkName
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentCheck/insertRandomCheck")
    public R insertRandomCheck(String classId,String fieldName,String checkName,String userId){
        try {
            studentCheckService.insertRandomCheck(classId,fieldName,checkName,userId);
            return R.ok("新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("新增失败！");
        }
    }

    /***
     * 获取随机点名考勤主题
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentCheck/selecteRandomCheck")
    public List<Map<String,Object>> selecteRandomCheck(String classId){
        return studentCheckService.selecteRandomCheck(classId);
    }

    /***
     * 获取随机点名学员信息
     * @param classId
     * @param checkId
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentCheck/selectStudentByRandomCheck")
    public List<Map<String,Object>> selectStudentByRandomCheck(String classId,String checkId){
        return studentCheckService.selectStudentByRandomCheck(classId,checkId);
    }

    /**
     * 随机点名信息录入
     * @param classId
     * @param checkId
     * @param optUser
     * @param studentId
     * @param status
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentCheck/insertStudentByRandomCheck")
    public R insertStudentByRandomCheck(String classId,String checkId,String optUser,String studentId,
                                           String status,String remark){
        try {
            studentCheckService.insertStudentByRandomCheck(classId,checkId,optUser,studentId,status,remark);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
}
