package com.slzr.operation.controller;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.*;
import com.slzr.operation.domain.DailysummaryTopupDO;
import com.slzr.operation.domain.ICTopupOrderDO;
import com.slzr.operation.domain.ICWXRefundDO;
import com.slzr.operation.service.DailysummaryTopupService;
import com.slzr.operation.service.ICTopupOrderService;
import com.slzr.operation.service.ICWXRefundService;
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


// IC 退款查询
@Controller
@RequestMapping("/operation/icwxrefund")
public class ICWXRefundController extends BaseController{
	private String prefix = "operation/icwxrefund";
//	@Autowired
//	private DailysummaryTopupService topupservice;

	@Autowired
	private ICWXRefundService icwxRefundService;
	
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
	
/*	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id) {
		return prefix + "/edit";
	}
	
	@GetMapping("/check/{id}")
	public String check(Model model,@PathVariable("id") String id) {
		model.addAttribute("id",id);
		return prefix + "/check";
	}*/
	
	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:topup:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		//查询列表数据
        Query query = new Query(params);
		List<ICWXRefundDO> list = icwxRefundService.list(query);
		int total = icwxRefundService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	
/*

	@ResponseBody
	@RequestMapping("api/again")
	@RequiresPermissions("operation:topup:do")
	public R apiagain(String id){
		icwxRefundService.updateAudit(id, -1, this.getId().intValue(),"");
		return R.ok();
	}*/


    @RequestMapping("/expexcel")
	@SystemControllerLog(description="4")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
    	
  	  	String fileName = "IC卡退款明细报表.xls";
  	  	
  	  	String[] title = new String[]{"卡号","订单号","退款单号","退款金额","退款时间","退款结果","退款人"};
  	  	
  	    List<ICWXRefundDO> list = null;
  	    
		try {
			list = icwxRefundService.list(params);
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        //卡号
		        if(null!=list.get(i).getCardNO() && !list.get(i).getCardNO().equals("")){
		        	str[0] = list.get(i).getCardNO();
		        }else{
		        	str[0] = "";
		        }
		        //订单号
		        if(null!=list.get(i).getOrderNO() && !list.get(i).getOrderNO().equals("")){
		        	str[1] = list.get(i).getOrderNO();
		        }else{
		        	str[1] = "";
		        }
		        //退款单号
		        if(null!=list.get(i).getRefundOrderNO()){
		        	str[2] = list.get(i).getRefundOrderNO();
		        }else{
		        	str[2] = "";
		        }
		        //退款金额
				 str[3] = ""+list.get(i).getRefundAmount();


				//退款时间
				if(null!=list.get(i).getRefundTime()){
					str[4] =  DateUtils.format(list.get(i).getRefundTime(),DateUtils.DATE_TIME_PATTERN)  ;

				}else {
					str[4] = "";
				}


				//退款结果
				if(null!=list.get(i).getRefundResult()){
					String refundResult  = list.get(i).getRefundResult();
					if(refundResult.equals("0"))
						str[5] = "退款失败";
					if(refundResult.equals("1"))
						str[5] = "退款成功";

				}else{
					str[5] = "";
				}


				//退款人
		        if(null!=list.get(i).getNickName()){
		        	str[6] = list.get(i).getNickName();
		        }else{
		        	str[6] = "";
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
