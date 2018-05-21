package com.slzr.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 账户注销管理
 * @author lc
 * @date 2018-03-08 16:45:51
 */
public class AccountCancelDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//商户编码,用于区分不同县城
	private String merchantcode;
	//二维码账号
	private String accountno;
	//申请时间
	private Date submittime;
	//用户ID
	private Integer uid;
	//电话
	private String mobilephone;
	
	//服务费
	private BigDecimal servicefee;
	
	//处理结果(审核状态)  -1：未审核   0：审核不通过    1：审核通过.
	private Integer dealresult;
	
	//处理时间 (审核时间)
	private Date dealtime;
	
	//处理意见 (审核意见)
	private String dealmsg;
	
	//用户确认状态(1:已确认)
	private Integer userconfirm;
	
	//用户确认时间
	private Date userconfirmtime;
	
	//
	private Date summarydatetime;

	//类型
	private int accounttype;
	
	//状态
	private int state;

	//账户余额
	private BigDecimal abalance;
	
	//可退金额
	private BigDecimal balance;

	
	//退款状态 		1：已退款   0：未退款	
	private int refundresult;
	
	//退款时间
	private Date refundtime;
	
	//处理人
	private Long dealuid;
	
	
	private String AccountTypeName;
	private String StateName;
	
	

	public String getAccountTypeName() {
		return AccountTypeName;
	}


	public void setAccountTypeName(String accountTypeName) {
		AccountTypeName = accountTypeName;
	}


	public String getStateName() {
		return StateName;
	}


	public void setStateName(String stateName) {
		StateName = stateName;
	}


	public Long getDealuid() {
		return dealuid;
	}


	public void setDealuid(Long dealuid) {
		this.dealuid = dealuid;
	}


	public AccountCancelDO() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getMerchantcode() {
		return merchantcode;
	}


	public void setMerchantcode(String merchantcode) {
		this.merchantcode = merchantcode;
	}


	public String getAccountno() {
		return accountno;
	}


	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}


	public Date getSubmittime() {
		return submittime;
	}


	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}


	public Integer getUid() {
		return uid;
	}


	public void setUid(Integer uid) {
		this.uid = uid;
	}


	public String getMobilephone() {
		return mobilephone;
	}


	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}




	public BigDecimal getServicefee() {
		return servicefee;
	}


	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}


	public Integer getDealresult() {
		return dealresult;
	}


	public void setDealresult(Integer dealresult) {
		this.dealresult = dealresult;
	}


	public Date getDealtime() {
		return dealtime;
	}


	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}


	public String getDealmsg() {
		return dealmsg;
	}


	public void setDealmsg(String dealmsg) {
		this.dealmsg = dealmsg;
	}


	public Integer getUserconfirm() {
		return userconfirm;
	}


	public void setUserconfirm(Integer userconfirm) {
		this.userconfirm = userconfirm;
	}


	public Date getUserconfirmtime() {
		return userconfirmtime;
	}


	public void setUserconfirmtime(Date userconfirmtime) {
		this.userconfirmtime = userconfirmtime;
	}


	public Date getSummarydatetime() {
		return summarydatetime;
	}


	public void setSummarydatetime(Date summarydatetime) {
		this.summarydatetime = summarydatetime;
	}


	public int getAccounttype() {
		return accounttype;
	}


	public void setAccounttype(int accounttype) {
		this.accounttype = accounttype;
	}


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}


	public BigDecimal getAbalance() {
		return abalance;
	}


	public void setAbalance(BigDecimal abalance) {
		this.abalance = abalance;
	}


 


	public BigDecimal getBalance() {
		return balance;
	}


	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}


	public Date getRefundtime() {
		return refundtime;
	}


	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}


	public int getRefundresult() {
		return refundresult;
	}


	public void setRefundresult(int refundresult) {
		this.refundresult = refundresult;
	}


	public AccountCancelDO(Integer id, String merchantcode, String accountno, Date submittime, Integer uid,
			String mobilephone, BigDecimal servicefee, Integer dealresult, Date dealtime, String dealmsg,
			Integer userconfirm, Date userconfirmtime, Date summarydatetime, int accounttype, int state,
			BigDecimal abalance, BigDecimal balance, int refundresult, Date refundtime) {
		super();
		this.id = id;
		this.merchantcode = merchantcode;
		this.accountno = accountno;
		this.submittime = submittime;
		this.uid = uid;
		this.mobilephone = mobilephone;
		this.servicefee = servicefee;
		this.dealresult = dealresult;
		this.dealtime = dealtime;
		this.dealmsg = dealmsg;
		this.userconfirm = userconfirm;
		this.userconfirmtime = userconfirmtime;
		this.summarydatetime = summarydatetime;
		this.accounttype = accounttype;
		this.state = state;
		this.abalance = abalance;
		this.balance = balance;
		this.refundresult = refundresult;
		this.refundtime = refundtime;
	}






	
	
	
	
	
	
}
