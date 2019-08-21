package com.bootdo.system.service.impl;

/**
 * Created by Breeze on 2019/7/13.
 */
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.system.dao.RoleMenuDao;
import com.bootdo.system.dao.TangDao;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.domain.TangDO;
import com.bootdo.system.service.TangService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class TangServiceImpl  implements TangService {

    @Autowired
    TangDao menuMapper;
    @Autowired
    RoleMenuDao roleMenuMapper;

    /*@Cacheable
    @Override
    public Tree<TangDO> getSysMenuTree(Long id) {
        List<Tree<TangDO>> trees = new ArrayList<Tree<TangDO>>();
        List<TangDO> menuDOs = menuMapper.listMenuByUserId(id);
        for (TangDO sysMenuDO : menuDOs) {//冒号：遍 历集合取出每一个元素，赋给sysMenuDO
            Tree<TangDO> tree = new Tree<TangDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getAction());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<TangDO> t = BuildTree.build(trees);
        return t;
    }*/

    @Override
    public int count(Map<String, Object> map) {
        return menuMapper.count(map);
    }

    @Override
    public List<TangDO> list(Map<String, Object> params) {
        List<TangDO> menus = menuMapper.list(params);
        return menus;
    }

    @Override
    public TangDO get(Long id) {
        TangDO menuDO = menuMapper.get(id);
        return menuDO;
    }

    @Override
    public Tree<TangDO> getTree() {
        List<Tree<TangDO>> trees = new ArrayList<Tree<TangDO>>();
        List<TangDO> menuDOs = menuMapper.list(new HashMap<>(16));
        for (TangDO sysMenuDO : menuDOs) {
            Tree<TangDO> tree = new Tree<TangDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<TangDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<TangDO> getTree(Long id) {
        // 根据roleId查询权限
        List<TangDO> menus = menuMapper.list(new HashMap<String, Object>(16));
        List<Long> menuIds = roleMenuMapper.listMenuIdByRoleId(id);
        List<Long> temp = menuIds;
        for (TangDO menu : menus) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<TangDO>> trees = new ArrayList<Tree<TangDO>>();
        List<TangDO> menuDOs = menuMapper.list(new HashMap<String, Object>(16));
        for (TangDO sysMenuDO : menuDOs) {
            Tree<TangDO> tree = new Tree<TangDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = sysMenuDO.getId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<TangDO> t = BuildTree.build(trees);
        return t;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public int remove(Long id) {
        int result = menuMapper.remove(id);
        return result;
    }
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public int save(TangDO menu) {
        int r = menuMapper.save(menu);
        return r;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public int update(TangDO menu) {
        int r = menuMapper.update(menu);
        return r;
    }

    /*@Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = menuMapper.listUserPerms(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<Tree<TangDO>> listMenuTree(Long id) {
        List<Tree<TangDO>> trees = new ArrayList<Tree<TangDO>>();
        List<TangDO> menuDOs = menuMapper.listMenuByUserId(id);
        for (TangDO sysMenuDO : menuDOs) {
            Tree<TangDO> tree = new Tree<TangDO>();
            tree.setId(sysMenuDO.getId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getAction());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<TangDO>> list = BuildTree.buildList(trees, "0");
        return list;
    }*/

}
