package com.bootdo.edu.dao;

import com.bootdo.edu.domain.SeatTab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchSeatDao {
	
	List<SeatTab> searchSeatList(@Param("gradeId") String gradeId, @Param("classId") String classId);
	
	void delSeatList(@Param("gradeId") String gradeId, @Param("classId") String classId);
	List<Map> serchStudtList(@Param("classId") String classId);
	public int insert(SeatTab seatTab);
}
