package com.slzr.operation.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slzr.common.annotation.SystemControllerLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.slzr.account.domain.AccountCancelDO;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.DWMQDateUtil;
import com.slzr.common.utils.DWMQDateVO;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.operation.domain.DailysummarySettleDO;
import com.slzr.operation.domain.DailysummaryTopupDO;
import com.slzr.operation.service.DailysummaryTopupService;


@Controller
@RequestMapping("/operation/topup")
public class DailysummaryTopupController extends BaseController{
	private String prefix = "operation/topup";
	@Autowired
	private DailysummaryTopupService topupservice;
	
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
		List<DailysummaryTopupDO> list = topupservice.list(query);
		for(DailysummaryTopupDO objdo : list) {
			objdo.setEnterAmount(objdo.getTopupAmount().multiply(new BigDecimal(1-0.001)));
			objdo.setSummaryDateTimeText(DateUtils.format(objdo.getSummaryDateTime(), DateUtils.DATE_PATTERN));
		}
		int total = topupservice.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	
	@ResponseBody
	@SystemControllerLog(description="9")
	@RequestMapping("api/check")
	@RequiresPermissions("operation:topup:check")
	public R apicheck(Integer id,int auditvalue,String remark){
		if(auditvalue!=0 && auditvalue!=1)
		{
			return R.error("审核结果选择不正确");
		}
		if(StringUtils.isNullOrEmpty(remark))
		{
			return R.error("审核描述必须填写");
		}
		topupservice.updateAudit(id, auditvalue, this.getId().intValue(),remark);
		return R.ok();
	}
	
	@ResponseBody
	@SystemControllerLog(description="10")
	@RequestMapping("api/again")
	@RequiresPermissions("operation:topup:do")
	@Transactional
	public R apiagain(int id){
		DailysummaryTopupDO settledo = topupservice.get(id);
		String msg = topupservice.againDaily(settledo.getTxnDate());
		if(msg.equals("OK")) 
		{
			return R.ok();
		}else
		{
			return R.error();
		}
	}
	@SystemControllerLog(description="4")
    @RequestMapping("/expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
    	
  	  	String fileName = "充值日结报表.xls";
  	  	
  	  	String[] title = new String[]{"日结日期","交易日期","充值方式","充值金额","入账金额","充值笔数","退款金额","退款笔数","服务费","审核状态","审核人","审核时间","审核说明"};
  	  	
  	    List<DailysummaryTopupDO> list = null;
  	    
		try {
			list = topupservice.list(params);
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        //日结日期
		        if(null!=list.get(i).getSummaryDateTime()){
		        	str[0] = DateUtils.format(list.get(i).getSummaryDateTime(),DateUtils.DATE_PATTERN);
		        }else{
		        	str[0] = "";
		        }
		        //交易日期
		        if(null!=list.get(i).getTxnDate() && !list.get(i).getTxnDate().equals("")){
		        	str[1] = list.get(i).getTxnDate();
		        }else{
		        	str[1] = "";
		        }
		        //充值方式
		        if(null!=list.get(i).getPayMethodId() && !list.get(i).getPayMethodId().equals("")){
		        	str[2] = ConvertToPayMethodText(list.get(i).getPayMethodId());
		        }else{
		        	str[2] = "";
		        }
		        //充值金额
		        if(null!=list.get(i).getTopupAmount()){
		        	str[3] = list.get(i).getTopupAmount();
		        }else{
		        	str[3] = "";
		        }
		        //入账金额
		        if(null!=list.get(i).getTopupAmount()){
		        	str[4] = list.get(i).getTopupAmount().multiply(new BigDecimal(1-0.001)).setScale(2,BigDecimal.ROUND_HALF_UP);
		        }else{
		        	str[4] = "";
		        }
		        //充值笔数
		        if(null!=list.get(i).getTopupNum()){
		        	str[5] = list.get(i).getTopupNum();
		        }else{
		        	str[5] = "";
		        }
		        //退款金额
		        if(null!=list.get(i).getRefundAmount()){
		        	str[6] = list.get(i).getRefundAmount();
		        }else{
		        	str[6] = "";
		        }
		        //退款笔数
		        if(null!=list.get(i).getRefundNum()){
		        	str[7] = list.get(i).getRefundNum();
		        }else{
		        	str[7] = "";
		        }
		        //服务费
		        if(null!=list.get(i).getServiceFee()){
		        	str[8] = list.get(i).getServiceFee();
		        }else{
		        	str[8] = "";
		        }
		        //审核状态
		        if(null!=list.get(i).getAuditStatus()){
		        	str[9] = ConvertToAuditStatusText(list.get(i).getAuditStatus());
		        }else{
		        	str[9] = "";
		        }
		        //审核人
		        if(null!=list.get(i).getAuditUserName()){
		        	str[10] = list.get(i).getAuditUserName();
		        }else{
		        	str[10] = "";
		        }
		        //审核时间
		        if(null!=list.get(i).getAuditDateTime()){
		        	str[11] = DateUtils.format(list.get(i).getAuditDateTime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[11] = "";
		        }
		        //审核说明
		        if(null!=list.get(i).getAuditRemark()){
		        	str[12] = list.get(i).getAuditRemark();
		        }else{
		        	str[12] = "";
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
	private String ConvertToAuditStatusText(int value)
	{
		switch(value)
		{
		   case -1:
				return "未审核";
		   case 0:
				return "审核不通过";
		   case 1:
				return "审核通过";
		    default:break;
		}
		return "未知";
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
}
