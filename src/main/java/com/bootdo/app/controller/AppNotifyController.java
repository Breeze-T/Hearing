package com.bootdo.app.controller;

import com.bootdo.common.config.Constant;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.oa.domain.NotifyRecordDO;
import com.bootdo.oa.service.NotifyRecordService;
import com.bootdo.oa.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/24 0024.
 */
@Controller
@RequestMapping("/app")
public class AppNotifyController {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private NotifyRecordService notifyRecordService;

    @ResponseBody
    @RequestMapping("/countGetNoRead")
    public R countGetNoRead(String userId) {
      int count=  notifyService.countGetNoRead(userId);
        return R.ok(count+"");
    }
    @ResponseBody
    @RequestMapping("/getMsgList")
    PageUtils getMsgList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        return notifyService.selfList(query);
    }
    @ResponseBody
    @GetMapping("/read")
    public R read(Long id,Long userId) {
       // NotifyDO notify = notifyService.get(id);
        //更改阅读状态
        NotifyRecordDO notifyRecordDO = new NotifyRecordDO();
        notifyRecordDO.setNotifyId(id);
        notifyRecordDO.setUserId(userId);
        notifyRecordDO.setReadDate(new Date());
        notifyRecordDO.setIsRead(Constant.OA_NOTIFY_READ_YES);
        notifyRecordService.changeRead(notifyRecordDO);
        return R.ok();
    }
}
