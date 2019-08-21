package com.bootdo.app.controller;

import com.bootdo.activiti.domain.StudentLeave;
import com.bootdo.activiti.service.StudentLeaveService;
import com.bootdo.activiti.utils.ActivitiUtils;
import com.bootdo.app.domain.StudentReportDO;
import com.bootdo.app.service.StudentReportService;
import com.bootdo.common.annotation.Log;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.*;
import com.bootdo.edu.domain.StudentDO;
import com.bootdo.edu.service.StudentService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;
import com.bootdo.system.vo.UserVO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 */

@Controller
@RequestMapping("/app")
public class StudentReportController extends BaseController{
    @Autowired
    private StudentReportService studentReportService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;

    @Log("APP登录")
    @PostMapping("/appLogin")
    @ResponseBody
    R AppLogin(String username, String password) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Map<String,Object> map = new HashedMap();
            UserDO user = ShiroUtils.getUser();
            List<Map<String,Object>> list = studentReportService.selectStudent(user.getUsername());
            if(list.size() > 0){
                if("0".equals(list.get(0).get("status"))){
                    return R.error("帐号审核中！");
                }else if("2".equals(list.get(0).get("status"))){
                    return R.error("帐号审核未通过！");
                }
            }
            List<Map<String,Object>> roleList = userService.userRoleList(user.getUserId().toString());
            user.setRoles(roleList);
            boolean isAdmin=false;
            a:
            for(Map<String,Object> rolemap : roleList){
                if(((Long)rolemap.get("roleId")).compareTo(1l)==0){
                    isAdmin=true;
                    break a;
                }
            }
            if(isAdmin){
                List<Map<String,Object>> classList = userService.classAllList();
                user.setClasses(classList);
            }else{
                List<Map<String,Object>> classList = userService.userClassList(user.getUserId().toString());
                user.setClasses(classList);
            }
            List<Map<String,Object>> scoreList = studentReportService.getcheckScore(user.getUserId().toString());//查询的时候顺便把学生头像查了一下

            user.setScore("0");
            map.put("user",user);
            map.put("sessionId",subject.getSession().getId().toString());
            String photo=null;//新增头像，null字符串说明没有头像
            if(scoreList.size()>0){
                photo=(String) scoreList.get(0).get("photo");
                if("1".equals(scoreList.get(1).get("type"))){
                    Double score = (Double) scoreList.get(0).get("score");
                    user.setScore(score.toString());
                }else{
                    String s = String.valueOf(scoreList.get(0).get("score"));
                    user.setScore(s);
                }
            }
            map.put("photo",photo);
            return R.ok(map);
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系班主任");
        }  catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @ResponseBody
    @RequestMapping("/studentReport/list")
    public List<StudentReportDO> list(String classId,String studentName,String id) {
        return studentReportService.list(classId,studentName,id);
    }

    /**
     * 新生报到接口
     */
    @ResponseBody
    @PostMapping("/studentReport/save")
    public R saveOrUpdate(HttpServletResponse resp,StudentDO studentDO,@RequestParam(value="file",required=false)MultipartFile file) {
        try {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            List<Map<String,Object>> list = studentReportService.selectStudent(studentDO.getAlarmNum());
            if(list.size() > 0){
                return R.error("该警号已经注册！");
            }
            if(file != null && !"".equals(file)){
                String photoUrl = studentReportService.saveDiskFile(file);
                studentDO.setPhoto(photoUrl);
            }

            studentDO.setStatus("0");
            studentService.studentSave(studentDO);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /***
     * 获取最近一年内所有班级信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentReport/getAllClass")
    public List<Map<String,Object>> getAllClass(){
        return studentReportService.getAllClass();
    }
    /**
     * 根据ID获取用户信息
     */
    @ResponseBody
    @RequestMapping("/studentReport/getUserInfo")
    public List<Map<String,Object>> getUserInfo(String userId){
        return studentReportService.getUserInfo(userId);
    }

    /***
     * 修改个人信息
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentReport/updateUserInfo")
    public R updateUserInfo(@RequestParam Map<String, Object> params){
        return studentReportService.updateUserInfo(params);
    }
    /***
     * 根据班级ID获取班级下的信息
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentReport/selectStudentByClass")
    public List<Map<String,Object>> selectStudentByClass(String classId,String studentName){
        return studentReportService.selectStudentByClass(classId,studentName);
    }
    @ResponseBody
    @RequestMapping("/studentReport/selectClassStudent")
    public List<Map<String,Object>> selectClassStudent(String classId,String studentName){
        return studentReportService.selectClassStudent(classId,studentName);
    }
    /**
     * 密码更改
     */
    @PostMapping("/studentReport/updateUserPassword")
    @ResponseBody
    public R updateUserPassword(String userName,String userId,String oldPassword,String newPassword) {
        UserDO userDO = userService.get(Long.parseLong(userId));
        UserVO userVO = new UserVO();
        userVO.setPwdNew(newPassword);
        userVO.setPwdOld(oldPassword);
        userVO.setUserDO(userDO);

        try {
            userService.resetPwd(userVO,userDO);
            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }

    /**
     * 文件上传
     * @param req
     * @param resp
     * @param file
     * @return
     */
    @RequestMapping(value = "/studentReport/fileUpload", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public String submission(HttpServletRequest req, HttpServletResponse resp, @RequestParam(value="file",required=false)MultipartFile file) {
        if(file == null){
            return null;
        }
        resp.setHeader("Access-Control-Allow-Origin", "*");
        return studentReportService.saveDiskFile(file);
    }
    /**
     * 删除
     */
    @PostMapping("/studentReport/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") String[] ids) {
//        studentLeaveService.batchRemove(ids);
        return R.ok();
    }

}
