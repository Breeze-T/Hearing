package com.bootdo.activiti.controller;

import com.bootdo.activiti.domain.SalaryDO;
import com.bootdo.activiti.domain.StudentLeave;
import com.bootdo.activiti.service.SalaryService;
import com.bootdo.activiti.service.StudentLeaveService;
import com.bootdo.activiti.utils.ActivitiUtils;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
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
@RequestMapping("/student/leave")
public class StudentLeaveController extends BaseController{
    @Autowired
    private StudentLeaveService studentLeaveService;
    @Autowired
    ActivitiUtils activitiUtils;

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<StudentLeave> salaryList = studentLeaveService.list(query);
        int total = studentLeaveService.count(query);
        PageUtils pageUtils = new PageUtils(salaryList, total);
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
    @PostMapping("/save")
    public R saveOrUpdate(StudentLeave studentLeave) {
        studentLeave.setId(UUID.randomUUID().toString().replace("-",""));
        studentLeave.setCreateDate(new Date());
        studentLeave.setUpdateDate(new Date());
        studentLeave.setCreateUser(ShiroUtils.getUserId().toString());
        studentLeave.setUpdateUser(ShiroUtils.getUserId().toString());
        if (studentLeaveService.save(studentLeave) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(StudentLeave studentLeave) {
//        String taskKey = activitiUtils.getTaskByTaskId(studentLeave.getTaskId()).getTaskDefinitionKey();
        studentLeaveService.update(studentLeave);
        return R.ok();
    }
    @ResponseBody
    @RequestMapping("/getHisLeave")
    public List<Map<String, Object>> getHisLeave(){
        return studentLeaveService.getHisLeave();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
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
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") String[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        studentLeaveService.batchRemove(ids);
        return R.ok();
    }

}
