package com.slzr.operation.service.Impl;

import com.slzr.common.controller.BaseController;
import com.slzr.common.utils.DateUtils;
import com.slzr.operation.dao.SendCaseDao;
import com.slzr.operation.domain.SendCaseDo;
import com.slzr.operation.service.SendCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SendCaseServiceImpl extends BaseController implements SendCaseService{

    @Autowired
    private SendCaseDao sendCaseDao;
    @Override
    public List<SendCaseDo> list(Map<String, Object> map) {
        List<SendCaseDo> list = sendCaseDao.list(map);
        for (SendCaseDo sendCase:list){
            sendCase.setEffectiveDateTime(DateUtils.format( DateUtils.ConvertToDate(sendCase.getEffectiveDateTime()), DateUtils.DATE_PATTERN));
            sendCase.setExpiredDateTime(DateUtils.format( DateUtils.ConvertToDate(sendCase.getExpiredDateTime()), DateUtils.DATE_PATTERN));
        }
        return sendCaseDao.list(map);
    }
    @Override
    public List<SendCaseDo> lists(Map<String, Object> map) {
        return sendCaseDao.lists(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sendCaseDao.count(map);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int save(Map<String, Object> params) {

        String  content = params.get("content").toString();
        if(content.indexOf("fromAmount")>0) {
            String head = content.substring(0, content.indexOf("&!fromAmount"));

            String end = content.substring(content.indexOf("fromAmount"), content.length());

            List<String> fromDate = new ArrayList<>();
            List<String> toDate = new ArrayList<>();

            String[] ends = end.split("&!");
            for (int i = 0; i < ends.length; i++) {
                String fromAmount = ends[i].substring(ends[i].indexOf("fromAmount=") + 11, ends[i].indexOf("&toAmount"));
                String toAmount = ends[i].substring(ends[i].indexOf("toAmount=") + 9, ends[i].indexOf("&discountAmount"));
                String discountAmount = ends[i].substring(ends[i].indexOf("discountAmount=") + 15, ends[i].length());
                Matcher matcher1 = Pattern.compile("^[1-9]\\d*$").matcher(fromAmount);
                Matcher matcher2 = Pattern.compile("^[1-9]\\d*$").matcher(toAmount);
                Matcher matcher3 = Pattern.compile("^[1-9]\\d*%$").matcher(discountAmount);
                Matcher matcher4 = Pattern.compile("^[1-9]\\d*$").matcher(discountAmount);

                //输入价格为空
                if (toAmount.equals("") || fromAmount.equals("") || discountAmount.equals("")) {
                    return -2;
                }
                if(discountAmount.indexOf("%")>0) {
                    if (!matcher1.find() || !matcher2.find() || !matcher3.find()) {
                        return -1;
                    }
                }else{
                    if (!matcher1.find() || !matcher2.find()|| !matcher4.find()) {
                        return -1;
                    }
                }                //输入的价格区间有误
                if (Integer.parseInt(toAmount) < Integer.parseInt(fromAmount)) {
                    return -3;
                }
                fromDate.add(fromAmount);
                toDate.add(toAmount);
            }
            if (fromDate.size() > 1) {
                for (int i = 0; i < fromDate.size() - 1; i++) {
                    if (Integer.parseInt(fromDate.get(i + 1)) <= Integer.parseInt(toDate.get(i))) {
                        return -3;
                    }
                }
            }

            String merchantCode = head.substring(head.indexOf("merchantCode=") + 13, head.lastIndexOf("&discountname"));
            String discountname = head.substring(head.indexOf("discountname=") + 13, head.lastIndexOf("&discountdesc"));
            String discountdesc = head.substring(head.indexOf("discountdesc=") + 13, head.lastIndexOf("&effectivedatetime"));
            String effectivedatetime = head.substring(head.indexOf("effectivedatetime=") + 18, head.lastIndexOf("&expireddatetime"));
            String expireddatetime = head.substring(head.indexOf("expireddatetime=") + 16, head.lastIndexOf("&enabled"));
            String enabled = head.substring(head.indexOf("enabled=") + 8, head.length());
            //添加之前首先看是否启用
            Integer id = 0;
            if (enabled.equals("0")) {
                SendCaseDo sendCase1 = new SendCaseDo();
                sendCase1.setMerchantCode(merchantCode);
                sendCase1.setDiscountName(discountname);
                sendCase1.setDiscountDesc(discountdesc);
                sendCase1.setEffectiveDateTime(effectivedatetime);
                sendCase1.setExpiredDateTime(expireddatetime);
                sendCase1.setCreatedBy(new Long(getId()).intValue());
                sendCase1.setCreatedDateTime(new Date());
                sendCase1.setEnabled(enabled);
                sendCaseDao.save1(sendCase1);
                id = sendCase1.getId();
            } else {
                //先将其他启用的状态进行修改
                Map<String, Object> map = new HashMap<>();
                map.put("merchantCode", merchantCode);
                sendCaseDao.updateUnEnable(map);
                //再将启用的方案添加进去
                SendCaseDo sendCase1 = new SendCaseDo();
                sendCase1.setMerchantCode(merchantCode);
                sendCase1.setDiscountName(discountname);
                sendCase1.setDiscountDesc(discountdesc);
                sendCase1.setEffectiveDateTime(effectivedatetime);
                sendCase1.setExpiredDateTime(expireddatetime);
                sendCase1.setCreatedBy(new Long(getId()).intValue());
                sendCase1.setCreatedDateTime(new Date());
                sendCase1.setEnabled(enabled);
                sendCaseDao.save1(sendCase1);
                id = sendCase1.getId();
            }

            //将本条数据存储之后返回一个ID

            int count = 0;
            for (int i = 0; i < ends.length; i++) {
                SendCaseDo sendCase2 = new SendCaseDo();
                String fromAmount = ends[i].substring(ends[i].indexOf("fromAmount=") + 11, ends[i].indexOf("&toAmount"));
                String toAmount = ends[i].substring(ends[i].indexOf("toAmount=") + 9, ends[i].indexOf("&discountAmount"));
                String discountAmount = ends[i].substring(ends[i].indexOf("discountAmount=") + 15, ends[i].length());
                if (discountAmount.indexOf("%") > 0) {
                    String dis1 = discountAmount.substring(0, discountAmount.length() - 1);
                    String dis2 = discountAmount.substring(discountAmount.length() - 1, discountAmount.length());
                    sendCase2.setDiscountAmount(Integer.parseInt(dis1));
                    sendCase2.setDiscountAmountUnit(dis2);
                } else {
                    sendCase2.setDiscountAmount(Integer.parseInt(discountAmount));
                }
                sendCase2.setDiscountSettingID(id);
                sendCase2.setFromAmount(Integer.parseInt(fromAmount));
                sendCase2.setToAmount(Integer.parseInt(toAmount));

                count = sendCaseDao.save2(sendCase2);
            }
            return count;
        }
        return -4;

    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int update(Map<String, Object> params) {

        String  content = params.get("content").toString();
        if(content.indexOf("fromAmount")>0) {
            String end = content.substring(content.indexOf("fromAmount"), content.length());
            String head = content.substring(0, content.indexOf("&!fromAmount"));
            String[] ends = end.split("&!");
            List<String> fromDate = new ArrayList<>();
            List<String> toDate = new ArrayList<>();
            for (int i = 0; i < ends.length; i++) {
                String end1 = ends[i];
                String fromAmount = end1.substring(end1.indexOf("fromAmount=") + 11, end1.indexOf("&toAmount"));
                String toAmount = end1.substring(end1.indexOf("toAmount=") + 9, end1.indexOf("&discountAmount"));
                String discountAmount = end1.substring(end1.indexOf("discountAmount=") + 15, end1.indexOf("&pid"));
                Matcher matcher = Pattern.compile("^[1-9]\\d*$").matcher(fromAmount);
                Matcher matcher1 = Pattern.compile("^[1-9]\\d*$").matcher(toAmount);

                Matcher matcher3 = Pattern.compile("^[1-9]\\d*%$").matcher(discountAmount);
                Matcher matcher4 = Pattern.compile("^[1-9]\\d*$").matcher(discountAmount);
                //输入价格为空或者不合法

                if (toAmount.equals("") || fromAmount.equals("") || discountAmount.equals("")) {
                    return -2;
                }
                if(discountAmount.indexOf("%")>0) {
                    if (!matcher.find() || !matcher1.find() || !matcher3.find()) {
                        return -1;
                    }
                }else {
                    if (!matcher.find() || !matcher1.find()|| !matcher4.find()) {
                        return -1;
                    }
                }                //输入的价格区间有误
                if (Integer.parseInt(toAmount) < Integer.parseInt(fromAmount)) {
                    return -3;
                }
                fromDate.add(fromAmount);
                toDate.add(toAmount);
            }
            if (fromDate.size() > 1) {
                for (int i = 0; i < fromDate.size() - 1; i++) {
                    if (Integer.parseInt(fromDate.get(i + 1)) <= Integer.parseInt(toDate.get(i))) {
                        return -3;
                    }
                }
            }
            String id = head.substring(head.indexOf("id=") + 3, head.lastIndexOf("&merchantCode"));
            String merchantCode = head.substring(head.indexOf("merchantCode=") + 13, head.lastIndexOf("&discountName"));
            String discountname = head.substring(head.indexOf("discountName=") + 13, head.lastIndexOf("&discountDesc"));
            String discountdesc = head.substring(head.indexOf("discountDesc=") + 13, head.lastIndexOf("&effectiveDateTime"));
            String effectivedatetime = head.substring(head.indexOf("effectiveDateTime=") + 18, head.lastIndexOf("&expiredDateTime"));
            String expireddatetime = head.substring(head.indexOf("expiredDateTime=") + 16, head.lastIndexOf("&enabled"));
            String enabled = head.substring(head.indexOf("enabled=") + 8, head.length());
            if (enabled.equals("0")) {

                SendCaseDo sendCase1 = new SendCaseDo();
                sendCase1.setMerchantCode(merchantCode);
                sendCase1.setDiscountName(discountname);
                sendCase1.setDiscountDesc(discountdesc);
                sendCase1.setEffectiveDateTime(effectivedatetime);
                sendCase1.setExpiredDateTime(expireddatetime);
                sendCase1.setCreatedBy(new Long(getId()).intValue());
                sendCase1.setCreatedDateTime(new Date());
                sendCase1.setEnabled(enabled);
                sendCaseDao.update1(sendCase1);

            } else {
                //先将其他启用的状态进行修改
                Map<String, Object> map = new HashMap<>();
                map.put("merchantCode", merchantCode);
                sendCaseDao.updateUnEnable(map);
                //在进行添加

                SendCaseDo sendCase1 = new SendCaseDo();
                sendCase1.setId(Integer.parseInt(id));
                sendCase1.setMerchantCode(merchantCode);
                sendCase1.setDiscountName(discountname);
                sendCase1.setDiscountDesc(discountdesc);
                sendCase1.setEffectiveDateTime(effectivedatetime);
                sendCase1.setExpiredDateTime(expireddatetime);
                sendCase1.setCreatedBy(new Long(getId()).intValue());
                sendCase1.setCreatedDateTime(new Date());
                sendCase1.setEnabled(enabled);
                sendCaseDao.update1(sendCase1);
            }

            int count = 0;
            for (int i = 0; i < ends.length; i++) {
                String end1 = ends[i];
                String fromAmount = end1.substring(end1.indexOf("fromAmount=") + 11, end1.indexOf("&toAmount"));
                String toAmount = end1.substring(end1.indexOf("toAmount=") + 9, end1.indexOf("&discountAmount"));
                String discountAmount = end1.substring(end1.indexOf("discountAmount=") + 15, end1.indexOf("&pid"));
                String pid = end1.substring(end1.indexOf("pid=") + 4, end1.length());
                //这是需要更新的价格区间
                if (pid != null && !pid.equals("")) {
                    SendCaseDo sendCase2 = new SendCaseDo();
                    //这个id为价格区间的id
                    sendCase2.setPid(Integer.parseInt(pid));
                    sendCase2.setFromAmount(Integer.parseInt(fromAmount));
                    sendCase2.setToAmount(Integer.parseInt(toAmount));
                    if (discountAmount.indexOf("%") > 0) {
                        String dis1 = discountAmount.substring(0, discountAmount.length() - 1);
                        String dis2 = discountAmount.substring(discountAmount.length() - 1, discountAmount.length());
                        sendCase2.setDiscountAmount(Integer.parseInt(dis1));
                        sendCase2.setDiscountAmountUnit(dis2);
                    } else {
                        //这个编辑是未含有%，需要将原discountAnountUtit置空
                        sendCase2.setDiscountAmountUnit("");
                        sendCase2.setDiscountAmount(Integer.parseInt(discountAmount));
                    }
                    sendCaseDao.update2(sendCase2);
                } else {
                    //这是新增的价格区间
                    SendCaseDo sendCase3 = new SendCaseDo();
                    sendCase3.setDiscountSettingID(Integer.parseInt(id));
                    sendCase3.setFromAmount(Integer.parseInt(fromAmount));
                    sendCase3.setToAmount(Integer.parseInt(toAmount));
                    if (discountAmount.matches("%")) {
                        String dis1 = discountAmount.substring(0, discountAmount.length() - 1);
                        String dis2 = discountAmount.substring(discountAmount.length() - 1, discountAmount.length());
                        sendCase3.setDiscountAmount(Integer.parseInt(dis1));
                        sendCase3.setDiscountAmountUnit(dis2);
                    } else {
                        sendCase3.setDiscountAmount(Integer.parseInt(discountAmount));
                    }
                    count = sendCaseDao.save2(sendCase3);
                }
            }
            return count;
        }
        return -4;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public int remove(Integer id) {
        //删除优惠方案表
        sendCaseDao.remove1(id);
        //将该方案的指定pid删除
        int result = sendCaseDao.remove2(id);
        return result;
    }

    /**
     * 启用优惠方案
     * @param
     * @return
     */
    @Override
    public int updateEnable(Integer id) {
        //得到该商户所有方案
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<SendCaseDo> list = sendCaseDao.list(map);
        String merchantCode="";
        for(SendCaseDo sendCaseDo :list){
              merchantCode = sendCaseDo.getMerchantCode();
        }
        //通过商户code将其所有开启状态为1的变更为’0‘
        map.put("merchantCode",merchantCode);
        sendCaseDao.updateUnEnable(map);
        //再将其所属id的优惠方案修改为1（激活状态）
        int result = sendCaseDao.updateEnable(map);

        return result;
    }

    @Override
    public int updateUnEnable(Integer id) {
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<SendCaseDo> list = sendCaseDao.list(map);
        String merchantCode="";
        for(SendCaseDo sendCaseDo :list){
            merchantCode = sendCaseDo.getMerchantCode();
        }
        //通过商户code将其所有开启状态为1的变更为’0‘
        map.put("merchantCode",merchantCode);
        int result = sendCaseDao.updateUnEnable(map);

        return result;
    }


}
