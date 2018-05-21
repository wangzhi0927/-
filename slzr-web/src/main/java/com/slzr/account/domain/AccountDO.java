package com.slzr.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 账户管理
 * @author lc
 * @date 2018-03-18 16:45:51
 */
public class AccountDO implements Serializable {
	private static long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//商户代码
	@JSONField  (ordinal = 1,name = "MerchantCode")
	private String merchantcode;
	//用户ID
	@JSONField  (ordinal = 2,name = "UID")
	private Integer uid;
	//账号类型，数据字典
	@JSONField  (ordinal = 3,name = "AccountType")
	private Integer accounttype;
	//二维码账号
	@JSONField  (ordinal = 4,name = "AccountNO") 
	private String accountno;
	//手机号码
	@JSONField  (ordinal = 5,name = "MobilePhone") 
	private String mobilephone;
	//真实姓名
	private String truename;
	//性别
	private String gender;
	//身份证号
	private String certno;
	//卡类型代码，数据字典
	@JSONField  (ordinal = 6,name = "CardTypeCode") 
	private Integer cardtypecode;
	//卡子类型代码，数据字典
	@JSONField  (ordinal = 7,name = "SubCardTypeCode") 
	private Integer subcardtypecode;
	//账号状态(-4注销中  -3:注销  -2:账户异常  -1:被锁定  0:未审核  1:审核通过)
	private Integer state;
	//最大消费金额
	private BigDecimal maxtxnamount;
	//充值计数器
	@JSONField  (ordinal = 8,name = "TopupCounter") 
	private Integer topupcounter;
	//消费计数器
	@JSONField  (ordinal = 9,name = "DebitCounter") 
	private Integer debitcounter;
	//最后生成票据时间
	@JSONField  (ordinal = 10,name = "LastMakePayTicketTime",format="yyyy-MM-dd HH:mm:ss") 
	private Date lastmakepaytickettime;
	//最后充值金额
	@JSONField  (ordinal = 11,name = "LastTopupAmount")
	private BigDecimal lasttopupamount;
	//最后充值时间
	@JSONField  (ordinal = 12,name = "LastTopupTime",format="yyyy-MM-dd HH:mm:ss")
	private Date lasttopuptime;
	//最后消费金额
	@JSONField  (ordinal = 13,name = "LastDebitAmount")
	private BigDecimal lastdebitamount;
	//最后消费时间
	@JSONField  (ordinal = 14,name = "LastDebitTime",format="yyyy-MM-dd HH:mm:ss")
	private Date lastdebittime;
	//总充值金额
	@JSONField  (ordinal = 15,name = "TotalTopupAmount")
	private BigDecimal totaltopupamount;
	//总消费金额
	@JSONField  (ordinal = 16,name = "TotalDebitAmount")
	private BigDecimal totaldebitamount;
	//账号余额
	@JSONField  (ordinal = 17,name = "Balance")
	private BigDecimal balance;
	//积分
	@JSONField  (ordinal = 18,name = "Score")
	private Integer score;
	//赠送余额
	@JSONField  (ordinal = 19,name = "FreeBalance")
	private BigDecimal freebalance;
	//赠送次数
	@JSONField  (ordinal = 20,name = "FreeTimes")
	private Integer freetimes;
	//账号过期时间
	@JSONField  (ordinal = 21,name = "AccountExpireTime",format="yyyy-MM-dd HH:mm:ss")
	private Date accountexpiretime;
	//证书照片地址
	private String certphotourl;
	//个人照片地址
	private String personphotourl;
	//创建人
	private Integer createdby;
	//创建时间
	@JSONField  (format="yyyy-MM-dd HH:mm:ss")
	private Date createddate;
	//更新人
	private Integer updatedby;
	//更新时间
	private Date updateddate;
	//更新时间
	@JSONField  (format="yyyy-MM-dd HH:mm:ss")
	private String remark;
	//校验MAC
	private String certmac;

	private String AccountTypeName;
	private String StateName;


	public AccountDO() {
		super();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public static void setSerialVersionUID(long serialVersionUID) {
		AccountDO.serialVersionUID = serialVersionUID;
	}


	public AccountDO(Integer id, String merchantcode, Integer uid, Integer accounttype, String accountno,
					 String mobilephone, String truename, String gender, String certno, Integer cardtypecode,
					 Integer subcardtypecode, Integer state, BigDecimal maxtxnamount, Integer topupcounter, Integer debitcounter,
					 Date lastmakepaytickettime, BigDecimal lasttopupamount, Date lasttopuptime, BigDecimal lastdebitamount,
					 Date lastdebittime, BigDecimal totaltopupamount, BigDecimal totaldebitamount, BigDecimal balance,
					 Integer score, BigDecimal freebalance, Integer freetimes, Date accountexpiretime, String certphotourl,
					 String personphotourl, Integer createdby, Date createddate, Integer updatedby, Date updateddate,
					 String remark, String certmac, String accountTypeName, String stateName) {
		super();
		this.id = id;
		this.merchantcode = merchantcode;
		this.uid = uid;
		this.accounttype = accounttype;
		this.accountno = accountno;
		this.mobilephone = mobilephone;
		this.truename = truename;
		this.gender = gender;
		this.certno = certno;
		this.cardtypecode = cardtypecode;
		this.subcardtypecode = subcardtypecode;
		this.state = state;
		this.maxtxnamount = maxtxnamount;
		this.topupcounter = topupcounter;
		this.debitcounter = debitcounter;
		this.lastmakepaytickettime = lastmakepaytickettime;
		this.lasttopupamount = lasttopupamount;
		this.lasttopuptime = lasttopuptime;
		this.lastdebitamount = lastdebitamount;
		this.lastdebittime = lastdebittime;
		this.totaltopupamount = totaltopupamount;
		this.totaldebitamount = totaldebitamount;
		this.balance = balance;
		this.score = score;
		this.freebalance = freebalance;
		this.freetimes = freetimes;
		this.accountexpiretime = accountexpiretime;
		this.certphotourl = certphotourl;
		this.personphotourl = personphotourl;
		this.createdby = createdby;
		this.createddate = createddate;
		this.updatedby = updatedby;
		this.updateddate = updateddate;
		this.remark = remark;
		this.certmac = certmac;
		AccountTypeName = accountTypeName;
		StateName = stateName;
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

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(Integer accounttype) {
		this.accounttype = accounttype;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public Integer getCardtypecode() {
		return cardtypecode;
	}

	public void setCardtypecode(Integer cardtypecode) {
		this.cardtypecode = cardtypecode;
	}

	public Integer getSubcardtypecode() {
		return subcardtypecode;
	}

	public void setSubcardtypecode(Integer subcardtypecode) {
		this.subcardtypecode = subcardtypecode;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public BigDecimal getMaxtxnamount() {
		return maxtxnamount;
	}

	public void setMaxtxnamount(BigDecimal maxtxnamount) {
		this.maxtxnamount = maxtxnamount;
	}

	public Integer getTopupcounter() {
		return topupcounter;
	}

	public void setTopupcounter(Integer topupcounter) {
		this.topupcounter = topupcounter;
	}

	public Integer getDebitcounter() {
		return debitcounter;
	}

	public void setDebitcounter(Integer debitcounter) {
		this.debitcounter = debitcounter;
	}

	public Date getLastmakepaytickettime() {
		return lastmakepaytickettime;
	}

	public void setLastmakepaytickettime(Date lastmakepaytickettime) {
		this.lastmakepaytickettime = lastmakepaytickettime;
	}

	public BigDecimal getLasttopupamount() {
		return lasttopupamount;
	}

	public void setLasttopupamount(BigDecimal lasttopupamount) {
		this.lasttopupamount = lasttopupamount;
	}

	public Date getLasttopuptime() {
		return lasttopuptime;
	}

	public void setLasttopuptime(Date lasttopuptime) {
		this.lasttopuptime = lasttopuptime;
	}

	public BigDecimal getLastdebitamount() {
		return lastdebitamount;
	}

	public void setLastdebitamount(BigDecimal lastdebitamount) {
		this.lastdebitamount = lastdebitamount;
	}

	public Date getLastdebittime() {
		return lastdebittime;
	}

	public void setLastdebittime(Date lastdebittime) {
		this.lastdebittime = lastdebittime;
	}

	public BigDecimal getTotaltopupamount() {
		return totaltopupamount;
	}

	public void setTotaltopupamount(BigDecimal totaltopupamount) {
		this.totaltopupamount = totaltopupamount;
	}

	public BigDecimal getTotaldebitamount() {
		return totaldebitamount;
	}

	public void setTotaldebitamount(BigDecimal totaldebitamount) {
		this.totaldebitamount = totaldebitamount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public BigDecimal getFreebalance() {
		return freebalance;
	}

	public void setFreebalance(BigDecimal freebalance) {
		this.freebalance = freebalance;
	}

	public Integer getFreetimes() {
		return freetimes;
	}

	public void setFreetimes(Integer freetimes) {
		this.freetimes = freetimes;
	}

	public Date getAccountexpiretime() {
		return accountexpiretime;
	}

	public void setAccountexpiretime(Date accountexpiretime) {
		this.accountexpiretime = accountexpiretime;
	}

	public String getCertphotourl() {
		return certphotourl;
	}

	public void setCertphotourl(String certphotourl) {
		this.certphotourl = certphotourl;
	}

	public String getPersonphotourl() {
		return personphotourl;
	}

	public void setPersonphotourl(String personphotourl) {
		this.personphotourl = personphotourl;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Integer getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(Integer updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCertmac() {
		return certmac;
	}

	public void setCertmac(String certmac) {
		this.certmac = certmac;
	}

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
	
	
	
}
