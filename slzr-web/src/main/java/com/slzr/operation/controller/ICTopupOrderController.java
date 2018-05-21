package com.slzr.operation.controller;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.*;
import com.slzr.operation.domain.DailysummaryTopupDO;
import com.slzr.operation.domain.ICTopupOrderDO;
import com.slzr.operation.service.DailysummaryTopupService;
import com.slzr.operation.service.ICTopupOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


// IC 卡充值明细
@Controller
@RequestMapping("/operation/ictopuporder")
public class ICTopupOrderController extends BaseController{
	private String prefix = "operation/ictopuporder";
//	@Autowired
//	private DailysummaryTopupService topupservice;

	@Autowired
	private ICTopupOrderService iCTopupOrderService;
	
	@GetMapping("/list")
	@SystemControllerLog(description="0")
	public String list(ModelMap model) {

		DWMQDateVO dwmq = null;
		try {
			dwmq = DWMQDateUtil.GetDWMQDateVO(DateUtils.format(Calendar.getInstance().getTime(), DateUtils.DATE_PATTERN));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("dwmq", dwmq);

		return prefix + "/list";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id) {
		return prefix + "/edit";
	}
	
	@GetMapping("/check/{id}")
	public String check(Model model,@PathVariable("id") String id) {
		model.addAttribute("id",id);
		return prefix + "/check";
	}



	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:topup:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		//查询列表数据
        Query query = new Query(params);
		List<ICTopupOrderDO> list = iCTopupOrderService.list(query);
		int total = iCTopupOrderService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}




	@SystemControllerLog(description="4")
    @RequestMapping("/expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
    	
  	  	String fileName = "IC卡充值明细报表.xls";

  	  	String[] title = new String[]{"IC卡号","充值时间","充值金额","账号余额","充值方式","充值结果"};
  	  	
  	    List<ICTopupOrderDO> list = null;
  	    
		try {
			list = iCTopupOrderService.list(params);
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        //IC卡号
		        if(null!=list.get(i).getCardNO() && !list.get(i).getCardNO().equals("")){
		        	str[0] = list.get(i).getCardNO();
		        }else{
		        	str[0] = "";
		        }

		        //充值时间
		        if(null!=list.get(i).getTxnDateTime() && !list.get(i).getTxnDateTime().equals("")){
		        	str[1] =  DateUtils.format(list.get(i).getTxnDateTime(),DateUtils.DATE_TIME_PATTERN)  ;
		        }else{
		        	str[1] = "";
		        }

				//充值金额
				str[2] = String.format("%.2f", list.get(i).getTxnAmount()) ;



				//账号余额
				str[3] =String.format("%.2f", list.get(i).getBalance()) ;

		        //充值方式
		        if(null!=list.get(i).getPayMethodId()){
		        	String payMethodId = list.get(i).getPayMethodId();
		        	str[4] = list.get(i).getTxnDateTime();
					if(payMethodId.equals("0"))
						str[4] = "微信支付";
					if(payMethodId.equals("1"))
						str[4] = "支付宝支付";
				} else{
		        	str[4] = "";
		        }

				//充值结果
				if(null!=list.get(i).getPayResult()){
					String payResult = list.get(i).getPayResult();
					if(payResult.equals("0"))
						str[5] = "支付失败";
					if(payResult.equals("1"))
						str[5] = "支付成功";
				} else{
					str[5] = "";
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

}
