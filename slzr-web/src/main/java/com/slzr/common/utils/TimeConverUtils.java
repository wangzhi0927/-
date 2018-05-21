package com.slzr.common.utils;

import org.apache.poi.ss.formula.functions.T;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeConverUtils {
    public static void main(String[] args) {
       // System.err.print(getLastDayOfWeek("2018", "13"));
       // System.err.print(getFirstDayOfWeek("2018", "13"));
        //System.err.print(getLastDayOfMonth("2018", "12"));
       // System.err.print(getFirstDayOfMonth("2018", "03"));
        //System.err.print(getFirstDayOfQuarter("2018", "2"));
        //System.err.print(getLastDayOfQuarter("2018", "2"));
    }
    //获取截止周的最后一天
    public static String getLastDayOfWeek(String year, String week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, Integer.parseInt(week) * 7);

        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 0:00:00");
        String format = sdf.format(date);
        return format;
    }

    //获取开始周的第一天
    public static String getFirstDayOfWeek(String year, String week) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        //设置周
        cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
        //设置该周第一天为星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        //设置最后一天是星期日
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6); // Sunday
        //格式化日期
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 6);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  0:00:00");
        String lastDayOfWeek = sdf.format(cal.getTime());
        return lastDayOfWeek;

    }

    //获取某月的第一天
    public static String getFirstDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        String format = new SimpleDateFormat("yyyy-MM-dd  0:00:00").format(cal.getTime());
        return  format;
    }
    //获取某年某月的最后一天
    public static String getLastDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        //设置月份
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) +1);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 0:00:00");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    //返回某年 某季度的第一天
    public static String getFirstDayOfQuarter(String year, String quarter) {
        String month = "";
        String firstDayOfMonth="";
        if (Integer.parseInt(quarter) == 1) {
               month = "1" ;
             firstDayOfMonth = getFirstDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 2) {
            month ="4";
             firstDayOfMonth = getFirstDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 3) {
            month = "7";
             firstDayOfMonth = getFirstDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 4) {
            month = "10";
             firstDayOfMonth = getFirstDayOfMonth(year, month);
        }

        return firstDayOfMonth;
    }

    //返回 某年某季度的最后一天
    public static String getLastDayOfQuarter(String year, String quarter) {
        String month = "";
        String lastDayOfMonth="";
        if (Integer.parseInt(quarter) == 1) {
            month = "3" ;
            lastDayOfMonth = getLastDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 2) {
            month ="6";
            lastDayOfMonth = getLastDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 3) {
            month = "9";
            lastDayOfMonth = getLastDayOfMonth(year, month);
        } else if (Integer.parseInt(quarter) == 4) {
            month = "12";
            lastDayOfMonth = getLastDayOfMonth(year, month);
        }
        return lastDayOfMonth;
    }
    //将得到的参数的paramers进行转换
    public  static Map<String,Object>  converDate(Map<String, Object> parameters){
        String dataType="";
        String startDate="";
        String endDate="";
        if(parameters.containsKey("datetype")){
        	dataType = parameters.get("datetype").toString();
        }
        if(parameters.containsKey("startDate")){  
        	startDate = parameters.get("startDate").toString();
        }
        if(parameters.containsKey("endDate")){  
        	endDate = parameters.get("endDate").toString();
        }
        if(dataType.equals("D")){
            parameters.put("datetype","Day");
        }
        
        if (dataType.equals("W")) {
            parameters.put("datetype","Week");
            String[] split1 = startDate.split("-");
            String year1=split1[0];
            String week1=split1[1].replaceAll("W","");
            String firstDayOfWeek = TimeConverUtils.getFirstDayOfWeek(year1, week1);
            parameters.put("startDate",firstDayOfWeek);

            String[] split2 = endDate.split("-");
            String year2=split2[0];
            String week2=split2[1].replaceAll("W","");
            String lastDayOfWeek = TimeConverUtils.getLastDayOfWeek(year2, week2);
            parameters.put("endDate",lastDayOfWeek);
        }
        if (dataType.equals("M")) {
            parameters.put("datetype","Month");
            String[] split1 = startDate.split("-");
            String year1=split1[0];
            String month1=split1[1];
            String firstDayOfMonth = TimeConverUtils.getFirstDayOfMonth(year1, month1);
            parameters.put("startDate",firstDayOfMonth);

            String[] split2 = endDate.split("-");
            String year2=split2[0];
            String month12=split2[1];
            String lastDayOfMonth = TimeConverUtils.getLastDayOfMonth(year2, month12);
            parameters.put("endDate",lastDayOfMonth);
        }
        if (dataType.equals("Q")) {
            parameters.put("datetype","Quarter");
            String[] split1 = startDate.split("-");
            String year1=split1[0];
            String quarter1=split1[1].replaceAll("Q","");
            String firstDayOfQuarter = TimeConverUtils.getFirstDayOfQuarter(year1, quarter1);
            parameters.put("startDate",firstDayOfQuarter);

            String[] split2 = endDate.split("-");
            String year2=split2[0];
            String quarter2=split2[1].replaceAll("Q","");
            String lastDayOfQuarter = TimeConverUtils.getLastDayOfQuarter(year2, quarter2);
            parameters.put("endDate",lastDayOfQuarter);
        }

        return  parameters;
    }
}
