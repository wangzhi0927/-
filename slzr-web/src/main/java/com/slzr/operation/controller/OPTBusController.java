package com.slzr.operation.controller;

import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.*;
import com.slzr.operation.domain.*;
import com.slzr.operation.service.ArticleService;
import com.slzr.operation.service.OPTBusService;
import com.slzr.operation.service.OPTDepartmentService;
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

@RequestMapping("/operation/optbus")
@Controller
public class OPTBusController extends BaseController{
	private String prefix = "operation/optbus";
	@Autowired
	private OPTBusService oPTBusService;

	@Autowired
	private OPTDepartmentService oPTDepartmentService;

	@Autowired
	private OPTLineService oPTLineService;

	@Autowired
	UserService userService;

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
		List<OPTDepartmentDO>  departmentList = oPTDepartmentService.list(query);
		model.addAttribute("departmentList",departmentList);

		List<OPTLineDO>  lineList = oPTLineService.list(query);
		model.addAttribute("lineList",lineList);
		return prefix + "/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model,@PathVariable("id") String id) {
		OPTBusDO o = oPTBusService.getByID(Integer.valueOf(id));
		model.addAttribute("model",oPTBusService.getByID(Integer.valueOf(id)));
		Map<String, Object> params = new HashMap<String, Object>();
		Query query = new Query();
		List<OPTDepartmentDO>  departmentList = oPTDepartmentService.list(query);
		model.addAttribute("departmentList",departmentList);

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
			List<OPTBusDO> list = oPTBusService.list(query);
			int total = oPTBusService.count(query);
			 pageUtil = new PageUtils(list, total);
		}else{
			params.put("merchantCode",userDO.getMerchantCode());
			Query query = new Query(params);
			List<OPTBusDO> list = oPTBusService.list(query);
			int total = oPTBusService.count(query);
			 pageUtil = new PageUtils(list, total);
		}

		return pageUtil;
	}
	
	@ResponseBody

	@SystemControllerLog(description="1")
	@PostMapping("api/add")
	public R apiadd(@RequestParam Map<String, Object> params){
		try {
			OPTBusDO oPTBusDO = new OPTBusDO();
			String busCode = params.get("busCode").toString();
			String busNO = params.get("busNO").toString();
			String busModel = params.get("busModel").toString();
			String busPlateNO = params.get("busPlateNO").toString();
			String runCardNO = params.get("runCardNO").toString();
//			String lineName = params.get("lineName").toString();
			String terminalNum = params.get("terminalNum").toString();
			String factory = params.get("factory").toString();
			String state = params.get("state").toString();
			String remark = params.get("remark").toString();
			String lineID = params.get("lineID").toString();
			int deptCode = 0;
			if(params.get("deptCode") != null) {
				String deptCodeParams = params.get("deptCode").toString();
				if(deptCodeParams !=null&&!deptCodeParams.equals("")) {
					deptCode = Integer.parseInt(deptCodeParams);
				}
			}

			Date buyDate = null;
			if(params.get("buyDate") != null) {
				buyDate = DateUtils.ConvertToDate(params.get("buyDate").toString());
			}

			UserDO userDO = userService.get(getId());


			oPTBusDO.setMerchantCode(""+ userDO.getMerchantCode());
			oPTBusDO.setBusCode(busCode);
			oPTBusDO.setBusNO(busNO);
			oPTBusDO.setBusModel(busModel);
			oPTBusDO.setBusPlateNO(busPlateNO);
			oPTBusDO.setRunCardNO(runCardNO);
//			oPTBusDO.setLineName(lineName);
			oPTBusDO.setTerminalNum(terminalNum);
			oPTBusDO.setFactory(factory);
			oPTBusDO.setDeptID(deptCode);
			oPTBusDO.setBuyDate(buyDate);
			oPTBusDO.setState(state);
			oPTBusDO.setRemark(remark);
			oPTBusDO.setLineID(lineID);
			oPTBusDO.setCreatedDate(new Date());



			oPTBusDO.setCreatedDate(new Date());
			oPTBusDO.setCreatedBy(getId()+"");
			oPTBusDO.setUpdatedBy(getId()+"");

			oPTBusService.save(oPTBusDO);
		}catch (Exception e) {
			System.out.print(e);
			e.printStackTrace();
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
			OPTBusDO oPTBusDO = oPTBusService.getByID(id);
//			String deptName = params.get("deptName").toString();
			String busCode = params.get("busCode").toString();
			String busNO = params.get("busNO").toString();
			String busModel = params.get("busModel").toString();
			String busPlateNO = params.get("busPlateNO").toString();
			String runCardNO = params.get("runCardNO").toString();
			String terminalNum = params.get("terminalNum").toString();
			String factory = params.get("factory").toString();
			String state = params.get("state").toString();
			String remark = params.get("remark").toString();
			String lineID = params.get("lineID").toString();

			int deptCode = 0;
			if(params.get("deptCode") != null) {
				String deptCodeParams = params.get("deptCode").toString();
				if(deptCodeParams !=null && !deptCodeParams.equals("")) {
					deptCode = Integer.parseInt(deptCodeParams);
				}
			}

			Date buyDate = null;
			if(params.get("buyDate") != null) {
				buyDate = DateUtils.ConvertToDate(params.get("buyDate").toString());
			}


			UserDO userDO = userService.get(getId());

			oPTBusDO.setMerchantCode(""+userDO.getMerchantCode());

			oPTBusDO.setDeptID(deptCode);
			oPTBusDO.setBusCode(busCode);
			oPTBusDO.setBusNO(busNO);
			oPTBusDO.setBusModel(busModel);
			oPTBusDO.setBusPlateNO(busPlateNO);
			oPTBusDO.setRunCardNO(runCardNO);

			oPTBusDO.setLineID(lineID);

			oPTBusDO.setTerminalNum(terminalNum);
			oPTBusDO.setFactory(factory);
			oPTBusDO.setBuyDate(buyDate);
			oPTBusDO.setState(state);
			oPTBusDO.setRemark(remark);

			oPTBusDO.setCreatedDate(new Date());
			oPTBusDO.setCreatedBy(getId()+"");
			oPTBusDO.setUpdatedBy(getId()+"");
			oPTBusDO.setLineID(lineID);

			oPTBusService.update(oPTBusDO);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
			e.printStackTrace();
			return R.error("编辑失败");
		}
		return R.ok("编辑成功");
	}

	/**
	 * 标记 删除
	 */
	@PostMapping("/delete")
	@SystemControllerLog(description="3")
	@ResponseBody
	//	@RequiresPermissions("operation:GuestMessage:remove")
	public R deleteart(Integer id){
		try {
//			GuestMessageDO Do = GuestMessageService.getByID(id);
			oPTBusService.remove(id);
			return R.ok();
		}catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
	}



}
