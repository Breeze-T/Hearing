
package com.bootdo.app.service;

import com.bootdo.app.dao.SignDao;
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
public class SignService {
    @Autowired
    private SignDao signDao;

    public List<Map<String,Object>> list(String userId,String date){
        if("".equals(date) || date == null){
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            date = sim.format(new Date(System.currentTimeMillis()));
        }
        return signDao.list(userId,date);
    }

    public void insertSign(String userId, String longitude, String latitude,String signAddr,String remark){
        signDao.insertSign(userId,longitude,latitude,signAddr,remark);
    }
}
