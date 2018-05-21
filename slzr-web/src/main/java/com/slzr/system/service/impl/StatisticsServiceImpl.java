package com.slzr.system.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slzr.system.dao.StatisticsDao;
import com.slzr.system.service.StatisticsService;
import com.slzr.system.vo.StatisticsVO;

@Service
public class StatisticsServiceImpl implements StatisticsService {
	@Autowired
	private StatisticsDao statisticmapper;
	
	@Override
	public StatisticsVO GetStatisticsVO(String code) {
		StatisticsVO vo=new StatisticsVO();
		vo.setTodayStatistics(statisticmapper.GetTodayStatistics(code));
		vo.setTotalStatistics(statisticmapper.GetTotalStatistics(code));
		vo.setWeekDayRegister(statisticmapper.GetWeekRegister(code));
		vo.setWeekDay(statisticmapper.GetWeekDay(code));
		vo.setTodayRegister(statisticmapper.GetTodayRegister(code));
		vo.setTotalRegister(statisticmapper.GetTotalRegister(code));
		
		return vo;
	}
}
