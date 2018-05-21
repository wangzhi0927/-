package com.slzr.account.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.config.BootdoConfig;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.UserDO;
import com.slzr.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slzr.account.domain.AccountCancelDO;
import com.slzr.account.service.AccountCancelService;
import com.slzr.account.service.AccountService;
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

/**
 * 账户注销管理
 * @author lc
 * @date 2018-03-08 16:45:51
 */
 
@Controller
@RequestMapping("/account/accountCancel")
public class AccountCancelController extends BaseController{
	@Autowired
	private AccountCancelService accountCancelService;
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private  UserService userService;

	@Autowired
	private MerchService merchService;

	@Autowired
	BootdoConfig bootdoConfig;
	
	@GetMapping()
	@SystemControllerLog(description="0")
	@RequiresPermissions("account:accountCancel:accountCancel")
	String AccountCancel(Model model){
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
	    return "account/accountCancel/accountCancel";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("account:accountCancel:accountCancel")
	public PageUtils list(@RequestParam Map<String, Object> params){
		if(params.get("isday").equals("true")){
			params.put("isday", 7);
		}else{
			params.put("isday", null);
		}
		UserDO userDO = userService.get(getId());
		PageUtils pageUtil=null;
		if(userDO.getMerchantCode()==null||userDO.getMerchantCode().equals("")){
			Query query = new Query(params);
			List<AccountCancelDO> sysUserList = accountCancelService.list(query);
			int total = accountCancelService.count(query);
			pageUtil = new PageUtils(sysUserList, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			Query query = new Query(params);
			List<AccountCancelDO> sysUserList=accountCancelService.list(query);
			int total = accountCancelService.count(query);
			pageUtil = new PageUtils(sysUserList, total);
		}
		return pageUtil;
	}
	

	@RequiresPermissions("account:accountCancel:deal")
	@GetMapping("/deal/{id}")	
	String deal(@PathVariable("id") Integer id,Model model){
		AccountCancelDO accountCancel = accountCancelService.get(id);
		if(accountCancel!=null){
			if(accountCancel.getAbalance()==null){
				accountCancel.setAbalance(new BigDecimal(0));
			}
			if(accountCancel.getServicefee()==null || accountCancel.getServicefee().compareTo(BigDecimal.ZERO)==0){
				accountCancel.setServicefee(new BigDecimal(2));
			}
			model.addAttribute("accountCancel", accountCancel);
		    return "account/accountCancel/deal";
		}else{
			return "account/accountCancel/error";
		}
	}
	

	/**
	 * 审核
	 */
	@RequiresPermissions("account:accountCancel:deal")
	@SystemControllerLog(description="9")
	@RequestMapping("/update")	
	@ResponseBody
	public R update( AccountCancelDO accountCancel){
		
		if(accountService.getbyAccountNO(accountCancel.getAccountno()).getState()!=1){
		
			accountCancel.setDealtime(new Date());
			accountCancel.setDealuid(getId()); 
	
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
			
			
			
			
			String msgurl = bootdoConfig.getInterfaceUrl()+"api/pc/accountcancel/edit";
			SLRequest slrequest2 = new SLRequest();
			 
			slrequest2.setAppId("slzrop");
			slrequest2.setAppType("OP");
	
			slrequest2.setTimeStamp(YXDate.getTime());
			slrequest2.setNonce("62A0E888F5033BC65BF8D78622277C8E"+new java.util.Random().nextInt(100));
			slrequest2.setUrl(msgurl);
			slrequest2.setData(accountCancel);
			
			try {
				String slrequestMap = JSONObject.toJSONString(slrequest2);	
				String msg2= HttpUtil.sendPost(msgurl,slrequestMap,token);
				System.out.println(msg2);
			} catch (Exception e) {	
				System.out.println("发送失败"+e);
				e.printStackTrace();
				return R.error("操作失败");
			} 
			return R.ok("操作成功");
		}else{
			return R.error("用户已撤销申请,请刷新列表页");
		}
	}
	

	

	
	/**
     * 导出
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/expexcel")
	@SystemControllerLog(description="4")
    @RequiresPermissions("account:accountCancel:expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response){
    	   	
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
    	parameterMap.put("searchName", request.getParameter("searchName"));
    	parameterMap.put("dealresult", request.getParameter("dealresult"));
    	parameterMap.put("refundresult", request.getParameter("refundresult"));
    	
  	  	String fileName = "账号注销.xls";
  	  	
  	  	String[] title = new String[]{"账号","类型","状态","手机号码","申请时间","账户余额","可退金额","服务费","退款时间","审核状态","审核时间"};
  	  	
  	    List<AccountCancelDO> list = null;
  	     	    
		try {
			list = accountCancelService.list(parameterMap);		    	 	    	  		    	  		
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        if(null!=list.get(i).getAccountno() && !list.get(i).getAccountno().equals("")){
		        	str[0]=list.get(i).getAccountno().toString();
		        }else{
		        	str[0]="";
		        }
		        if(null!=list.get(i).getAccountTypeName()&& !list.get(i).getAccountTypeName().equals("")){
		        	str[1]=list.get(i).getAccountTypeName().toString();
		        }else{
		        	str[1]="";
		        }
		        if(null!=list.get(i).getStateName()&& !list.get(i).getStateName().equals("")){
		        	str[2]=list.get(i).getStateName().toString();
		        }else{
		        	str[2]="";
		        }
		        if(null!=list.get(i).getMobilephone() && !list.get(i).getMobilephone().equals("")){
		        	str[3]=list.get(i).getMobilephone().toString();
		        }else{
		        	str[3]="";
		        }
		        if(null!=list.get(i).getSubmittime() && !list.get(i).getSubmittime().equals("")){
		        	str[4]=DateUtils.format(list.get(i).getSubmittime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[4]="";
		        }
		        if(null!=list.get(i).getAbalance() && !list.get(i).getAbalance().equals("")){
		        	str[5]=list.get(i).getAbalance().toString();
		        }else{
		        	str[5]="";
		        }
		        if(null!=list.get(i).getBalance() && !list.get(i).getBalance().equals("")){
		        	str[6]=list.get(i).getBalance().toString();
		        }else{
		        	str[6]="";
		        }
		        if(null!=list.get(i).getServicefee() && !list.get(i).getServicefee().equals("")){
		        	str[7]=list.get(i).getServicefee().toString();
		        }else{
		        	str[7]="";
		        }
		        if(null!=list.get(i).getRefundtime() && !list.get(i).getRefundtime().equals("")){
		        	str[8]=DateUtils.format(list.get(i).getRefundtime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[8]="";
		        }
		        if(null!=list.get(i).getDealresult() && !list.get(i).getDealresult().equals("")){
		        	//str[9]=list.get(i).getDealresult().toString();
		        	if(list.get(i).getDealresult().toString().equals("0")){
		        		str[9]="审核不通过";
		        	}
		        	if(list.get(i).getDealresult().toString().equals("1")){
		        		str[9]="审核通过";
		        	}
		        	if(list.get(i).getDealresult().toString().equals("-1")){
		        		str[9]="未审核";
		        	}
		        	
		        }else{
		        	str[9]="";
		        }
		        if(null!=list.get(i).getDealtime() && !list.get(i).getDealtime().equals("")){
		        	str[10]=DateUtils.format(list.get(i).getDealtime(),DateUtils.DATE_TIME_PATTERN);
		        }else{
		        	str[10]="";
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
