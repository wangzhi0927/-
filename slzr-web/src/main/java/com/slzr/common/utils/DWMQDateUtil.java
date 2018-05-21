package com.slzr.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DWMQDateUtil {
	
	private final static String _w_tpl = "%s-W%s";
	private final static String _m_tpl = "%s-%s";
	private final static String _q_tpl = "%s-Q%s";
	private final static int _w_times = 54;//周
	private final static int _m_times = 12;//月
	private final static int _q_times = 12;//季

	public static DWMQDateVO GetDWMQDateVO(String datestr) throws ParseException{
		DWMQDateVO resultvo=new DWMQDateVO();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(datestr);
		Calendar ca_date = Calendar.getInstance();
		Calendar pev_week_date = Calendar.getInstance();
		ca_date.setTime(date);
		pev_week_date.setTime(date);
		pev_week_date.add(Calendar.DATE, -7);
		ca_date.setFirstDayOfWeek(Calendar.MONDAY);
		int currentMonth=ca_date.get(Calendar.MONTH)+1;
		resultvo.setEndDay(DateUtils.format(ca_date.getTime(), DateUtils.DATE_PATTERN));
		resultvo.setStartDay(DateUtils.format(pev_week_date.getTime(), DateUtils.DATE_PATTERN));
		resultvo.setEndW(String.format(_w_tpl,ca_date.get(Calendar.YEAR),ca_date.get(Calendar.WEEK_OF_YEAR)));
		resultvo.setEndM(String.format(_m_tpl,ca_date.get(Calendar.YEAR),AddZero(currentMonth)));
		resultvo.setEndQ(String.format(_q_tpl,ca_date.get(Calendar.YEAR),GetQByMonth(currentMonth)));
		
		//W
		Calendar w_date = Calendar.getInstance();
		w_date.setTime(date);
		w_date.setFirstDayOfWeek(Calendar.MONDAY);
		for(int i=_w_times;i>0;i--)
		{
			resultvo.getwValues().add(String.format(_w_tpl,w_date.get(Calendar.YEAR),w_date.get(Calendar.WEEK_OF_YEAR)));
			w_date.add(Calendar.DATE, -7);
		}
		resultvo.setStartW(resultvo.getwValues().get(resultvo.getwValues().size()-1));
		//M		
		Calendar m_date = Calendar.getInstance();
		m_date.setTime(date);
		for(int i=_m_times;i>0;i--)
		{
			resultvo.getmValues().add(String.format(_m_tpl,m_date.get(Calendar.YEAR),AddZero(m_date.get(Calendar.MONTH)+1)));
			m_date.add(Calendar.MONTH, -1);
		}
		resultvo.setStartM(resultvo.getmValues().get(resultvo.getmValues().size()-1));
		//Q
		Calendar q_date = Calendar.getInstance();
		q_date.setTime(date);
		for(int i=_q_times;i>0;i--)
		{
			resultvo.getqValues().add(String.format(_q_tpl,q_date.get(Calendar.YEAR),GetQByMonth(q_date.get(Calendar.MONTH)+1)));
			q_date.add(Calendar.MONTH, -3);
		}
		resultvo.setStartQ(resultvo.getqValues().get(resultvo.getqValues().size()-1));
		
		return resultvo;
	}
	
	private static int GetQByMonth(int month)
	{
		return (month-1)/3+1;
	}
	
	private static String AddZero(int month)
	{
		if(month<10)
			return "0"+month;
		else
			return month+"";
	}
}
