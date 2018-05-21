package com.slzr.ureport.domain;

import java.math.BigDecimal;


/**
 * 充值统计
 */
public class AccountCountDo {

    private Integer id;
    //year
    private  String year;
    //充值日期
    private  String txnDate;
    //充值方式
    private String payMethod;
    //充值方式ID
    private Integer  payMethodId;
    //充值地点
    private String payAddress;
    //充值笔数
    private Integer topupNum;
    //充值金额
    private BigDecimal topupAmount;

    //充值赠送笔数
    private  Integer topupFreeNum;
    //充值赠送金额
    private  BigDecimal topupFreeAmount;

    private String userName;

    //起始查询日
    private String startDate;

    //终止日
    private  String endDate;

    //商户代码
    private String merChantCode;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(Integer payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getMerChantCode() {
        return merChantCode;
    }

    public void setMerChantCode(String merChantCode) {
        this.merChantCode = merChantCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayAddress() {
        return payAddress;
    }

    public void setPayAddress(String payAddress) {
        this.payAddress = payAddress;
    }

    public Integer getTopupNum() {
        return topupNum;
    }

    public void setTopupNum(Integer topupNum) {
        this.topupNum = topupNum;
    }

    public BigDecimal getTopupAmount() {
        return topupAmount;
    }

    public void setTopupAmount(BigDecimal topupAmount) {
        this.topupAmount = topupAmount;
    }

    public Integer getTopupFreeNum() {
        return topupFreeNum;
    }

    public void setTopupFreeNum(Integer topupFreeNum) {
        this.topupFreeNum = topupFreeNum;
    }

    public BigDecimal getTopupFreeAmount() {
        return topupFreeAmount;
    }

    public void setTopupFreeAmount(BigDecimal topupFreeAmount) {
        this.topupFreeAmount = topupFreeAmount;
    }
}
