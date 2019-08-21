package com.bootdo.edu.service.impl;

import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.bootdo.edu.dao.EduSubjectDao;
import com.bootdo.edu.domain.EduSubjectDO;
import com.bootdo.edu.service.EduSubjectService;



@Service
public class EduSubjectServiceImpl implements EduSubjectService {
	@Autowired
	private EduSubjectDao eduSubjectDao;
	
	@Override
	public EduSubjectDO get(Integer id){
		return eduSubjectDao.get(id);
	}
	
	@Override
	public List<EduSubjectDO> list(Map<String, Object> map){
		return eduSubjectDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduSubjectDao.count(map);
	}
	
	@Override
	public int save(EduSubjectDO eduSubject){
		UserDO userDO= ShiroUtils.getUser();
		eduSubject.setCreateUser(userDO.getUserId()+"");
		eduSubject.setCreateTime(new Date());
		return eduSubjectDao.save(eduSubject);
	}
	
	@Override
	public int update(EduSubjectDO eduSubject){
		return eduSubjectDao.update(eduSubject);
	}
	
	@Override
	public int remove(Integer id){
		return eduSubjectDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return eduSubjectDao.batchRemove(ids);
	}
	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		Tree<DeptDO> tree1 = new Tree<DeptDO>();
		tree1.setId("-2");
		tree1.setParentId("0");
		tree1.setText("学科");
		Map<String, Object> state1 = new HashMap<>(16);
		state1.put("opened", true);
		state1.put("mType", "dept");
		tree1.setState(state1);
		trees.add(tree1);
		Map<String, Object> map=new HashMap<String, Object>(16);
		map.put("state","1");
		List<EduSubjectDO> list = eduSubjectDao.list(map);
		for (EduSubjectDO eduSubjectDO : list) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(eduSubjectDO.getId()+"");
			tree.setParentId("-2");
			tree.setText(eduSubjectDO.getSubjectName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("mType", "user");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public List<Map> getSubjectTeacherList(String classId) {
		return eduSubjectDao.getSubjectTeacherList(classId);
	}
	@Override
	public List<Map> getSubjectScoreList(String classId) {
		return eduSubjectDao.getSubjectScoreList(classId);
	}
}
