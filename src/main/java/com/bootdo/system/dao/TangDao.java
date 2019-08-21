package com.bootdo.system.dao;

import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.domain.TangDO;

import java.util.List;
import java.util.Map;

/**
 * Created by Breeze on 2019/7/13.
 */
public interface TangDao {

    List<TangDO> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    TangDO get(Long menuId);

    int save(TangDO menu);

    int update(TangDO menu);

    int remove(Long menuId);

    int batchRemove(Long[] menuIds);

/*    List<TangDO> listMenuByUserId(Long id);

    List<String> listUserPerms(Long id);*/
}
