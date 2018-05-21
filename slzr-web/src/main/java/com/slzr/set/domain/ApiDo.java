package com.slzr.set.domain;

import java.util.Date;

public class ApiDo {
    private Integer id;
    //更新人
    private Long updatedBy;
    //创建人
    private Long createdBy;
    //应用ID
    private String appId;
    //更新时间
    private Date updatedDate;
    //应用密匙
    private String appKey;
    //创建时间
    private Date createdDate;
    //过期时间
    private Date expireTime;
    //应用类型
    private String appType;
    private String remark;
    public ApiDo() {
        super();
    }

    public ApiDo(Integer id, Long updatedBy, Long createdBy, String appId, Date updatedDate, String appKey, Date createdDate, Date expireTime, String appType, String remark) {
        this.id = id;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.appId = appId;
        this.updatedDate = updatedDate;
        this.appKey = appKey;
        this.createdDate = createdDate;
        this.expireTime = expireTime;
        this.appType = appType;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public java.util.Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(java.util.Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(java.util.Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getAppType() {
        return this.appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
