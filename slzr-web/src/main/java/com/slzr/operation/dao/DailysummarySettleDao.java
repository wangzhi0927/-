package com.slzr.operation.dao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.slzr.operation.domain.DailysummarySettleDO;

@Mapper
public interface DailysummarySettleDao{

	List<DailysummarySettleDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	DailysummarySettleDO get(int id);

	void updateAudit(@Param("id") String id,@Param("auditvalue") int auditvalue,@Param("chkuserid") int chkuserid,@Param("remark") String remark,@Param("amount") BigDecimal amount,@Param("transdate") Date transdate,@Param("transno") String transno,@Param("transstatus") int transstatus);
}