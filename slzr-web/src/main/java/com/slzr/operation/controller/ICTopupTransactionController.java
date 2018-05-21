package com.slzr.operation.controller;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.*;
import com.slzr.operation.domain.ICTopupTransactionDO;
import com.slzr.operation.domain.ICWXRefundDO;
import com.slzr.operation.service.ICTopupTransactionService;
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


// IC卡 圈存查询
@Controller
@RequestMapping("/operation/ictopuptransaction")
public class ICTopupTransactionController extends BaseController{
	private String prefix = "operation/ictopuptransaction";
//	@Autowired
//	private DailysummaryTopupService topupservice;

	@Autowired
	private ICTopupTransactionService icTopupTransactionService;
	
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
		List<ICTopupTransactionDO> list = icTopupTransactionService.list(query);
		int total = icTopupTransactionService.count(query);
		PageUtils pageUtils = new PageUtils(list, total);
		return pageUtils;
	}
	
//	@ResponseBody
//	@RequestMapping("api/check")
//	@RequiresPermissions("operation:topup:check")
//	public R apicheck(String id,int auditvalue,String remark){
//		ICWXRefundDO settledo = icwxRefundService.get(id);
//		if(settledo.getAuditStatus()==-1)
//		{
//			icwxRefundService.updateAudit(id, auditvalue, this.getUserId().intValue(),remark);
//			return R.ok();
//		}
//        return R.error();
//	}
	
//	@ResponseBody
//	@RequestMapping("api/again")
//	@RequiresPermissions("operation:topup:do")
//	public R apiagain(String id){
//		icTopupTransactionService.updateAudit(id, -1, this.getId().intValue(),"");
//		return R.ok();
//	}
    @RequestMapping("/expexcel")
    public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
    	
  	  	String fileName = "IC卡圈存查询报表.xls";
  	  	
  	  	String[] title = new String[]{"订单号","终端号","卡号","订单状态","订单金额","支付时间","支付人","圈存结果","圈存时间","审核时间","审核人"};
  	  	
  	    List<ICTopupTransactionDO> list = null;
  	    
		try {
			list = icTopupTransactionService.list(params);
	    	List<Object[]> data = new ArrayList<Object[]>();
	    	for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
		        //订单号
		        if(null!=list.get(i).getOrderNO() && !list.get(i).getOrderNO().equals("")){
		        	str[0] = list.get(i).getOrderNO();
		        }else{
		        	str[0] = "";
		        }
		        //终端号
		        if(null!=list.get(i).getTerminalNO() && !list.get(i).getTerminalNO().equals("")){
		        	str[1] = list.get(i).getTerminalNO();
		        }else{
		        	str[1] = "";
		        }
		        //卡号
		        if(null!=list.get(i).getCardAppNO()){
		        	str[2] = list.get(i).getCardAppNO();
		        }else{
		        	str[2] = "";
		        }

		        int oorderState  = list.get(i).getOorderState();
                if(oorderState==1)
                    str[3] = "未支付";
                if(oorderState==2)
                    str[3] = "支付成功";
                if(oorderState==3)
                    str[3] = "支付失败";
                if(oorderState==4)
                    str[3] = "锁定";
                if(oorderState==5)
                    str[3] = "圈存失败";
                if(oorderState==6)
                    str[3] = "圈存成功";
                if(oorderState==7)
                    str[3] = "已退款";


		        //订单金额
                str[4] = "" + list.get(i).getOtxnAmount();

		        //支付时间
		        if(null!=list.get(i).getOpayTime()){
		        	str[5] = DateUtils.format(list.get(i).getOpayTime(),DateUtils.DATE_TIME_PATTERN) ;
		        }else{
		        	str[5] = "";
		        }
		        //支付人
		        if(null!=list.get(i).getbNickName()){
		        	str[6] = list.get(i).getbNickName();
		        }else{
		        	str[6] = "";
		        }


		        //圈存结果
                int oloadState  = list.get(i).getOloadState();
                if(oloadState==1)
                    str[7] = "未支付";
                if(oloadState==2)
                    str[7] = "支付成功";
                if(oloadState==3)
                    str[7] = "支付失败";
                if(oloadState==4)
                    str[7] = "锁定";
                if(oloadState==5)
                    str[7] = "圈存失败";
                if(oloadState==6)
                    str[7] = "圈存成功";
                if(oloadState==7)
                    str[7] = "已退款";


                //圈存时间
		        if(null!=list.get(i).getOloadBackDate()){
		        	str[8] = DateUtils.format(list.get(i).getOloadBackDate(),DateUtils.DATE_TIME_PATTERN)  ;
		        }else{
		        	str[8] = "";
		        }
		        //审核时间
		        if(null!=list.get(i).getOreviewTime()){
		        	str[9] = DateUtils.format(list.get(i).getOreviewTime(),DateUtils.DATE_TIME_PATTERN)  ;
		        }else{
		        	str[9] = "";
		        }
		        //审核人
		        if(null!=list.get(i).getsUserName()){
		        	str[10] = list.get(i).getsUserName();
		        }else{
		        	str[10] = "";
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
