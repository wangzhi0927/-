package com.slzr.set.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户表
 */
public class MerchDo implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID
    private Integer id;

    //商户代码||

    private String merchantCode;
    //映射商户代码
    private  String mapMerchantCode;
    //商户名称||
    private String merchantName;
    //商户简称
    private String shortName;
    //城市代码||
    private String cityCode;
    //联系电话
    private String contactPhone;
    //联系人
    private String contactPerson;
    //清算方式 1：充值金额 2：消费金额
    private String settleMethod;
    //清算费率
    private Double settleRate;
    //清算周期
    private Integer settlePeriod;
    //清算周期单位
    private String settlePeriodUnit;
    //结算日
    private Integer settleDay;
    //结算佣金
    private Integer serviceFee;
    //最后结算时间
    private Date lastSettleDateTime;
    //是否启用
    private String enableStatus;

    //创建人
    private Long createdBy;

    //创建时间
    private Date createdDate;

    //更新人
    private Long updatedBy;
    //更新时间
    private  Date updatedDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSettleRate() {
        return settleRate;
    }

    public void setSettleRate(Double settleRate) {
        this.settleRate = settleRate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMapMerchantCode() {
        return mapMerchantCode;
    }

    public void setMapMerchantCode(String mapMerchantCode) {
        this.mapMerchantCode = mapMerchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getSettleMethod() {
        return settleMethod;
    }

    public void setSettleMethod(String settleMethod) {
        this.settleMethod = settleMethod;
    }




    public Integer getSettlePeriod() {
        return settlePeriod;
    }

    public void setSettlePeriod(Integer settlePeriod) {
        this.settlePeriod = settlePeriod;
    }

    public String getSettlePeriodUnit() {
        return settlePeriodUnit;
    }

    public void setSettlePeriodUnit(String settlePeriodUnit) {
        this.settlePeriodUnit = settlePeriodUnit;
    }

    public Integer getSettleDay() {
        return settleDay;
    }

    public void setSettleDay(Integer settleDay) {
        this.settleDay = settleDay;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Date getLastSettleDateTime() {
        return lastSettleDateTime;
    }

    public void setLastSettleDateTime(Date lastSettleDateTime) {
        this.lastSettleDateTime = lastSettleDateTime;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }


    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }
}
