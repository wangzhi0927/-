package com.slzr.operation.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slzr.common.annotation.SystemControllerLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slzr.common.utils.DWMQDateUtil;
import com.slzr.common.utils.DWMQDateVO;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.operation.domain.DailysummaryTopupDO;
import com.slzr.operation.domain.TopupTransDO;
import com.slzr.operation.service.TopupTransService;

@RequestMapping("/operation/topupTrans")
@Controller
public class TopupTransController {
	private String prefix = "operation/topupTrans";
	@Autowired
	private TopupTransService topuptransservice;
	
	@GetMapping("/list")
	@SystemControllerLog(description="0")
	public String list(Model model) {
		DWMQDateVO dwmq = null;
		try {
			dwmq = DWMQDateUtil.GetDWMQDateVO(DateUtils.format(Calendar.getInstance().getTime(), DateUtils.DATE_PATTERN));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("dwmq", dwmq);
		return prefix + "/list";
	}
	
	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:topupTrans:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		//查询列表数据
        Query query = new Query(params);
		List<TopupTransDO> list = topuptransservice.list(query);
		int total = topuptransservice.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}

	@SystemControllerLog(description="4")
    @RequestMapping("/expexcel")
	public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
		String fileName = "交易充值报表.xls";
		
		String[] title = new String[]{"帐号","充值时间","充值金额","账号余额","赠送余额","充值方式","支付状态"};
		
		List<TopupTransDO> list = null;
		
			try {
				list = topuptransservice.list(params);
				List<Object[]> data = new ArrayList<Object[]>();
				for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
			        //帐号
			        if(null!=list.get(i).getAccountNO() && !list.get(i).getAccountNO().equals("")){
		        		str[0] = list.get(i).getAccountNO();
			        }else{
			        	str[0] = "";
			        }
			        //充值时间
			        if(null!=list.get(i).getTxnDateTime()){
		        		str[1] = DateUtils.format(list.get(i).getTxnDateTime(),DateUtils.DATE_TIME_PATTERN);
			        }else{
			        	str[1] = "";
			        }
			        //充值金额
			        if(null!=list.get(i).getTxnAmount()){
		        		str[2] = list.get(i).getTxnAmount();
			        }else{
			        	str[2] = "";
			        }
			        //账号余额
			        if(null!=list.get(i).getBalance()){
		        		str[3] = list.get(i).getBalance();
			        }else{
			        	str[3] = "";
			        }
					//赠送余额
					if(null!=list.get(i).getFreeAmount()){
						str[4] = list.get(i).getFreeAmount();
					}else{
						str[4] = "";
					}
			        //充值方式
			        if(null!=list.get(i).getPayMethodId()){
			        	str[5] = ConvertToPayMethodText(list.get(i).getPayMethodId());
			        }else{
			        	str[5] = "";
			        }
			        //支付状态
			        if(null!=list.get(i).getPayResult()){
		        		str[6] = ConvertToPayStatusText(list.get(i).getPayResult());
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
	private String ConvertToPayMethodText(int value)
	{
		switch(value)
		{
		   case 0:
				return "微信";
		   case 1:
				return "支付宝";
		    default:break;
		}
		return "未知";
	}
	private String ConvertToPayStatusText(int value)
	{
		switch(value)
		{
		   case 0:
				return "失败";
		   case 1:
				return "成功";
		    default:break;
		}
		return "未知";
	}
}
