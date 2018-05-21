package com.slzr.ureport.domain;

import java.math.BigDecimal;

public class BCountDo {
	/**
	 * 二级统计 
	 * @author Administrator
	 *
	 */
    private static final long serialVersionUID = 1L;
    private Long id;
    //年
    private  String year;
    //日期
    private String txnDate;
    //部門
    private  String deptName;
    //線路
    private String lineName;
    //車輛編號
    private  String busNO;

    //司机名字
    private  String driverName;

    //消費人次
    private String consumptionPerson;
    //消費金額
    private BigDecimal consumptionAmount;
    //赠送消费人次
    private Integer debitFreeNum;
    //赠送消费金额
    private  BigDecimal debitFreeAmount;
    //当前登录的用户
    private String userName;

    //起始查询日
    private String startDate;
    //终止日
    private  String endDate;

    //商户代码
    private String merChantCode;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getBusNO() {
        return busNO;
    }

    public void setBusNO(String busNO) {
        this.busNO = busNO;
    }

    public String getConsumptionPerson() {
        return consumptionPerson;
    }

    public void setConsumptionPerson(String consumptionPerson) {
        this.consumptionPerson = consumptionPerson;
    }

    public BigDecimal getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(BigDecimal consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public Integer getDebitFreeNum() {
        return debitFreeNum;
    }

    public void setDebitFreeNum(Integer debitFreeNum) {
        this.debitFreeNum = debitFreeNum;
    }

    public BigDecimal getDebitFreeAmount() {
        return debitFreeAmount;
    }

    public void setDebitFreeAmount(BigDecimal debitFreeAmount) {
        this.debitFreeAmount = debitFreeAmount;
    }
}
