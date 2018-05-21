package com.slzr.operation.domain;

public class TopupTransDO {
    private Object iD;
    private String payResultMsg;
    private String accountNO;
    private String orderNO;
    private Double txnAmount;
    private java.util.Date settleDateTime;
    private Integer payClientID;
    private Integer uID;
    private java.util.Date txnDateTime;
    private Integer counter;
    private java.util.Date summaryDateTime;
    private Integer payMethodId;
    private String tAC;
    private java.util.Date refundDateTime;
    private java.util.Date payTime;
    private Integer payResult;
    private Double balance;
    private Double freeAmount;

    public Double getFreeAmount() {
        return freeAmount;
    }

    public void setFreeAmount(Double freeAmount) {
        this.freeAmount = freeAmount;
    }

    public Object getID() {
        return this.iD;
    }

    public void setID(Object iD) {
        this.iD = iD;
    }

    public String getPayResultMsg() {
        return this.payResultMsg;
    }

    public void setPayResultMsg(String payResultMsg) {
        this.payResultMsg = payResultMsg;
    }

    public String getAccountNO() {
        return this.accountNO;
    }

    public void setAccountNO(String accountNO) {
        this.accountNO = accountNO;
    }

    public String getOrderNO() {
        return this.orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public Double getTxnAmount() {
        return this.txnAmount;
    }

    public void setTxnAmount(Double txnAmount) {
        this.txnAmount = txnAmount;
    }

    public java.util.Date getSettleDateTime() {
        return this.settleDateTime;
    }

    public void setSettleDateTime(java.util.Date settleDateTime) {
        this.settleDateTime = settleDateTime;
    }

    public Integer getPayClientID() {
        return this.payClientID;
    }

    public void setPayClientID(Integer payClientID) {
        this.payClientID = payClientID;
    }

    public Integer getUID() {
        return this.uID;
    }

    public void setUID(Integer uID) {
        this.uID = uID;
    }

    public java.util.Date getTxnDateTime() {
        return this.txnDateTime;
    }

    public void setTxnDateTime(java.util.Date txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public Integer getCounter() {
        return this.counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public java.util.Date getSummaryDateTime() {
        return this.summaryDateTime;
    }

    public void setSummaryDateTime(java.util.Date summaryDateTime) {
        this.summaryDateTime = summaryDateTime;
    }

    public Integer getPayMethodId() {
        return this.payMethodId;
    }

    public void setPayMethodId(Integer payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getTAC() {
        return this.tAC;
    }

    public void setTAC(String tAC) {
        this.tAC = tAC;
    }

    public java.util.Date getRefundDateTime() {
        return this.refundDateTime;
    }

    public void setRefundDateTime(java.util.Date refundDateTime) {
        this.refundDateTime = refundDateTime;
    }

    public java.util.Date getPayTime() {
        return this.payTime;
    }

    public void setPayTime(java.util.Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayResult() {
        return this.payResult;
    }

    public void setPayResult(Integer payResult) {
        this.payResult = payResult;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
