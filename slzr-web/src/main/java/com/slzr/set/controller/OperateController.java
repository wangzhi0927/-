package com.slzr.set.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slzr.common.annotation.Log;
import com.slzr.common.annotation.SystemControllerLog;
import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.JSONUtils;
import com.slzr.common.utils.R;
import com.slzr.set.domain.OperateDo;
import com.slzr.set.domain.SysSetting;
import com.slzr.set.service.OperateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.batik.svggen.SVGStylingAttributes.set;


@RequestMapping("/operate/operateSet")
@Controller
public class OperateController extends BaseController{
    private String  prefix = "set/operate";

    @Autowired
    private OperateService operateService;

    @RequiresPermissions("set:operateSet:operateSet")
    @SystemControllerLog(description="0")
    @GetMapping("")
    String merch(Model model) {
        List<Integer> options=new ArrayList<>();
        for(int i=0;i<32;i++){
            options.add(i);
        }
        model.addAttribute("options", options);
        OperateDo operateDo = operateService.selectOperateDo();
        String setting = operateDo.getSetting();
        JSONObject jsonObject = JSONObject.parseObject(setting);
        SysSetting sysSetting = JSONObject.toJavaObject(jsonObject, SysSetting.class);
        model.addAttribute("setting",sysSetting);
        //将得到的json字符串转成对象
        return prefix + "/operate";
    }

    @Log("更新运营参数")
    @PostMapping("/update")
    @SystemControllerLog(description="2")
    @ResponseBody
    public R save(@RequestParam Map<String,Object> params){
        OperateDo operate=new OperateDo();
        //param对象转为json
        String setting = JSONUtils.beanToJson(params);
        operate.setSetting(setting);
        operate.setUpdatedBy(getId());
        operate.setUpdatedDate(new Date());
        if(operateService.update(operate)>0){
            return R.ok();
        }
        return R.error();
    }

}
