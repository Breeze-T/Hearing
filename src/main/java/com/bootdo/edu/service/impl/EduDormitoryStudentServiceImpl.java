package com.bootdo.edu.service.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.edu.dao.EduDormitoryDao;
import com.bootdo.edu.dao.StudentDao;
import com.bootdo.edu.domain.EduDormitoryDO;
import com.bootdo.edu.domain.StudentDO;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bootdo.edu.dao.EduDormitoryStudentDao;
import com.bootdo.edu.domain.EduDormitoryStudentDO;
import com.bootdo.edu.service.EduDormitoryStudentService;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EduDormitoryStudentServiceImpl implements EduDormitoryStudentService {
	@Autowired
	private EduDormitoryStudentDao eduDormitoryStudentDao;
	@Autowired
	private StudentDao studentDao;
    @Autowired
    private EduDormitoryDao eduDormitoryDao;
	@Override
	public EduDormitoryStudentDO get(String userId){
		return eduDormitoryStudentDao.get(userId);
	}
	
	@Override
	public List<EduDormitoryStudentDO> list(Map<String, Object> map){
		return eduDormitoryStudentDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return eduDormitoryStudentDao.count(map);
	}
	
	@Override
	public int save(EduDormitoryStudentDO eduDormitoryStudent){
		return eduDormitoryStudentDao.save(eduDormitoryStudent);
	}
	
	@Override
	public int update(EduDormitoryStudentDO eduDormitoryStudent){
		return eduDormitoryStudentDao.update(eduDormitoryStudent);
	}
	
	@Override
	public int remove(String userId){
		return eduDormitoryStudentDao.remove(userId);
	}
	
	@Override
	public int batchRemove(String[] userIds){
		return eduDormitoryStudentDao.batchRemove(userIds);
	}
	@Transactional
	@Override
	public Tree<Map> getStudentGroup(Map inmap) {
		int zd=Integer.valueOf(inmap.get("team")+"");
		int count = Integer.valueOf(inmap.get("group")+"");;//把客户分成多少个块
		//List list=new ArrayList();
		Map sqlMap=new HashedMap();
		sqlMap.put("classId",inmap.get("classId"));
		sqlMap.put("sex","M");
		List list = studentDao.getMaplist(sqlMap);

		/*for(int i=1;i<101;i++){
			Map map=new HashMap();
			map.put("name","男学生"+i);
			map.put("id",""+i);
			map.put("type","4");
			list.add(map);
			//list.add("男学生"+i);
		}*/
		//List list1=new ArrayList<>();
		sqlMap.put("sex","F");
		List list1 = studentDao.getMaplist(sqlMap);
		if(list.size()+list1.size()<zd+1){
			return null;
		}
		if(list.size()+list1.size()<count+1){
			return null;
		}
		/*for(int i=0;i<5;i++){
			Map map=new HashMap();
			map.put("id","10"+i);
			map.put("name","女学生"+i);
			map.put("type","4");
			//list1.add(map);
			//list1.add("女学生"+i);
		}
*/
		List<List<String>> result = new ArrayList<List<String>>();


		//
		Map ddz=null;
		if(list.size()>0){
			ddz=(Map) list.get(0);//大队长
			list.remove(0);
		}else if(list1.size()>0){
			ddz=(Map) list1.get(0);//大队长
			list1.remove(0);
		}
		ddz.put("pId","0");
		ddz.put("number","1");
		ddz.put("type","1");

		List zdz=null;
		if(list.size()>zd){
			zdz=new ArrayList(list.subList(0,zd));
			list.subList(0,zd).clear();
		}else if(list1.size()>zd){
			zdz=new ArrayList(list1.subList(0,zd));
			list1.subList(0,zd).clear();
		}


    /*List zdz=new ArrayList<>();
    for(int i=0;i<2;i++){
        zdz.add(list.get(i));
        list.remove(i);
    }*/
		//
		result=averageAssign(list,count);
		List<List<String>> result1=averageAssign(list1,count);

		for(int i=0;i<result.size();i++){
			result.get(i).addAll(result1.get(i));
		}
		result=averageAssignList(result,zd);
		//String s= JSONObject.toJSONString(result);
		List<Map> studentList=new ArrayList<Map>();
		int znumber=0;
		for(int i=0;i<result.size();i++){
			List listzd= result.get(i);//中队列表
			Map map=(Map)zdz.get(i);
			map.put("flag","中队长");
			map.put("pId",ddz.get("id"));
			map.put("number",i+1);
			map.put("type","2");
			//listzd.add(map);
			for(int j=0;j<listzd.size();j++){
				List listzz=(List)listzd.get(j);
				Map map1=(Map) listzz.get(0);
				map1.put("flag","组长");
				map1.put("pId",map.get("id"));
				znumber+=1;
				map1.put("number",znumber);
				//map1.put("number",j+1);
				map1.put("type","3");
				for(int k=1;k<listzz.size();k++){
					Map zy= (Map)listzz.get(k);//中队列表
					zy.put("pId",map1.get("id"));
					zy.put("number",k);
					zy.put("type","4");
				}
				studentList.addAll(listzz);
			}

		}
		studentList.addAll(zdz);
		studentList.add(ddz);
		studentDao.batchUpdate(studentList);
		//String s= JSONObject.toJSONString(studentList);
		List<Tree<Map>> trees = new ArrayList<Tree<Map>>();
		for (Map ssmap : studentList) {
			Tree<Map> tree = new Tree<Map>();
			tree.setId(ssmap.get("id")+"");
			tree.setParentId(ssmap.get("pId")+"");
			tree.setText(ssmap.get("name")+"");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("number", ssmap.get("number")+"");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Map> t = BuildTree.build(trees);
		return t;
	}
@Transactional
	@Override
	public Boolean changeGroup(Long classIdOld, Long classIdNew) {
		Long[] ids={classIdOld,classIdNew};
		List<StudentDO> stuList = studentDao.getList(ids);
		if(stuList.size()>1){
			StudentDO studentDONew1=stuList.get(0);
			String typeNew1=studentDONew1.getType();
			String pIdNew1=studentDONew1.getpId();
			String numberNew1=studentDONew1.getNumber();

			StudentDO studentDONew2=stuList.get(1);
			String typeNew2=studentDONew2.getType();
			String pIdNew2=studentDONew2.getpId();
			String numberNew2=studentDONew2.getNumber();
			studentDONew1.setType(typeNew2);
			studentDONew1.setpId(pIdNew2);
			studentDONew1.setNumber(numberNew2);
			studentDONew2.setType(typeNew1);
			studentDONew2.setpId(pIdNew1);
			studentDONew2.setNumber(numberNew1);
			int a=studentDao.update(studentDONew1);
			int b=studentDao.update(studentDONew2);
			int c=studentDao.updateTempPid(studentDONew2.getId(),studentDONew1.getId());
			int d=studentDao.updateTempPid(studentDONew1.getId(),studentDONew2.getId());
			int e=studentDao.updatePid(studentDONew1.getId(),studentDONew2.getId());
		}

		return true;
	}
	@Override
	public Tree<Map> getDataBaseStudentGroup(Map inmap) {
		Map sqlMap=new HashedMap();
		sqlMap.put("classId",inmap.get("classId"));
		//先查询男生
		List <Map>studentList = studentDao.getMaplist(sqlMap);
		List<Tree<Map>> trees = new ArrayList<Tree<Map>>();
		for (Map ssmap : studentList) {
			Tree<Map> tree = new Tree<Map>();
			tree.setId(ssmap.get("id")+"");
			tree.setParentId(ssmap.get("pId")+"");
			tree.setText(ssmap.get("name")+"");
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			state.put("number", ssmap.get("number")+"");
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<Map> t = BuildTree.build(trees);
		return t;
	}

    @Override
    public int getDormitoryStudent(Map inmap) {
		Map sqlMap=new HashedMap();
		sqlMap.put("classId",inmap.get("classId"));
		sqlMap.put("sex","M");
		//先查询男生
		int a= distributionDormitoryStudent(sqlMap);
		sqlMap.put("sex","F");
		//再查询女生
		int b= distributionDormitoryStudent(sqlMap);
		if(b==-1||a==-1){
			//宿舍不够
			return -1;
		}
        return a+b;
    }

	@Override
	public List<Map> getDormitoryStudentList(Map map) {
		List<Map>list=getMaplist(map);
		if (list.size()==0){
			int re= getDormitoryStudent(map);
			if(re==-1){
				//宿舍不够
				eduDormitoryStudentDao.remove(map.get("classId")+"");
				return null;
			}
			list=getMaplist(map);
		}
		return list;
	}
	public List<Map> getMaplist(Map map) {
		List<EduDormitoryDO> dormitoryDOList=eduDormitoryDao.getClassDormitorylist(map);//班级的宿舍列表
		List<EduDormitoryStudentDO>eduDormitoryStudentDOList= eduDormitoryStudentDao.getClassDormitoryStudentlist(map);//宿舍对应的班级学员列表
		List treestotal = new ArrayList<>();
		List studentList =null;
		Map roomMap =null;
		for (EduDormitoryDO eduDormitoryDO : dormitoryDOList) {
			studentList = new ArrayList<>();
			roomMap=new HashedMap();
			for (EduDormitoryStudentDO eduDormitoryStudentDO : eduDormitoryStudentDOList) {
				if (eduDormitoryStudentDO.getDormitoryId().equals(eduDormitoryDO.getId()+"")) {
					studentList.add(eduDormitoryStudentDO);
				}
			}
			roomMap.put("roomMap",eduDormitoryDO);
			roomMap.put("studentList",studentList);
			treestotal.add(roomMap);
		}
		return treestotal;
	}
	@Transactional
	@Override
	public Boolean changeStudentDormitory(Map map) {
//{newId:newId,newRoomId:newRoomId,newNumber:newNumber,oldId:oldId,oldName:oldName,oldNumber:oldNumber,oldRoomId:oldRoomId}
		EduDormitoryStudentDO newEduDormitoryStudentDO=new EduDormitoryStudentDO();
		newEduDormitoryStudentDO.setStudentId(map.get("newId")+"");
		newEduDormitoryStudentDO.setDormitoryId(map.get("oldRoomId")+"");
		newEduDormitoryStudentDO.setNumber(map.get("oldNumber")+"");
		int a=eduDormitoryStudentDao.update(newEduDormitoryStudentDO);
		 newEduDormitoryStudentDO=new EduDormitoryStudentDO();
		newEduDormitoryStudentDO.setStudentId(map.get("oldId")+"");
		newEduDormitoryStudentDO.setDormitoryId(map.get("newRoomId")+"");
		newEduDormitoryStudentDO.setNumber(map.get("newNumber")+"");
		int b=eduDormitoryStudentDao.update(newEduDormitoryStudentDO);
		return true;
	}
	@Override
	public List<Map> setDormitoryStudentList(Map map) {
		//重置宿舍信息，先删除再插入
		int a =eduDormitoryStudentDao.remove(map.get("classId")+"");
		int re= getDormitoryStudent(map);
		if(re==-1){
			//宿舍不够
			eduDormitoryStudentDao.remove(map.get("classId")+"");
			return null;
		}
		List<Map>list=getMaplist(map);
		return list;
	}

	@Override
	public Map getMapStudentDormitory(Long studentId) {
		return eduDormitoryStudentDao.getMapStudentDormitory(studentId);
	}

	public synchronized int distributionDormitoryStudent(Map inmap) {
		//先查询男生
		List <Map>studentList = studentDao.getMaplist(inmap);
		int studentListSise=studentList.size();
		if(studentListSise==0){
			return 0;
		}
		int liveAmountSum=0;
		List <EduDormitoryStudentDO> dormitoryStudentDOList=new ArrayList<>();
		List <EduDormitoryDO> dormitoryDOList=eduDormitoryDao.getEmptylist(inmap);
		int number=0;
		for(int i=0;i<dormitoryDOList.size();i++) {
			if (studentList.size()==0){
				break;
			}
			EduDormitoryDO eduDormitoryDO=dormitoryDOList.get(i);//3
			int subEndindex=eduDormitoryDO.getLiveAmount();
			if (studentList.size()<subEndindex){
				subEndindex=studentList.size();
			}
			List<Map> list=  studentList.subList(0,subEndindex);
			for(Map map:list){
				EduDormitoryStudentDO eduDormitoryStudentDO=new EduDormitoryStudentDO();
				eduDormitoryStudentDO.setStudentId(map.get("id")+"");
				eduDormitoryStudentDO.setUserId(map.get("userId")+"");
				eduDormitoryStudentDO.setClassId(inmap.get("classId")+"");
				eduDormitoryStudentDO.setDormitoryId(eduDormitoryDO.getId()+"");
				number+=1;
				eduDormitoryStudentDO.setNumber(number+"");
				dormitoryStudentDOList.add(eduDormitoryStudentDO);
			}
			list.clear();
			liveAmountSum+=subEndindex;
		}
		if(studentListSise>liveAmountSum){
			return -1;
		}
		int a= eduDormitoryStudentDao.saveBatch(dormitoryStudentDOList);
		return a;
	}
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remainder = source.size() % n;  //(先计算出余数)
		int number = source.size() / n;  //然后是商
		int offset = 0;//偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remainder > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remainder--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			value=new ArrayList<T>(value);
			result.add(value);
		}
		return result;
	}

	public static <T> List<List<T>> averageAssignList(List<List<T>> source, int n) {
		List result = new ArrayList<List<List<T>>>();
		int remainder = source.size() % n;  //(先计算出余数)
		int number = source.size() / n;  //然后是商
		int offset = 0;//偏移量
		for (int i = 0; i < n; i++) {
			List<List<T>> value = null;
			if (remainder > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remainder--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			value=new ArrayList<List<T>>(value);
			result.add(value);
		}
		return result;
	}
}
