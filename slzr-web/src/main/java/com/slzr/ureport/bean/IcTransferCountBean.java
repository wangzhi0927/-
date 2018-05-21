package com.slzr.ureport.bean;


import java.util.List;
import java.util.Map;

import org.apache.batik.anim.timing.TimeContainer;
import org.springframework.beans.factory.annotation.Autowired;

import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.TimeConverUtils;
import com.slzr.ureport.domain.ACountDo;
import com.slzr.ureport.domain.IcTransferCountDo;
import com.slzr.ureport.service.IcTransferCountService;

/**
 * IC卡圈存统计
 * @author Administrator
 *
 */
public class IcTransferCountBean extends BaseController{
	@Autowired
	private IcTransferCountService icTransferCountService;
	
	
    public List<IcTransferCountDo> loadReportData(String dsName,String datasetName,Map<String,Object> parameters){
		String startDate = (String) parameters.get("startDate");
		String endDate = (String) parameters.get("endDate");
        Map<String,Object> mobj = TimeConverUtils.converDate(parameters);
        
    	List<IcTransferCountDo> list = icTransferCountService.list(mobj);
    	 for (int i = 0; i < list.size(); i++) {
			 //判断日期类型为什么
			 if(mobj.get("datetype").toString().equals("Week")){
				 list.get(i).setSummarydatetime(list.get(i).getYear()+"-W"+list.get(i).getSummarydatetime());
			 }
			 if(mobj.get("datetype").toString().equals("Month")){
				 list.get(i).setSummarydatetime(list.get(i).getYear()+"-"+list.get(i).getSummarydatetime());
			 }
			 if(mobj.get("datetype").toString().equals("Quarter")){
				 list.get(i).setSummarydatetime(list.get(i).getYear()+"-Q"+list.get(i).getSummarydatetime());
			 }
    		 list.get(i).setUserName(getUserName());
			 list.get(i).setStartDate(startDate);
			 list.get(i).setEndDate(endDate);
    	 }

  
        return list;
    }
}
