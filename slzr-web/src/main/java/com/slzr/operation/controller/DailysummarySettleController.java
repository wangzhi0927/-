package com.slzr.operation.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.operation.domain.DebitTransDO;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.UserDO;
import com.slzr.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.slzr.operation.service.DailysummarySettleService;


@Controller
@RequestMapping("/operation/settle")
public class DailysummarySettleController extends BaseController {
	private String prefix = "operation/settle";
	@Autowired
	private DailysummarySettleService settleservice;

	@Autowired
	private UserService userService;

	@Autowired
	private MerchService merchService;
	
	@GetMapping("/list")
	@SystemControllerLog(description="0")
	public String list(Model model) {
		HashMap<String,Object> params=new HashMap<>();
		UserDO userDO = userService.get(getId());
		List<MerchDo> merchantList=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			merchantList=merchService.list(params);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			merchantList=merchService.list(params);
		}
		DWMQDateVO dwmq = null;
		try {
			dwmq = DWMQDateUtil.GetDWMQDateVO(DateUtils.format(Calendar.getInstance().getTime(), DateUtils.DATE_PATTERN));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.addAttribute("dwmq", dwmq);
		model.addAttribute("merchantList",merchantList);
		return prefix + "/list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model,@PathVariable("id") String id) {
		DailysummarySettleDO obj = settleservice.get(id);
		model.addAttribute("model",obj);
		model.addAttribute("settleMethodText",ConvertToSettleMethodText(obj.getSettleMethod()));
		model.addAttribute("auditStatusText",ConvertToAuditStatusText(obj.getAuditStatus()));
		return prefix + "/detail";
	}
	
	@GetMapping("/check/{id}")
	public String check(Model model,@PathVariable("id") String id) {
		DailysummarySettleDO obj = settleservice.get(id);
		model.addAttribute("model",obj);
		return prefix + "/check";
	}
	
	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:settle:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		UserDO userDO = userService.get(getId());
		PageUtils pageUtil=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			//查询列表数据
			Query query = new Query(params);
			List<DailysummarySettleDO> list = settleservice.list(query);
			int total = settleservice.count(query);
			 pageUtil = new PageUtils(list, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			//查询列表数据
			Query query = new Query(params);
			List<DailysummarySettleDO> list = settleservice.list(query);
			int total = settleservice.count(query);
			 pageUtil = new PageUtils(list, total);
		}



		return pageUtil;
	}
	
	@ResponseBody
	@SystemControllerLog(description="9")
	@RequestMapping("api/check")
	@RequiresPermissions("operation:settle:check")
	public R apicheck(String id,int auditvalue,String remark,BigDecimal amount,Date transdate,String transno,int transstatus){
		if(auditvalue!=0&&auditvalue!=1)
		{
			return R.error("审核结果异常");
		}
		DailysummarySettleDO settledo = settleservice.get(id);
		if(settledo.getAuditStatus()==null || settledo.getAuditStatus()==-1)
		{
			settleservice.updateAudit(id, auditvalue, this.getId().intValue(),remark,amount,transdate,transno,transstatus);
			return R.ok();
		}else
		{
			return R.error("该记录已经审核过，不能重复审核");
		}
	}
    @RequestMapping("/expexcel")
	@SystemControllerLog(description="4")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
    	
  	  	String fileName = "商户结算报表.xls";
  	  	
  	  	String[] title = new String[]{"商户代码","商户简称","充值金额","充值笔数","消费金额","消费笔数","退款金额","退款笔数","服务费","结算方式","结算费率","结算金额","转账状态","审核状态","审核人","审核时间"};
  	    List<DailysummarySettleDO> list = null;
  	    
		try {
			list = settleservice.list(params);
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		    	//商户代码
		        if(null!=list.get(i).getMerchantCode() && !list.get(i).getMerchantCode().equals("")){
		        	str[0] = list.get(i).getMerchantCode();
		        }else{
		        	str[0] = "";
		        }
		    	//商户简称
		        if(null!=list.get(i).getMerchantName() && !list.get(i).getMerchantName().equals("")){
		        	str[1] = list.get(i).getMerchantName();
		        }else{
		        	str[1] = "";
		        }
		    	//充值金额
		        if(null!=list.get(i).getTopupAmount()){
		        	str[2] = list.get(i).getTopupAmount();
		        }else{
		        	str[2] = "";
		        }
		    	//充值笔数
		        if(null!=list.get(i).getTopupNum()){
		        	str[3] = list.get(i).getTopupNum();
		        }else{
		        	str[3] = "";
		        }
		    	//消费金额
		        if(null!=list.get(i).getDebitAmount()){
		        	str[4] = list.get(i).getDebitAmount();
		        }else{
		        	str[4] = "";
		        }
		    	//消费笔数
		        if(null!=list.get(i).getDebitNum()){
		        	str[5] = list.get(i).getDebitNum();
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
		        if(null!=list.get(i).getRefundServiceFee()){
		        	str[8] = list.get(i).getRefundServiceFee();
		        }else{
		        	str[8] = "";
		        }
		    	//结算方式
		        if(null!=list.get(i).getSettleMethod()){
		        	str[9] = ConvertToSettleMethodText(list.get(i).getSettleMethod());
		        }else{
		        	str[9] = "";
		        }
		    	//结算费率
		        if(null!=list.get(i).getSettleRate()){
		        	str[10] = list.get(i).getSettleRate();
		        }else{
		        	str[10] = "";
		        }
		    	//结算金额
		        if(null!=list.get(i).getSettleAmount()){
		        	str[11] = list.get(i).getSettleAmount();
		        }else{
		        	str[11] = "";
		        }
		    	//转账状态
		        if(null!=list.get(i).getTransferStatus()){
		        	str[12] = ConvertToTransStatusText(list.get(i).getTransferStatus());
		        }else{
		        	str[12] = "";
		        }
		    	//审核状态
		        if(null!=list.get(i).getAuditStatus()){
		        	str[13] = ConvertToAuditStatusText(list.get(i).getAuditStatus());
		        }else{
		        	str[13] = "";
		        }
		    	//审核人
		        if(null!=list.get(i).getAuditUserName()){
		        	str[14] = list.get(i).getAuditUserName();
		        }else{
		        	str[14] = "";
		        }
		    	//审核时间
		        if(null!=list.get(i).getAuditDateTime()){
		        	str[15] = DateUtils.format(list.get(i).getAuditDateTime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[15] = "";
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
	
	private String ConvertToSettleMethodText(String value)
	{
		switch(value)
		{
		   case "1":
				return "充值金额";
		   case "2":
				return "消费金额";
		    default:break;
		}
		return "未知";
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
	private String ConvertToTransStatusText(int value)
	{
		switch(value)
		{
		   case 0:
				return "未转账";
		   case 1:
				return "已转账";
		    default:break;
		}
		return "未知";
	}
}
