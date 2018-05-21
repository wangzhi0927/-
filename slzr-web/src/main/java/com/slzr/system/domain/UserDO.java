package com.slzr.system.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long id;
    // 角色
    private Long roleID;
    // 用户名
    private String userName;
    // 密码
    private String passWord;
    // 真实姓名
    private String trueName;
    
    //手机号码
    private String mobilePhone;
    // 性别
    private String gender;
    // 生日
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    // 用户类型ID
    private Integer userTypeID;
    // 用户状态ID （0:禁用 1:正常）
    private Integer stateID;
    // 最后登录时间
    private Date lastLoginDate;
    // 最后登录IP
    private String lastLoginIP;
    //登录次数
    private Integer loginNum;
    
    
    private Integer createdBy;
    
    private Date createdDate;
    
    private Integer updatedBy;
   
    private Date updatedDate;
   
    private String remark;
    
    //商户编码,用于区分不同县城
    private String merchantCode;

    //角色
    private List<Long> roleIds;
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

  

    public List<Long> getRoleIds() {
		return roleIds;
	}



	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getRoleID() {
		return roleID;
	}



	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassWord() {
		return passWord;
	}



	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}



	public String getTrueName() {
		return trueName;
	}



	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}



	public String getMobilePhone() {
		return mobilePhone;
	}



	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Date getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}



	public Integer getUserTypeID() {
		return userTypeID;
	}



	public void setUserTypeID(Integer userTypeID) {
		this.userTypeID = userTypeID;
	}



	public Integer getStateID() {
		return stateID;
	}



	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}



	public Date getLastLoginDate() {
		return lastLoginDate;
	}



	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}



	public String getLastLoginIP() {
		return lastLoginIP;
	}



	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}



	public Integer getLoginNum() {
		return loginNum;
	}



	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}



	public Integer getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Integer getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getMerchantCode() {
		return merchantCode;
	}



	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}



	@Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", roleID=" + roleID +
                ", userName='" + userName + '\'' +               
                ", passWord='" + passWord + '\'' +
                ", trueName='" + trueName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", userTypeID='" + userTypeID + '\'' +
                ", stateID='" + stateID + '\'' +
                ", lastLoginDate='" + lastLoginDate + '\'' +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                ", loginNum='" + loginNum + '\'' +                
                ", createdBy='" + createdBy + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate='" + updatedDate + '\'' +
                ", remark='" + remark + '\'' +
                ", merchantCode='" + merchantCode + '\'' +   
                ", roleIds=" + roleIds +
                '}';
    }
}
