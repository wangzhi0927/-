package com.slzr.ureport.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * IC卡圈存统计
 */
public class IcTransferCountDo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//year
	private  String year;
	//TerminalNO
	private String terminalno;
	//卡类
	private String cardtypeid;
	//PayMethodID
	private Integer paymethodid;
	//OrderDate
	private String orderdate;
	//支付金额
	private BigDecimal payamount;
	//支付笔数
	private Integer paynum;
	//未圈存金额
	private BigDecimal untopupamount;
	//未圈存数
	private Integer untopupnum;
	//补圈存金额
	private BigDecimal oldtopupokamount;
	//补圈存数
	private Integer oldtopupoknum;
	//TopupOKAmount
	private BigDecimal topupokamount;
	//TopupOKNum
	private Integer topupoknum;
	//TopupFailAmount
	private BigDecimal topupfailamount;
	//TopupFailNum
	private Integer topupfailnum;
	//补退款金额
	private BigDecimal oldrefundamount;
	//补退款数
	private Integer oldrefundnum;
	//退款金额
	private BigDecimal refundamount;
	//退款数
	private Integer refundnum;
	//结算日期
	private String summarydatetime;
	
    //当前登录的用户
    private String userName;

    //起始查询日
    private String startDate;
    //终止日
    private  String endDate;
    
    

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：TerminalNO
	 */
	public void setTerminalno(String terminalno) {
		this.terminalno = terminalno;
	}
	/**
	 * 获取：TerminalNO
	 */
	public String getTerminalno() {
		return terminalno;
	}
	/**
	 * 设置：CardTypeID
	 */
	public void setCardtypeid(String cardtypeid) {
		this.cardtypeid = cardtypeid;
	}
	/**
	 * 获取：CardTypeID
	 */
	public String getCardtypeid() {
		return cardtypeid;
	}
	/**
	 * 设置：PayMethodID
	 */
	public void setPaymethodid(Integer paymethodid) {
		this.paymethodid = paymethodid;
	}
	/**
	 * 获取：PayMethodID
	 */
	public Integer getPaymethodid() {
		return paymethodid;
	}
	/**
	 * 设置：OrderDate
	 */
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
	/**
	 * 获取：OrderDate
	 */
	public String getOrderdate() {
		return orderdate;
	}
	/**
	 * 设置：PayAmount
	 */
	public void setPayamount(BigDecimal payamount) {
		this.payamount = payamount;
	}
	/**
	 * 获取：PayAmount
	 */
	public BigDecimal getPayamount() {
		return payamount;
	}
	/**
	 * 设置：PayNum
	 */
	public void setPaynum(Integer paynum) {
		this.paynum = paynum;
	}
	/**
	 * 获取：PayNum
	 */
	public Integer getPaynum() {
		return paynum;
	}
	/**
	 * 设置：UnTopupAmount
	 */
	public void setUntopupamount(BigDecimal untopupamount) {
		this.untopupamount = untopupamount;
	}
	/**
	 * 获取：UnTopupAmount
	 */
	public BigDecimal getUntopupamount() {
		return untopupamount;
	}
	/**
	 * 设置：UnTopupNum
	 */
	public void setUntopupnum(Integer untopupnum) {
		this.untopupnum = untopupnum;
	}
	/**
	 * 获取：UnTopupNum
	 */
	public Integer getUntopupnum() {
		return untopupnum;
	}
	/**
	 * 设置：OldTopupOKAmount
	 */
	public void setOldtopupokamount(BigDecimal oldtopupokamount) {
		this.oldtopupokamount = oldtopupokamount;
	}
	/**
	 * 获取：OldTopupOKAmount
	 */
	public BigDecimal getOldtopupokamount() {
		return oldtopupokamount;
	}
	/**
	 * 设置：OldTopupOKNum
	 */
	public void setOldtopupoknum(Integer oldtopupoknum) {
		this.oldtopupoknum = oldtopupoknum;
	}
	/**
	 * 获取：OldTopupOKNum
	 */
	public Integer getOldtopupoknum() {
		return oldtopupoknum;
	}
	/**
	 * 设置：TopupOKAmount
	 */
	public void setTopupokamount(BigDecimal topupokamount) {
		this.topupokamount = topupokamount;
	}
	/**
	 * 获取：TopupOKAmount
	 */
	public BigDecimal getTopupokamount() {
		return topupokamount;
	}
	/**
	 * 设置：TopupOKNum
	 */
	public void setTopupoknum(Integer topupoknum) {
		this.topupoknum = topupoknum;
	}
	/**
	 * 获取：TopupOKNum
	 */
	public Integer getTopupoknum() {
		return topupoknum;
	}
	/**
	 * 设置：TopupFailAmount
	 */
	public void setTopupfailamount(BigDecimal topupfailamount) {
		this.topupfailamount = topupfailamount;
	}
	/**
	 * 获取：TopupFailAmount
	 */
	public BigDecimal getTopupfailamount() {
		return topupfailamount;
	}
	/**
	 * 设置：TopupFailNum
	 */
	public void setTopupfailnum(Integer topupfailnum) {
		this.topupfailnum = topupfailnum;
	}
	/**
	 * 获取：TopupFailNum
	 */
	public Integer getTopupfailnum() {
		return topupfailnum;
	}
	/**
	 * 设置：OldRefundAmount
	 */
	public void setOldrefundamount(BigDecimal oldrefundamount) {
		this.oldrefundamount = oldrefundamount;
	}
	/**
	 * 获取：OldRefundAmount
	 */
	public BigDecimal getOldrefundamount() {
		return oldrefundamount;
	}
	/**
	 * 设置：OldRefundNum
	 */
	public void setOldrefundnum(Integer oldrefundnum) {
		this.oldrefundnum = oldrefundnum;
	}
	/**
	 * 获取：OldRefundNum
	 */
	public Integer getOldrefundnum() {
		return oldrefundnum;
	}
	/**
	 * 设置：RefundAmount
	 */
	public void setRefundamount(BigDecimal refundamount) {
		this.refundamount = refundamount;
	}
	/**
	 * 获取：RefundAmount
	 */
	public BigDecimal getRefundamount() {
		return refundamount;
	}
	/**
	 * 设置：RefundNum
	 */
	public void setRefundnum(Integer refundnum) {
		this.refundnum = refundnum;
	}
	/**
	 * 获取：RefundNum
	 */
	public Integer getRefundnum() {
		return refundnum;
	}
	/**
	 * 设置：SummaryDateTime
	 */
	public String getSummarydatetime() {
		return summarydatetime;
	}

	public void setSummarydatetime(String summarydatetime) {
		this.summarydatetime = summarydatetime;
	}
}
