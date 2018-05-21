package com.slzr.ureport.bean;


import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.DateUtils;
import com.slzr.common.utils.TimeConverUtils;
import com.slzr.system.service.UserService;
import com.slzr.ureport.domain.AccountCountDo;
import com.slzr.ureport.service.AccountCountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountCountBean extends BaseController{

    @Autowired
    private AccountCountService accountCountService;

    @Autowired
    private UserService userService;

    public List<AccountCountDo> getListAccount(String dsName, String datasetName, Map<String,Object> parameters){
        String startDate = (String) parameters.get("startDate");
        String endDate = (String) parameters.get("endDate");
        Map<String, Object> stringObjectMap=null;
        if (parameters != null) {
            stringObjectMap = TimeConverUtils.converDate(parameters);
        }
        List<AccountCountDo> list=accountCountService.list(stringObjectMap);

        for (int i=0;i<list.size();i++){
            //判断日期类型为什么
            if(stringObjectMap.get("datetype").toString().equals("Week")){
                list.get(i).setTxnDate(list.get(i).getYear()+"-W"+list.get(i).getTxnDate());
            }
            if(stringObjectMap.get("datetype").toString().equals("Month")){
                list.get(i).setTxnDate(list.get(i).getYear()+"-"+list.get(i).getTxnDate());
            }
            if(stringObjectMap.get("datetype").toString().equals("Quarter")){
                list.get(i).setTxnDate(list.get(i).getYear()+"-Q"+list.get(i).getTxnDate());
            }
            list.get(i).setUserName(getUserName());
            list.get(i).setStartDate(startDate);
            list.get(i).setEndDate(endDate);
            
            list.get(i).setTopupFreeAmount(list.get(i).getTopupFreeAmount() == null ? new BigDecimal(0.00) :  list.get(i).getTopupFreeAmount());
            list.get(i).setTopupFreeNum(list.get(i).getTopupFreeNum() == null ? 0 :  list.get(i).getTopupFreeNum());
        }

        return list;
    }


}
