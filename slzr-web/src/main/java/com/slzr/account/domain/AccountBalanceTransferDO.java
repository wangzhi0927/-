package com.slzr.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


 
public class AccountBalanceTransferDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;

	//申请人UID
	private Long applyUid;
	//申请时间
	private Date applyTime;
	//账户余额
	private BigDecimal accountBalance;
	//转赠金额
	private BigDecimal transferAmount;
	//服务费
	private BigDecimal serviceFee;
	//审核UID
	private Long auditUid;
	//审核结果 -1：未审核   0：审核不通过    1：审核通过.
	private Integer auditResult;
	//审核时间
	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date auditDateTime;
	//审核消息描述
	private String auditMsg;
	//用户确认结果 (1:已确认)
	private Integer userConfirmResult;
	//用户确认时间
	private Date userConfirmDateTime;
	//日结汇总时间
	private Date summaryDateTime;
	
	//转赠账户
	private String fromAccountNo;
	//被转赠账户
	private String toAccountNo;
	//申请人 微信昵称
	private String applyNickName;
	
	private String merchantCode;
	
	private String auditUserName;

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getApplyUid() {
		return applyUid;
	}

	public void setApplyUid(Long applyUid) {
		this.applyUid = applyUid;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Long getAuditUid() {
		return auditUid;
	}

	public void setAuditUid(Long auditUid) {
		this.auditUid = auditUid;
	}

	public Integer getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(Integer auditResult) {
		this.auditResult = auditResult;
	}

	public Date getAuditDateTime() {
		return auditDateTime;
	}

	public void setAuditDateTime(Date auditDateTime) {
		this.auditDateTime = auditDateTime;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public Integer getUserConfirmResult() {
		return userConfirmResult;
	}

	public void setUserConfirmResult(Integer userConfirmResult) {
		this.userConfirmResult = userConfirmResult;
	}

	public Date getUserConfirmDateTime() {
		return userConfirmDateTime;
	}

	public void setUserConfirmDateTime(Date userConfirmDateTime) {
		this.userConfirmDateTime = userConfirmDateTime;
	}

	public Date getSummaryDateTime() {
		return summaryDateTime;
	}

	public void setSummaryDateTime(Date summaryDateTime) {
		this.summaryDateTime = summaryDateTime;
	}

	public String getFromAccountNo() {
		return fromAccountNo;
	}

	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}

	public String getToAccountNo() {
		return toAccountNo;
	}

	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}

	public String getApplyNickName() {
		return applyNickName;
	}

	public void setApplyNickName(String applyNickName) {
		this.applyNickName = applyNickName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	
	
	
	
	
	
	
	
	
}
