package com.bootdo.system.service;

import com.bootdo.common.domain.Tree;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.domain.TangDO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Breeze on 2019/7/13.
 */
public interface TangService {
    List<TangDO> list(Map<String, Object> params);

    TangDO get(Long id);

    Tree<TangDO> getTree();

    Tree<TangDO> getTree(Long id);

    int remove(Long id);

    int save(TangDO menu);

    int update(TangDO menu);

    int count(Map<String, Object> map);

/*    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Set<String> listPerms(Long userId);*/
}
