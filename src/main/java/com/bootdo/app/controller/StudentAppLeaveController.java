package com.bootdo.app.controller;

import com.bootdo.activiti.domain.StudentLeave;
import com.bootdo.activiti.service.StudentLeaveService;
import com.bootdo.activiti.utils.ActivitiUtils;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 */

@Controller
@RequestMapping("/app")
public class StudentAppLeaveController extends BaseController{
    @Autowired
    private StudentLeaveService studentLeaveService;
    @Autowired
    ActivitiUtils activitiUtils;
    @Autowired
    DictService dictService;

    @ResponseBody
    @RequestMapping("/studentLeave/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<StudentLeave> studentLeaveList = studentLeaveService.list(query);
        int total = studentLeaveService.count(query);
        PageUtils pageUtils = new PageUtils(studentLeaveList, total);
        return pageUtils;
    }

    @GetMapping("/from")
    String add() {
        return "act/student/leaveAdd";
    }

    @GetMapping("/from/{taskId}")
    String edit(@PathVariable("taskId") String taskId, Model model) {
        StudentLeave studentLeave = studentLeaveService.get(activitiUtils.getBusinessKeyByTaskId(taskId));
        studentLeave.setTaskId(taskId);
        model.addAttribute("studentLeave", studentLeave);
        return "act/student/leaveEdit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/studentLeave/save")
    public R saveOrUpdate(StudentLeave studentLeave,String userId) {
        studentLeave.setId(UUID.randomUUID().toString().replace("-",""));
        studentLeave.setCreateDate(new Date());
        studentLeave.setUpdateDate(new Date());
        studentLeave.setCreateUser(userId);
        studentLeave.setUpdateUser(userId);
        studentLeave.setSickStatus("0");
        studentLeave.setStatus("0");
        System.out.println(studentLeave.toString());
        if (studentLeaveService.save(studentLeave) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /***
     * 获取请假类型
     * @return
     */
    @ResponseBody
    @PostMapping("/studentLeave/getStudentLeaveType")
    public List<DictDO> getStudentLeaveType() {
        String dictType = "leave_type";
        return dictService.listByType(dictType);
    }
    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/studentLeave/update")
    public R update(StudentLeave studentLeave) {
//        String taskKey = activitiUtils.getTaskByTaskId(studentLeave.getTaskId()).getTaskDefinitionKey();
        return studentLeaveService.update(studentLeave);
    }

    /**
     * 更新销假状态
     * @param status
     * @param remark
     * @return
     */
    @ResponseBody
    @RequestMapping("/studentLeave/updateSickStatus")
    public R updateSickStatus(String status,String remark,String id){
        try {
            studentLeaveService.updateSickStatus(status,remark,id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
    /**
     * 删除
     */
    @PostMapping("/studentLeave/remove")
    @ResponseBody
    public R remove(String id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        if (studentLeaveService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/studentLeave/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") String[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        studentLeaveService.batchRemove(ids);
        return R.ok();
    }

}
