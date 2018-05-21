package com.slzr.operation.domain;

import java.math.BigDecimal;

public class DailysummaryTopupDO {
    private Object iD;
    private String auditRemark;
    private String txnDate;
    private BigDecimal refundAmount;
    private Integer topupNum;
    private Integer refundNum;
    private BigDecimal serviceFee;
    private Integer auditUID;
    private Integer auditStatus;
    private java.util.Date auditDateTime;
    private java.util.Date summaryDateTime;
    private Integer payMethodId;
    private BigDecimal topupAmount;
    private String auditUserName;
    private BigDecimal enterAmount;
    private String summaryDateTimeText;
	public Object getiD() {
		return iD;
	}
	public void setiD(Object iD) {
		this.iD = iD;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Integer getTopupNum() {
		return topupNum;
	}
	public void setTopupNum(Integer topupNum) {
		this.topupNum = topupNum;
	}
	public Integer getRefundNum() {
		return refundNum;
	}
	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}
	public BigDecimal getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}
	public Integer getAuditUID() {
		return auditUID;
	}
	public void setAuditUID(Integer auditUID) {
		this.auditUID = auditUID;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public java.util.Date getAuditDateTime() {
		return auditDateTime;
	}
	public void setAuditDateTime(java.util.Date auditDateTime) {
		this.auditDateTime = auditDateTime;
	}
	public java.util.Date getSummaryDateTime() {
		return summaryDateTime;
	}
	public void setSummaryDateTime(java.util.Date summaryDateTime) {
		this.summaryDateTime = summaryDateTime;
	}
	public Integer getPayMethodId() {
		return payMethodId;
	}
	public void setPayMethodId(Integer payMethodId) {
		this.payMethodId = payMethodId;
	}
	public BigDecimal getTopupAmount() {
		return topupAmount;
	}
	public void setTopupAmount(BigDecimal topupAmount) {
		this.topupAmount = topupAmount;
	}
	public String getAuditUserName() {
		return auditUserName;
	}
	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}
	public BigDecimal getEnterAmount() {
		return enterAmount;
	}
	public void setEnterAmount(BigDecimal enterAmount) {
		this.enterAmount = enterAmount;
	}
	public String getSummaryDateTimeText() {
		return summaryDateTimeText;
	}
	public void setSummaryDateTimeText(String summaryDateTimeText) {
		this.summaryDateTimeText = summaryDateTimeText;
	}
}
