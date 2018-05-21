package com.slzr.operation.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slzr.common.utils.DWMQDateUtil;
import com.slzr.common.utils.DWMQDateVO;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.ExcelExportTool;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.Query;
import com.slzr.operation.domain.DebitTransDO;
import com.slzr.operation.domain.OPTBusDO;
import com.slzr.operation.domain.OPTDepartmentDO;
import com.slzr.operation.domain.OPTDriverDO;
import com.slzr.operation.domain.OPTLineDO;
import com.slzr.operation.domain.TopupTransDO;
import com.slzr.operation.service.DebitTransService;
import com.slzr.operation.service.OPTBusService;
import com.slzr.operation.service.OPTDepartmentService;
import com.slzr.operation.service.OPTDriverService;
import com.slzr.operation.service.OPTLineService;

@RequestMapping("/operation/debitTrans")
@Controller
public class DebitTransController extends BaseController{
	@Autowired
	private OPTDepartmentService optDepartmentService;
	@Autowired
	UserService userService;
	@Autowired
	private OPTLineService optLineService;

	private String prefix = "operation/debitTrans";
	@Autowired
	private DebitTransService debittransservice;
	
	@Autowired
	private OPTBusService optBusService;
	
	@Autowired
	private OPTDriverService optDriverService;

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
		model.addAttribute("merchantList",merchantList);
		DWMQDateVO dwmq = null;
		try {
			dwmq = DWMQDateUtil.GetDWMQDateVO(DateUtils.format(Calendar.getInstance().getTime(), DateUtils.DATE_PATTERN));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<OPTDepartmentDO> Dept = optDepartmentService.list(new HashMap<>());
		List<OPTLineDO> lineName = optLineService.list(new HashMap<>());
		List<OPTBusDO> bus = optBusService.list(new HashMap<>());
		List<OPTDriverDO> driverName = optDriverService.list(new HashMap<>());
		
		model.addAttribute("dwmq", dwmq);
		model.addAttribute("lineName",lineName);
		model.addAttribute("Dept",Dept);
		model.addAttribute("bus",bus);
		model.addAttribute("driverName",driverName);
		
		return prefix + "/list";
	}
	
	@ResponseBody
	@GetMapping("api/list")
	@RequiresPermissions("operation:debitTrans:list")
	public PageUtils list(@RequestParam Map<String, Object> params) {

		UserDO userDO = userService.get(getId());
		PageUtils pageUtil=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			//查询列表数据
			Query query = new Query(params);
			List<DebitTransDO> list = debittransservice.list(query);
			int total = debittransservice.count(query);
			pageUtil = new PageUtils(list, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			Query query = new Query(params);
			List<DebitTransDO> list = debittransservice.list(query);
			int total = debittransservice.count(query);
			 pageUtil = new PageUtils(list, total);
		}

		return pageUtil;
	}

	@SystemControllerLog(description="4")
    @RequestMapping("/expexcel")
	public void expExceluser(ModelMap model,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> params){
		String fileName = "消费交易报表.xls";
		
		String[] title = new String[]{"帐号","线路","车辆","司机","消费金额","账号余额","消费时间","交易状态","扣费来源"};
		List<DebitTransDO> list = null;
		
			try {
				list = debittransservice.list(params);
				List<Object[]> data = new ArrayList<Object[]>();
				for(int i=0; i<list.size();i++){
		        Object[] str = new Object[title.length];
			        //账号
			        if(null!=list.get(i).getAccountNO() && !list.get(i).getAccountNO().equals("")){
		        		str[0] = list.get(i).getAccountNO();
			        }
	        		else {
			        	str[0] = "";
			        }
			        //线路
			        if(null!=list.get(i).getLineNO() && !list.get(i).getLineNO().equals("")){
		        		str[1] = list.get(i).getLineNO();
			        }
	        		else {
			        	str[1] = "";
			        }
			        //车辆
			        if(null!=list.get(i).getTerminalNO()){
		        		str[2] = list.get(i).getTerminalNO();
			        }
	        		else {
			        	str[2] = "";
			        }
			        //司机
			        if(null!=list.get(i).getDriverNO() && !list.get(i).getDriverNO().equals("")){
		        		str[3] = list.get(i).getDriverNO();
			        }
	        		else {
			        	str[3] = "";
			        }
			        //消费金额
			        if(null!=list.get(i).getTxnAmount()){
		        		str[4] = list.get(i).getTxnAmount();
			        }
	        		else {
			        	str[4] = "";
			        }
			        //账号余额
			        if(null!=list.get(i).getBalance()){
		        		str[5] = list.get(i).getBalance();
			        }
	        		else {
			        	str[5] = "";
			        }
			        //消费时间
			        if(null!=list.get(i).getTxnDateTime()){
		        		str[6] = DateUtils.format(list.get(i).getTxnDateTime(), DateUtils.DATE_TIME_PATTERN);
			        }
	        		else {
			        	str[6] = "";
			        }
			        //交易状态
					if(list.get(i).getDebitStatus()==1)
						str[7]= "正常扣费";
					else if(list.get(i).getDebitStatus()==2)
						str[7]= "未补扣";
					else if(list.get(i).getDebitStatus()==3)
						str[7]= "已补扣";
	        		else {
			        	str[7] = "";
			        }

					//扣费来源
					if("1".equals(list.get(i).getDebitFrom()))
						str[8]= "赠送余额";
					else {
						str[8] = "";
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
