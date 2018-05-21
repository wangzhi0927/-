package com.slzr.ureport.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 一级统计 
 * @author Administrator
 *
 */
public class ACountDo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	    private Long id;
	    //年
		private  String year;
	    //日期
	    private String txnDate;
	    //部门
	    private  String deptName;
	    //线路
	    private String lineName;
	    //车辆编号
	    private  String busNO;
	    
	    private String driverName; //司机

	    //消费人次
	    private Integer consumptionPerson;
	    //消费金额
	    private BigDecimal consumptionAmount;

	    //赠送消费人次
		private Integer debitFreeNum;
		//赠送消费金额
		private  BigDecimal debitFreeAmount;

	    //商户代码
	private String merchantCode;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	private String userName;
	    
	    private String startDate;
	    
	    private String endDate;

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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	    public Integer getConsumptionPerson() {
	        return consumptionPerson;
	    }

	    public void setConsumptionPerson(Integer consumptionPerson) {
	        this.consumptionPerson = consumptionPerson;
	    }

	    public BigDecimal getConsumptionAmount() {
	        return consumptionAmount;
	    }

	    public void setConsumptionAmount(BigDecimal consumptionAmount) {
	        this.consumptionAmount = consumptionAmount;
	    }

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
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