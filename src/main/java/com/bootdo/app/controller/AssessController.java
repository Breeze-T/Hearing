package com.bootdo.app.controller;

import com.bootdo.app.service.AssessService;
import com.bootdo.app.service.StudentCheckService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.DictDO;
import com.bootdo.common.service.DictService;
import com.bootdo.common.utils.R;
import com.bootdo.edu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */

@Controller
@RequestMapping("/app")
public class AssessController extends BaseController{
    @Autowired
    private AssessService assessService;
    @Autowired
    private DictService dictService;
    @Autowired
    private EduTeacherService eduTeacherService;
    @ResponseBody
    @RequestMapping("/assess/list")
    public List<Map<String,Object>> list(String userId,String classId,String date) {
        return assessService.list(userId,classId,date);
    }

    @ResponseBody
    @RequestMapping("/assess/insertAssess")
    public R insertAssess(String classId,String subjectId,String userId,String scheduleId,String assessType,String assessContent,String assessUser){
        try {
            assessService.insertAssess(classId,subjectId,userId,scheduleId,assessType,assessContent,assessUser);
            //学生每次评价的时候，计算教师的评分
            eduTeacherService.getTeacherAssess(userId,true);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
    @ResponseBody
    @RequestMapping("/dictType/list/{type}")
    public List<DictDO> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        map.put("sort", "sort");
        List<DictDO> dictList = dictService.list(map);
        return dictList;
    }

    /***
     * 获取评价状况
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/getAssessStatus")
    public List<Map<String,Object>> getAssessStatus(String classId,String date){
        List<Map<String,Object>> list = assessService.getAssessStatus(classId,date);
        return list;
    }
    /***
     * 获取学员评价状况详情信息
     * @param assessUser,date
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/getAssessStatusDetail")
    public List<Map<String,Object>> getAssessStatusDetail(String assessUser,String date){
        List<Map<String,Object>> list = assessService.getAssessStatusDetail(assessUser,date);
        return list;
    }
    @ResponseBody
    @RequestMapping("/assess/getNoAssessStatusDetail")
    public List<Map<String,Object>> getNoAssessStatusDetail(String classId,String assessUser,String date){
        List<Map<String,Object>> list = assessService.getNoAssessStatusDetail(classId,assessUser,date);
        return list;
    }

    /***
     * 获取学员详细评价
     * @param userId
     * @param classId
     * @param subjectId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/getAssessDetail")
    public List<Map<String,Object>> getAssessDetail(String userId,String classId,String subjectId,String scheduleId){
        return assessService.getAssessDetail(userId,classId,subjectId,scheduleId);
    }

    /***
     *
     * @param scheduleId
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/insertMessageRemind")
    public R insertMessageRemind(String scheduleId,String remindUser,String userId){
        try {
            assessService.insertMessageRemind(scheduleId,remindUser,userId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /***
     * 获取当前登陆人的提醒评价信息
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/getRemindAssess")
    public List<Map<String,Object>> getRemindAssess(String userId){
        return assessService.getRemindAssess(userId);
    }

    /**
     * 更新提醒评价表
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/assess/updateRemindAssess")
    public R updateRemindAssess(String id){
        return assessService.updateRemindAssess(id);
    }

}
