package com.bootdo.personal.service;

import com.bootdo.edu.domain.EduClassDO;
import com.bootdo.system.domain.MenuDO;

import java.util.List;
import java.util.Map;

/**
 * Created by Breeze on 2019/8/12.
 */
public interface DataService {

    int count(Map<String, Object> map);

    List<Map> list(Map<String, Object> map);

    MenuDO get(Long id);
}
