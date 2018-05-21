package com.slzr.operation.controller;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.PageUtils;
import com.slzr.common.utils.PathUtils;
import com.slzr.common.utils.Query;
import com.slzr.common.utils.R;
import com.slzr.operation.domain.*;
import com.slzr.operation.service.ArticleService;
import com.slzr.operation.service.OPTDriverService;
import com.slzr.operation.service.OPTLineService;
import com.slzr.set.domain.MerchDo;
import com.slzr.set.service.MerchService;
import com.slzr.system.domain.UserDO;
import com.slzr.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RequestMapping("/operation/optdriver")
@Controller
public class OPTDriverController extends BaseController{
	private String prefix = "operation/optdriver";
	@Autowired
	private OPTDriverService oPTDriverService;


	@Autowired
	private OPTLineService oPTLineService;

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
		model.addAttribute("merchantList",merchantList);
		return prefix + "/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Query query = new Query();
		List<OPTLineDO>  lineList = oPTLineService.list(query);
		model.addAttribute("lineList",lineList);
		return prefix + "/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model,@PathVariable("id") String id) {
		OPTDriverDO doo = oPTDriverService.getByID(Integer.valueOf(id));
		model.addAttribute("model",oPTDriverService.getByID(Integer.valueOf(id)));

		Query query = new Query();
		List<OPTLineDO>  lineList = oPTLineService.list(query);
		model.addAttribute("lineList",lineList);

		return prefix + "/edit";
	}
	
	@ResponseBody
	@GetMapping("api/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		UserDO userDO = userService.get(getId());
		PageUtils pageUtil=null;
		if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
			//查询列表数据
			Query query = new Query(params);
			List<OPTDriverDO> list = oPTDriverService.list(query);
			int total = oPTDriverService.count(query);
			 pageUtil = new PageUtils(list, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			//查询列表数据
			Query query = new Query(params);
			List<OPTDriverDO> list = oPTDriverService.list(query);
			int total = oPTDriverService.count(query);
			 pageUtil = new PageUtils(list, total);
		}

		return pageUtil;
	}
	
	@ResponseBody
	@SystemControllerLog(description="1")
	@PostMapping("api/add")
	public R apiadd(@RequestParam Map<String, Object> params){
		try {
			OPTDriverDO oPTDriverDO = new OPTDriverDO();
			String driverCode = params.get("driverCode").toString();
			String driverNO = params.get("driverNO").toString();
			String driverName = params.get("driverName").toString();
			String driverCardID = params.get("driverCardID").toString();
			String iDCardNO = params.get("iDCardNO").toString();
			String iDCardAddr = params.get("iDCardAddr").toString();
			String lineID = params.get("lineID").toString();
			String phone = params.get("phone").toString();
			String workCardNO = params.get("workCardNO").toString();
			String beginWorkDate = params.get("beginWorkDate").toString();
			String driverState = params.get("driverState").toString();

			UserDO userDO = userService.get(getId());

			// 临时
			oPTDriverDO.setMerchantCode(""+userDO.getMerchantCode());


			oPTDriverDO.setDriverCode(driverCode);
			oPTDriverDO.setDriverNO(driverNO);
			oPTDriverDO.setDriverName(driverName);
			oPTDriverDO.setDriverCardID(driverCardID);
			oPTDriverDO.setiDCardNO(iDCardNO);
			oPTDriverDO.setiDCardAddr(iDCardAddr);
			oPTDriverDO.setLineID(lineID);
			oPTDriverDO.setPhone(phone);
			oPTDriverDO.setWorkCardNO(workCardNO);
			oPTDriverDO.setBeginWorkDate(beginWorkDate);
			oPTDriverDO.setDriverState(driverState);
			oPTDriverDO.setCreatedDate(new Date());
			oPTDriverDO.setCreatedBy(getId()+"");


			oPTDriverService.save(oPTDriverDO);
		}catch (Exception e) {
			System.out.print(e);
			return R.error("添加失败");
		}
		return R.ok("添加成功");
	}
	
	@ResponseBody
	@SystemControllerLog(description="2")
	@PostMapping("api/edit")
	public R apiedit(@RequestParam Map<String, Object> params){
		try {
			int id = Integer.parseInt(params.get("id").toString());
			OPTDriverDO oPTDriverDO = oPTDriverService.getByID(id);
			String driverCode = params.get("driverCode").toString();
			String driverNO = params.get("driverNO").toString();
			String driverName = params.get("driverName").toString();
			String driverCardID = params.get("driverCardID").toString();
			String iDCardNO = params.get("iDCardNO").toString();
			String iDCardAddr = params.get("iDCardAddr").toString();
			String lineID = params.get("lineID").toString();
			String phone = params.get("phone").toString();
			String workCardNO = params.get("workCardNO").toString();
			String beginWorkDate = params.get("beginWorkDate").toString();
			String driverState = params.get("driverState").toString();

			oPTDriverDO.setDriverCode(driverCode);
			oPTDriverDO.setDriverNO(driverNO);
			oPTDriverDO.setDriverName(driverName);
			oPTDriverDO.setDriverCardID(driverCardID);
			oPTDriverDO.setiDCardNO(iDCardNO);
			oPTDriverDO.setiDCardAddr(iDCardAddr);
			oPTDriverDO.setLineID(lineID);
			oPTDriverDO.setPhone(phone);
			oPTDriverDO.setWorkCardNO(workCardNO);
			oPTDriverDO.setBeginWorkDate(beginWorkDate);
			oPTDriverDO.setDriverState(driverState);
			oPTDriverService.update(oPTDriverDO);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
			return R.error("编辑失败");
		}
		return R.ok("编辑成功");
	}
	

	/**
	 * 标记 删除
	 */
	@PostMapping("/deletedriver")
	@SystemControllerLog(description="3")
	@ResponseBody
	//	@RequiresPermissions("operation:GuestMessage:remove")
	public R deleteart(Integer id){
		try {
//			GuestMessageDO Do = GuestMessageService.getByID(id);
			oPTDriverService.remove(id);
			return R.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}
	
}
