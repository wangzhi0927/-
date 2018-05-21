package com.slzr.system.controller;

import com.slzr.common.annotation.Log;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.config.BootdoConfig;
import com.slzr.common.config.Constant;
import com.slzr.common.controller.BaseController;
import com.slzr.common.domain.Tree;
import com.slzr.common.service.LogService;
import com.slzr.common.utils.*;
import com.slzr.set.domain.CompanyDo;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.CompanyService;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.MenuDO;
import com.slzr.system.service.MenuService;
import com.slzr.system.service.StatisticsService;
import com.slzr.system.service.UserService;
import com.slzr.system.vo.DayRegisterVO;
import com.slzr.system.vo.RecentlyWeekVo;
import com.slzr.system.vo.StatisticsVO;
import com.slzr.system.vo.WeekDayVO;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;

	@Autowired
	UserService userService;
	
	@Autowired
	StatisticsService statisticsService;

    @Autowired
    private CompanyService companyService;
	
	@Autowired
	BootdoConfig bootdoConfig;	
	
	@Autowired
	private LogService logService;
	
	@GetMapping({ "/", "" })
	String welcome(Model model) {
		model.addAttribute("proName", bootdoConfig.getProName());
		return "redirect:/login";
	}

	@GetMapping({ "/index" })
	String index(Model model) {		
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getTrueName());
	/*		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl ","/img/photo_s.jpg");
		}*/
		model.addAttribute("picUrl","/img/photo_s.jpg");
		model.addAttribute("username", getUser().getUserName());
		model.addAttribute("proTitle", bootdoConfig.getProTitle());
		return "index_v1";
	}
	@GetMapping("/login")
	String login(Model model) {
		model.addAttribute("proName", bootdoConfig.getProName());
		return "login";
	}

	@SystemControllerLog(description="16")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			/*Map<String,String[]> param=new HashMap<>(); //请求提交的参数
			//param.put("username",[username,password]);
			String params = JSON.toJSONString(param);*/

			subject.login(token);
			/*LogDO logDO=new LogDO();
			logDO.setOperateTypeID(16);
			logDO.setModuleID(1);
			logDO.setLogTypeID(1);
			logDO.setSourceMethod("classname:com.slzr.system.controller.LoginController ==>methodName:ajaxLogin");
			logDO.setLogDateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));//用户操作的时间
			LogContentDo logContent=new LogContentDo();
			logContent.setId(UuidUtils.creatUUID());*/
			//logDO.setModuleID();
			//logDO.set
			//logService.createLog()
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}
	
	@SystemControllerLog(description="17")
	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main(Model model,String code) {
		
		code=code==null?"":code;
		
		List<CompanyDo> lsCode = companyService.list(new Query());
		model.addAttribute("companycodes", lsCode);
		
		StatisticsVO vo = statisticsService.GetStatisticsVO(code);
		
		String nameattr1="total";
		if(vo.getTotalStatistics()!=null)
		{
			model.addAttribute(nameattr1+"debit"+"amount", vo.getTotalStatistics().getDebitAmount()!=null?vo.getTotalStatistics().getDebitAmount().add(vo.getTodayStatistics().getDebitAmount()):0);
			model.addAttribute(nameattr1+"debit"+"num", vo.getTotalStatistics().getDebitNum()+vo.getTodayStatistics().getDebitNum());
			model.addAttribute(nameattr1+"refund"+"amount", vo.getTotalStatistics().getRefundAmount()!=null?vo.getTotalStatistics().getRefundAmount().add(vo.getTodayStatistics().getRefundAmount()):0);
			model.addAttribute(nameattr1+"refund"+"num", vo.getTotalStatistics().getRefundNum()+vo.getTodayStatistics().getRefundNum());
			model.addAttribute(nameattr1+"topup"+"amount", vo.getTotalStatistics().getTopupAmount()!=null?vo.getTotalStatistics().getTopupAmount().add(vo.getTodayStatistics().getTopupAmount()):0);
			model.addAttribute(nameattr1+"topup"+"num", vo.getTotalStatistics().getTopupNum()+vo.getTodayStatistics().getTopupNum());
		}

		String nameattr2="today";
		if(vo.getTodayStatistics()!=null)
		{
			model.addAttribute(nameattr2+"debit"+"amount", vo.getTodayStatistics().getDebitAmount());
			model.addAttribute(nameattr2+"debit"+"num", vo.getTodayStatistics().getDebitNum());
			model.addAttribute(nameattr2+"refund"+"amount", vo.getTodayStatistics().getRefundAmount()!=null?vo.getTodayStatistics().getRefundAmount():0);
			model.addAttribute(nameattr2+"refund"+"num", vo.getTodayStatistics().getRefundNum());
			model.addAttribute(nameattr2+"topup"+"amount", vo.getTodayStatistics().getTopupAmount());
			model.addAttribute(nameattr2+"topup"+"num", vo.getTodayStatistics().getTopupNum());
		}
		
		model.addAttribute(nameattr1+"register"+"num", vo.getTotalRegister());
		model.addAttribute(nameattr2+"register"+"num", vo.getTodayRegister());
		
		RecentlyWeekVo weekvo=new RecentlyWeekVo();
		weekvo.getLegends().add("用户新增数");
		weekvo.getLegends().add("充值总笔数");
		weekvo.getLegends().add("消费总笔数");
		weekvo.getLegends().add("退款总笔数");
		weekvo.getLegends().add("充值金额");
		weekvo.getLegends().add("消费金额");
		
		Calendar cale=Calendar.getInstance();
		cale.add(Calendar.DATE, -8);
		
		for(int i=0;i<7;i++)
		{
			cale.add(Calendar.DATE, +1);
			
			weekvo.getWeeknames().add(DateUtils.format(cale.getTime(), DateUtils.DATE_PATTERN));
			String curDateStr=DateUtils.format(cale.getTime(),DateUtils.DATE_PATTERN);
			
			int len1=weekvo.getData1().size();
			for(DayRegisterVO reg : vo.getWeekDayRegister())
			{
				if(reg.getDate().equals(curDateStr))
				{
					weekvo.getData1().add(reg.getDayRegister());
				}
			}
			if(len1==weekvo.getData1().size())
				weekvo.getData1().add(0);
			

			int len2=weekvo.getData2().size();
			for(WeekDayVO topup : vo.getWeekDay())
			{
				if(topup.getDate().equals(curDateStr))
				{
					weekvo.getData2().add(topup.getDayTopupNum());
				}
			}
			if(len2==weekvo.getData2().size())
				weekvo.getData2().add(0);

			int len3=weekvo.getData3().size();
			for(WeekDayVO debit : vo.getWeekDay())
			{
				if(debit.getDate().equals(curDateStr))
				{
					weekvo.getData3().add(debit.getDayDebitNum());
				}
			}
			if(len3==weekvo.getData3().size())
				weekvo.getData3().add(0);


			int len4=weekvo.getData4().size();
			for(WeekDayVO topup : vo.getWeekDay())
			{
				if(topup.getDate().equals(curDateStr))
				{
					weekvo.getData4().add(topup.getDayRefundNum());
				}
			}
			if(len4==weekvo.getData4().size())
				weekvo.getData4().add(0);
			
			int len5=weekvo.getData5().size();
			for(WeekDayVO wdvo : vo.getWeekDay())
			{
				if(wdvo.getDate().equals(curDateStr))
				{
					weekvo.getData5().add(wdvo.getDayTopupAmount());
				}
			}
			if(len5==weekvo.getData5().size())
				weekvo.getData5().add(new BigDecimal(0));
			
			int len6=weekvo.getData6().size();
			for(WeekDayVO wdvo : vo.getWeekDay())
			{
				if(wdvo.getDate().equals(curDateStr))
				{
					weekvo.getData6().add(wdvo.getDayDebitAmount());
				}
			}
			if(len6==weekvo.getData6().size())
				weekvo.getData6().add(new BigDecimal(0));
		}
		
		model.addAttribute("weeks", JSONUtils.beanToJson(weekvo));
		
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

	/*@Log("修改密码")*/
	@GetMapping (value = "/edit")
	String  editUser(Model model) {
		model.addAttribute("name", getUser().getTrueName());
		model.addAttribute("username", getUser().getUserName());
		model.addAttribute("userId",getUser().getId());
		return "edit";
	}

	/**
	 * 判断输入的密码是否正确
	 */
	@PostMapping("/isExit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		String  userName= (String) params.get("username");
		String password = (String) params.get("password");
		String Mpassword=MD5Utils.encrypt(userName,password);
		params.put("passWord",Mpassword);
		params.put("userName",userName);
		// 存在，不通过，false
		return userService.exit(params);
	}


	/**
	 * 修改账号用户名跟密码
	 */
	/*@Log("更新用户")*/
	@PostMapping("/updateNameAndPasW")
	@SystemControllerLog(description="7")
	@ResponseBody
	R updatePeronal(@RequestParam Map<String, Object> params) {
		String  username= (String) params.get("username");
		String password = (String) params.get("newPassword");
		 password=MD5Utils.encrypt(username,password);
		 params.put("password",password);
		if (Constant.DEMO_ACCOUNT.equals(getUserName())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		if (userService.updateNameAndPasW(params) > 0) {
			return R.ok();
		}
		return R.error();
	}
}
