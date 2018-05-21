package com.slzr.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slzr.system.vo.AmountNumVO;
import com.slzr.system.vo.DayRegisterVO;
import com.slzr.system.vo.WeekDayVO;

@Mapper
public interface StatisticsDao {
	AmountNumVO GetTotalStatistics(@Param("code") String code);
	AmountNumVO GetTodayStatistics(@Param("code") String code);

	List<DayRegisterVO> GetWeekRegister(@Param("code") String code);
	
	List<WeekDayVO> GetWeekDay(@Param("code") String code);
	
	int GetTodayRegister(@Param("code") String code);
	int GetTotalRegister(@Param("code") String code);
}
