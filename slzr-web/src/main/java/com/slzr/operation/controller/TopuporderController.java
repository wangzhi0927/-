package com.slzr.operation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slzr.common.annotation.SystemControllerLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slzr.account.domain.AccountDO;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.operation.domain.TopuporderDO;
import com.slzr.operation.service.TopuporderService;

/**
 * 订单 圈存异常
 * @author lc
 * @date 2018-03-20 11:15:51
 */
 
@Controller
@RequestMapping("/operation/icQuancunfail")
public class TopuporderController extends BaseController{
	@Autowired
	private TopuporderService topuporderService;
	
	@GetMapping()
	@SystemControllerLog(description="0")
	@RequiresPermissions("operation:icQuancunfail:list")
	String Topuporder(){
	    return "operation/icQuancunfail/list";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("operation:icQuancunfail:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<TopuporderDO> topuporderList = topuporderService.list(query);
		int total = topuporderService.count(query);
		PageUtils pageUtils = new PageUtils(topuporderList, total);
		return pageUtils;
	}
	


	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id,Model model){
		TopuporderDO topuporder = topuporderService.get(id);
		model.addAttribute("topuporder", topuporder);
		model.addAttribute("id", id);
	    return "operation/icQuancunfail/edit";
	}
	



	/**
     * 导出
     * @param model
     * @param request
     * @param response
     */
	@SystemControllerLog(description="4")
	@RequestMapping("/expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response){
    	   	
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
    	parameterMap.put("searchName", request.getParameter("searchName"));
    	
  	  	String fileName = "圈存异常.xls";
  	  	
  	  	String[] title = new String[]{"卡号","订单号","交易金额","余额","状态","描述","交易时间","自助终端编号"};
  	  	
  	    List<TopuporderDO> list = null;
  	     	    
		try {
			list = topuporderService.list(parameterMap);		    	 	    	  		    	  		
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        if(null!=list.get(i).getCardno() && !list.get(i).getCardno().equals("")){
		        	str[0]=list.get(i).getCardno().toString();
		        }else{
		        	str[0]="";
		        }
		        if(null!=list.get(i).getOrderno()&& !list.get(i).getOrderno().equals("")){
		        	str[1]=list.get(i).getOrderno().toString();
		        }else{
		        	str[1]="";
		        }
		        if(null!=list.get(i).getTxnamount()&& !list.get(i).getTxnamount().equals("")){
		        	str[2]=list.get(i).getTxnamount().toString();
		        }else{
		        	str[2]="";
		        }
		        if(null!=list.get(i).getBalance() && !list.get(i).getBalance().equals("")){
		        	str[3]=list.get(i).getBalance().toString();
		        }else{
		        	str[3]="";
		        }
		        
		        if(null!=list.get(i).getLoadstate() && !list.get(i).getLoadstate().equals("")){
		        	str[4]=list.get(i).getLoadstate().toString();
		        	
		        }else{
		        	str[4]="";
		        }
		        if(null!=list.get(i).getLoaddescribe() && !list.get(i).getLoaddescribe().equals("")){
		        	str[5]=list.get(i).getLoaddescribe().toString();
		        }else{
		        	str[5]="";
		        }
		        if(null!=list.get(i).getLoadbackdate() && !list.get(i).getLoadbackdate().equals("")){
		        	str[6]=DateUtils.format(list.get(i).getLoadbackdate(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[6]="";
		        }
		        if(null!=list.get(i).getTerminalno() && !list.get(i).getTerminalno().equals("")){
		        	str[7]=list.get(i).getTerminalno().toString();
		        }else{
		        	str[7]="";
		        }
		       
			        		        
		        data.add(str);
	    	}
	    	String sheetName = "expExcel";
			byte[] b = fileName.getBytes("UTF-8"); 
		    String formatFileName = new String(b,"ISO-8859-1");		      	 
		    response.setContentType("application/ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+formatFileName);	
		    ExcelExportTool.exportExcel(formatFileName, sheetName, title, data, response);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }finally{
			 list=null;
		 }   	
    }
    
    /**
     * 审核 操作
     * @param id
     * @return
     */
	@PostMapping("/operation")
	@SystemControllerLog(description="9")
	@ResponseBody
	R operation(String id,String operationType) {
		TopuporderDO topuporder = new TopuporderDO();
		topuporder.setId(Integer.parseInt(id));
		topuporder.setReviewid(getId().intValue());
		topuporder.setReviewtime(new Date());
		if(operationType.equals("0")){ //不通过
			topuporder.setOrderstate(2);
			
			if (topuporderService.update(topuporder) > 0){
				return R.ok();
			}else{return R.error();}
			
		}else{ //通过
			topuporder.setOrderstate(6);
			
			if (topuporderService.update(topuporder) > 0){
				return R.ok();
			}else{return R.error();}
		}
		
	}
	
	
	
}
