package com.slzr.operation.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.slzr.operation.domain.DailysummarySettleDO;

public interface DailysummarySettleService{

	List<DailysummarySettleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	DailysummarySettleDO get(String id);
	
	void updateAudit(String id,int auditvalue,int chkuserid,String remark,BigDecimal amount,Date transdate,String transno,int transstatus);
}