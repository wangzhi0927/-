package com.slzr.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LogDO {
	private String id;

	private Long userId;

	private String userName;

	//操作类型ID，数据字典
	private Integer operateTypeID;

	private  String operateType;

	//模块ID
	private Integer moduleID;
	private  String moduleName;

	//日志类型ID
	private Integer logTypeID;

	//日志内容
	private String logContent;

	//日志时间
	private String logDateTime;
	//IP
	private String ip;

	//日志来源方法名称
	private  String sourceMethod;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOperateTypeID() {
		return operateTypeID;
	}

	public void setOperateTypeID(Integer operateTypeID) {
		this.operateTypeID = operateTypeID;
	}

	public Integer getModuleID() {
		return moduleID;
	}

	public void setModuleID(Integer moduleID) {
		this.moduleID = moduleID;
	}

	public Integer getLogTypeID() {
		return logTypeID;
	}

	public void setLogTypeID(Integer logTypeID) {
		this.logTypeID = logTypeID;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getLogDateTime() {
		return logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSourceMethod() {
		return sourceMethod;
	}

	public void setSourceMethod(String sourceMethod) {
		this.sourceMethod = sourceMethod;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


}