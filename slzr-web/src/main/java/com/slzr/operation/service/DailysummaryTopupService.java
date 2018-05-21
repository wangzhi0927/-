package com.slzr.operation.service;

import java.util.List;
import java.util.Map;

import com.slzr.operation.domain.DailysummarySettleDO;
import com.slzr.operation.domain.DailysummaryTopupDO;

public interface DailysummaryTopupService{

	List<DailysummaryTopupDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	DailysummaryTopupDO get(int id);
	
	void updateAudit(int id,int auditvalue,int chkuserid,String remark);

	String againDaily(String txndate);
}