package com.bootdo.activiti.controller;

import com.bootdo.activiti.service.ActTaskService;
import com.bootdo.activiti.vo.ProcessVO;
import com.bootdo.activiti.vo.TaskVO;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**

 */
@RequestMapping("activiti/task")
@RestController
public class TaskController {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    FormService formService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    ActTaskService actTaskService;
    @GetMapping("goto")
    public ModelAndView gotoTask(){
        return new ModelAndView("act/task/gotoTask");
    }

    @GetMapping("/gotoList")
    PageUtils list(int offset, int limit) {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .listPage(offset, limit);
        int count = (int) repositoryService.createProcessDefinitionQuery().count();
        List<Object> list = new ArrayList<>();
        for(ProcessDefinition processDefinition: processDefinitions){
            list.add(new ProcessVO(processDefinition));
        }

        PageUtils pageUtils = new PageUtils(list, count);
        return pageUtils;
    }

    @GetMapping("/form/{procDefId}")
    public void startForm(@PathVariable("procDefId") String procDefId  ,HttpServletResponse response) throws IOException {
        String formKey = actTaskService.getFormKey(procDefId, null);
        response.sendRedirect(formKey);
    }

    @GetMapping("/form/{procDefId}/{taskId}")
    public void form(@PathVariable("procDefId") String procDefId,@PathVariable("taskId") String taskId ,HttpServletResponse response) throws IOException {
        // 获取流程XML上的表单KEY

        String formKey = actTaskService.getFormKey(procDefId, taskId);


        response.sendRedirect(formKey+"/"+taskId);
    }

    @GetMapping("/todo")
    ModelAndView todo(){
        return new ModelAndView("act/task/todoTask");
    }

    @GetMapping("/todoList")
    List<TaskVO> todoList(){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(ShiroUtils.getUser().getUserId().toString()).list();
        List<TaskVO> taskVOS =  new ArrayList<>();
        for(Task task : tasks){
            TaskVO taskVO = new TaskVO(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    @GetMapping("/tohiList")
    List<TaskVO> tohiList(){
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().taskAssignee(ShiroUtils.getUser().getUserId().toString()).list();
        List<TaskVO> taskVOS =  new ArrayList<>();
        for(HistoricTaskInstance task : tasks){
//            task.getName()
//            TaskVO taskVO = new TaskVO();
//            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    /***
     * 删除流程
     * @param processInstanceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteProcessInstance/{processInstanceId}")
    public R deleteProcessInstance(@PathVariable("processInstanceId") String processInstanceId){
        try {
            actTaskService.deleteProcessInstance(processInstanceId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }
    /***
     * 删除历史流程
     * @param processInstanceId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteHistoryProcessInstance/{processInstanceId}")
    public R deleteHistoryProcessInstance(@PathVariable("processInstanceId") String processInstanceId){
        try {
            actTaskService.deleteHistoryProcessInstance(processInstanceId);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/trace/photo/{procDefId}/{execId}")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);

        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }


}
