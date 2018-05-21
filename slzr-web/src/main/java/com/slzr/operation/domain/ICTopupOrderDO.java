package com.slzr.operation.domain;

import java.util.Date;

public class ICTopupOrderDO {

    private String iD;
    private String uID;
    private String terminalNO;
    private String orderNO;
    private String cardNO;
    private String cardTypeID;
    private String counter;
    private float balance;
    private float txnAmount;
    private Date txnDateTime;
    private String orderState;
    private String orderDescribe;
    private String orderBackDate;
    private String loadState;
    private String loadDescribe;
    private String loadBackDate;

    private String payMethodId;
    private String payTime;
    private String payResult;
    private String pPayResultMsg;
    private String tAC;
    private String reviewID;
    private String reviewTime;
    private String summaryTime;
    private String mAC;


    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getTerminalNO() {
        return terminalNO;
    }

    public void setTerminalNO(String terminalNO) {
        this.terminalNO = terminalNO;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
    }

    public String getCardTypeID() {
        return cardTypeID;
    }

    public void setCardTypeID(String cardTypeID) {
        this.cardTypeID = cardTypeID;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(float txnAmount) {
        this.txnAmount = txnAmount;
    }

    public Date getTxnDateTime() {
        return txnDateTime;
    }

    public void setTxnDateTime(Date txnDateTime) {
        this.txnDateTime = txnDateTime;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderDescribe() {
        return orderDescribe;
    }

    public void setOrderDescribe(String orderDescribe) {
        this.orderDescribe = orderDescribe;
    }

    public String getOrderBackDate() {
        return orderBackDate;
    }

    public void setOrderBackDate(String orderBackDate) {
        this.orderBackDate = orderBackDate;
    }

    public String getLoadState() {
        return loadState;
    }

    public void setLoadState(String loadState) {
        this.loadState = loadState;
    }

    public String getLoadDescribe() {
        return loadDescribe;
    }

    public void setLoadDescribe(String loadDescribe) {
        this.loadDescribe = loadDescribe;
    }

    public String getLoadBackDate() {
        return loadBackDate;
    }

    public void setLoadBackDate(String loadBackDate) {
        this.loadBackDate = loadBackDate;
    }

    public String getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(String payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getpPayResultMsg() {
        return pPayResultMsg;
    }

    public void setpPayResultMsg(String pPayResultMsg) {
        this.pPayResultMsg = pPayResultMsg;
    }

    public String gettAC() {
        return tAC;
    }

    public void settAC(String tAC) {
        this.tAC = tAC;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getSummaryTime() {
        return summaryTime;
    }

    public void setSummaryTime(String summaryTime) {
        this.summaryTime = summaryTime;
    }

    public String getmAC() {
        return mAC;
    }

    public void setmAC(String mAC) {
        this.mAC = mAC;
    }
}
