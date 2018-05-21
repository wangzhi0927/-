package com.slzr.operation.controller;


import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.*;
import com.slzr.operation.domain.*;
import com.slzr.operation.service.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;

@RequestMapping("/operation/failConsum")
@Controller
public class FailConsunmController extends BaseController{

    @Autowired
     UserService userService;

    @Autowired
    private OPTDepartmentService optDepartmentService;

    @Autowired
    private OPTLineService optLineService;

    @Autowired
    private OPTBusService optBusService;

    @Autowired
    private OPTDriverService optDriverService;

    @Autowired
    private FailConsunmService failConsunmService;

    @Autowired
    private MerchService merchService;

    private String prefix = "operation/failConsum";

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
        List<OPTDepartmentDO> Dept = optDepartmentService.list(params);

        List<OPTLineDO> line = optLineService.list(params);

        List<OPTBusDO> bus = optBusService.list(params);

        List<OPTDriverDO> driver = optDriverService.list(params);

        model.addAttribute("Dept",Dept);
        model.addAttribute("line",line);
        model.addAttribute("bus",bus);
        model.addAttribute("driver",driver);

        model.addAttribute("dwmq", dwmq);
        return prefix + "/list";
    }


    @ResponseBody
    @GetMapping("api/list")
    @RequiresPermissions("operation:failConsum:list")
    public PageUtils list(@RequestParam Map<String, Object> params) {

        UserDO userDO = userService.get(getId());
        PageUtils pageUtil=null;
        if(userDO.getMerchantCode()==""||userDO.getMerchantCode()==null){
            //查询列表数据
            Query query = new Query(params);
            List<FailConsumDo> list = failConsunmService.list(query);
            for(int i=0;i<list.size();i++){
                Integer doorFlag = list.get(i).getDoorFlag();
                if(doorFlag==1){//上车
                    list.get(i).setStationTime(list.get(i).getStartStationTime());
                }else if(doorFlag==2){//下车
                    list.get(i).setStationTime(list.get(i).getEndStaionTime());
                }
            }
            int total = failConsunmService.count(query);
            pageUtil = new PageUtils(list, total);
        }else{
            params.put("merchantCode",userDO.getMerchantCode());
            Query query = new Query(params);
            List<FailConsumDo> list = failConsunmService.list(query);
            for(int i=0;i<list.size();i++){
                Integer doorFlag = list.get(i).getDoorFlag();
                if(doorFlag==1){//上车
                    list.get(i).setStationTime(list.get(i).getStartStationTime());
                }else if(doorFlag==2){//下车
                    list.get(i).setStationTime(list.get(i).getEndStaionTime());
                }
            }
            int total = failConsunmService.count(query);
            pageUtil = new PageUtils(list, total);
        }
        return pageUtil;
    }

    @SystemControllerLog(description="4")
    @RequestMapping("/expexcel")
    public void expExceluser(ModelMap model, HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params){
        String fileName = "异常消费报表.xls";

        String[] title = new String[]{"账号","线路","车辆","方向","站点","上/下车","上/下车时间"};

        List<FailConsumDo> list = null;

        try {
            list = failConsunmService.list(params);
            List<Object[]> data = new ArrayList<Object[]>();
            for(int i=0; i<list.size();i++){
                Object[] str = new Object[title.length];
                if(null!=list.get(i).getAccountNO() && !list.get(i).getAccountNO().equals("")){
                    str[0] = list.get(i).getAccountNO();
                }
                else {
                    str[0] = "";
                }
                if(null!=list.get(i).getLineName()){
                    str[1] = list.get(i).getLineName();
                }
                else {
                    str[1] = "";
                }
                //终端编号
                if(null!=list.get(i).getBusNo() && !list.get(i).getBusNo().equals("")){
                    str[2] = list.get(i).getBusNo();
                }
                else {
                    str[2] = "";
                }
                //方向
                if(null!=list.get(i).getDirection()){
                    if(list.get(i).getDirection().equals("U")) {
                        str[3] = "上行";
                    }if(list.get(i).getDirection().equals("D")){
                        str[3] = "下行";
                    }
                }
                else {
                    str[3] = "";
                }
                if(null!=list.get(i).getStationCode()){
                    str[4] = list.get(i).getStationCode();
                }
                else {
                    str[4] = "";
                }
                if(null!=list.get(i).getDoorFlag()){
                    if(list.get(i).getDoorFlag()==1) {
                        str[5] = "上车";
                }if(list.get(i).getDoorFlag()==2){
                        str[5] = "下车";
                    }
                }
                else {
                    str[5] = "";
                }
                //上下车时间
                if(null!=list.get(i).getDoorFlag() && !list.get(i).getDoorFlag().equals("")){
                    Integer doorFlag = list.get(i).getDoorFlag();
                    if(doorFlag==1){//上车
                        str[6]= DateUtils.format( list.get(i).getStartStationTime(),DateUtils.DATE_TIME_PATTERN);
                    }else if(doorFlag==2){//下车
                        str[6]= DateUtils.format( list.get(i).getEndStaionTime(),DateUtils.DATE_TIME_PATTERN);
                    }
                }
                else {
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
}
