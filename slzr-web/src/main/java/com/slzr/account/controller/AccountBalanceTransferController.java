package com.slzr.account.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slzr.account.domain.AccountBalanceTransferDO;
import com.slzr.account.service.AccountBalanceTransferService;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.config.BootdoConfig;
import com.slzr.common.controller.BaseController;
import com.slzr.common.domain.SLRequest;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.HttpUtil;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.common.utils.SignatureUtil;
import com.slzr.common.utils.YXDate;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.UserDO;
import com.slzr.system.service.UserService;

/**
 * 转赠
 * @author lc
 * @date 2018-05-14 16:45:51
 *
 */
 
@Controller
@RequestMapping("/account/accountBalanceTransfer")
public class AccountBalanceTransferController extends BaseController{
	@Autowired
	private AccountBalanceTransferService accountbalancetransferService;
	@Autowired
	private  UserService userService;
	@Autowired
	private MerchService merchService;
	@Autowired
	BootdoConfig bootdoConfig;
	
	@GetMapping()
	@RequiresPermissions("account:accountBalanceTransfer:accountBalanceTransfer")
	String Accountbalancetransfer(Model model){
		HashMap<String,Object> params=new HashMap<>();
		UserDO userDO = userService.get(getId());
		List<MerchDo> merchantList=null;
		if(userDO.getMerchantCode()==null||userDO.getMerchantCode().equals("")){
			 merchantList=merchService.list(params);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			merchantList=merchService.list(params);
		}
		model.addAttribute("merchantList",merchantList);
	    return "account/accountBalanceTransfer/accountBalanceTransfer";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("account:accountBalanceTransfer:accountBalanceTransfer")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AccountBalanceTransferDO> accountbalancetransferList = accountbalancetransferService.list(query);
		int total = accountbalancetransferService.count(query);
		PageUtils pageUtils = new PageUtils(accountbalancetransferList, total);
		return pageUtils;
	}
	
	@RequiresPermissions("account:accountBalanceTransfer:deal")
	@GetMapping("/deal/{id}")	
	String deal(@PathVariable("id") Integer id,Model model){
		
		
		AccountBalanceTransferDO acountBalanceTransferDO = accountbalancetransferService.get(id);
		if(acountBalanceTransferDO!=null){
			if(acountBalanceTransferDO.getAccountBalance()==null){
				acountBalanceTransferDO.setAccountBalance(new BigDecimal(0));
			}
			if(acountBalanceTransferDO.getServiceFee()==null){
				acountBalanceTransferDO.setServiceFee(new BigDecimal(2));
			}
			model.addAttribute("accountbalancetransfer", acountBalanceTransferDO);
		    return "account/accountBalanceTransfer/deal";
		}else{
			return "account/accountBalanceTransfer/error";
		}

	}
	
	

	/**
	 * 审核
	 */
	@RequiresPermissions("account:accountBalanceTransfer:deal")
	@RequestMapping("/update")	
	@ResponseBody
	public R update( AccountBalanceTransferDO accountBalanceTransferDO){
		accountBalanceTransferDO.setAuditDateTime(new Date());
		//accountBalanceTransferDO.setApplyUid(getId()); 
		accountBalanceTransferDO.setAuditUid(getId());

		String UserName = "zhy";
		String Password = "8A4A41D59AA8EB8DB189EDE440B25D0A";
		String url =bootdoConfig.getInterfaceUrl()+"api/sl/GetToken";  //签名接口
		String AppKey= "A0E6C376000F44AEF13F58614C379DD9";

		
		SLRequest slrequest = new SLRequest();
		Map<String, Object> Data = new HashMap<String, Object>();
		Data.put("UserName", UserName);
		Data.put("Password", Password);
		
		slrequest.setAppId("slzrop");
		slrequest.setAppType("OP");
		slrequest.setData(Data);
		slrequest.setTimeStamp(YXDate.getTime());
		slrequest.setNonce("62A0E888F5033BC65BF8D78622277"+new java.util.Random().nextInt(100));
		slrequest.setUrl(url);
		
		slrequest.setSignType("MD5");
		slrequest.setSignVersion("1.0");
		

        Map<String,String> paraMap = new HashMap<String,String>();  
        paraMap.put("AppId",slrequest.getAppId());  
        paraMap.put("AppType", slrequest.getAppType());  
        paraMap.put("Data", JSON.toJSONString(slrequest.getData()));  
        paraMap.put("Nonce",slrequest.getNonce()); 
        paraMap.put("TimeStamp",slrequest.getTimeStamp()); 
        paraMap.put("Url",slrequest.getUrl()); 
        paraMap.put("AppKey",AppKey); 
        
        String token = null;
		try {
			String signature =SignatureUtil.formatUrlMap(paraMap, false, false); 
			
			slrequest.setSignature(signature);
			
			String map = JSONObject.toJSONString(slrequest);		
					
			String msg= HttpUtil.sendPost(url,map,"");
			
			JSONObject jsonObject = JSONObject.parseObject(msg);
			JSONObject jsonobj=(JSONObject) jsonObject.get("Data");
			token = (String) jsonobj.get("Token");
		} catch (Exception e1) {
			System.out.println("获取token签名失败"+e1);
			e1.printStackTrace();
			return R.error("获取token签名失败");
		}
		System.out.println("token："+token);
		
		
		
		
		String msgurl = bootdoConfig.getInterfaceUrl()+"/api/pc/AccountBalanceTransfer/edit";
		SLRequest slrequest2 = new SLRequest();
		 
		slrequest2.setAppId("slzrop");
		slrequest2.setAppType("OP");

		slrequest2.setTimeStamp(YXDate.getTime());
		slrequest2.setNonce("62A0E888F5033BC65BF8D78622277C8E"+new java.util.Random().nextInt(100));
		slrequest2.setUrl(msgurl);
		slrequest2.setData(accountBalanceTransferDO);
		
		try {
			String slrequestMap = JSONObject.toJSONString(slrequest2);	
			System.out.println(slrequestMap);
			String msg2= HttpUtil.sendPost(msgurl,slrequestMap,token);
			System.out.println(msg2);
		} catch (Exception e) {	
			System.out.println("发送失败"+e);
			e.printStackTrace();
			return R.error("操作失败");
		} 
		return R.ok("操作成功");
	}
	

    
	/**
     * 导出
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/expexcel")
	@SystemControllerLog(description="4")
    @RequiresPermissions("account:accountBalanceTransfer:expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response){
    	   	
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
    	parameterMap.put("searchName", request.getParameter("searchName"));
    	parameterMap.put("auditResult", request.getParameter("auditResult"));
    	parameterMap.put("merchantCode", request.getParameter("merchantCode"));
    	
  	  	String fileName = "转赠列表.xls";
  	  	
  	  	String[] title = new String[]{"转赠账户","受赠账户","申请人","申请时间","账户余额","转赠金额","服务费",
  	  			"审核人","审核状态","审核时间","审核描述","确认结果","确认时间"};
  	  	
  	    List<AccountBalanceTransferDO> list = null;
  	     	    
		try {
			list = accountbalancetransferService.list(parameterMap);		    	 	    	  		    	  		
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        if(null!=list.get(i).getFromAccountNo() && !list.get(i).getFromAccountNo().equals("")){
		        	str[0]=list.get(i).getFromAccountNo().toString();
		        }else{
		        	str[0]="";
		        }
		        if(null!=list.get(i).getToAccountNo()&& !list.get(i).getToAccountNo().equals("")){
		        	str[1]=list.get(i).getToAccountNo().toString();
		        }else{
		        	str[1]="";
		        }
		        if(null!=list.get(i).getApplyNickName()&& !list.get(i).getApplyNickName().equals("")){
		        	str[2]=list.get(i).getApplyNickName().toString();
		        }else{
		        	str[2]="";
		        }
		        if(null!=list.get(i).getApplyTime() && !list.get(i).getApplyTime().equals("")){
		        	str[3]=DateUtils.format(list.get(i).getApplyTime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[3]="";
		        }
		        if(null!=list.get(i).getAccountBalance() && !list.get(i).getAccountBalance().equals("")){
		        	str[4]=list.get(i).getAccountBalance().toString();
		        }else{
		        	str[4]="";
		        }
		        if(null!=list.get(i).getTransferAmount() && !list.get(i).getTransferAmount().equals("")){
		        	str[5]=list.get(i).getTransferAmount().toString();
		        }else{
		        	str[5]="";
		        }
		        if(null!=list.get(i).getServiceFee() && !list.get(i).getServiceFee().equals("")){
		        	str[6]=list.get(i).getServiceFee().toString();
		        }else{
		        	str[6]="";
		        }
		        
		        if(null!=list.get(i).getAuditUid() && !list.get(i).getAuditUid().equals("")){
		        	str[7]=list.get(i).getAuditUid().toString();
		        }else{
		        	str[7]="";
		        }
		        if(null!=list.get(i).getAuditResult() && !list.get(i).getAuditResult().equals("")){
		        	if(list.get(i).getAuditResult().toString().equals("0")){
		        		str[8]="审核不通过";
		        	}else if(list.get(i).getAuditResult().toString().equals("1")){
		        		str[8]="审核通过";
		        	}else if(list.get(i).getAuditResult().toString().equals("-1")){
		        		str[8]="未审核";
		        	}
		        }else{
		        	str[8]="";
		        }
		        if(null!=list.get(i).getAuditDateTime() && !list.get(i).getAuditDateTime().equals("")){
		        	str[9]=DateUtils.format(list.get(i).getAuditDateTime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[9]="";
		        }
		        
		        if(null!=list.get(i).getAuditMsg() && !list.get(i).getAuditMsg().equals("")){
		        	str[10]=list.get(i).getAuditMsg().toString();
		        }else{
		        	str[10]="";
		        }
		        
		        if(null!=list.get(i).getUserConfirmResult() && !list.get(i).getUserConfirmResult().equals("")){
		        	if(list.get(i).getUserConfirmResult().toString().equals("1")){
		        		str[11]="已确认";
		        	}
		        }else{
		        	str[11]="";
		        }
		        
		        if(null!=list.get(i).getUserConfirmDateTime() && !list.get(i).getUserConfirmDateTime().equals("")){
		        	str[12]=DateUtils.format(list.get(i).getUserConfirmDateTime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[12]="";
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
