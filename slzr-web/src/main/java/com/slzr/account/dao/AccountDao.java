package com.slzr.account.dao;

import com.slzr.account.domain.AccountDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户管理
 * @author lc
 * @date 2018-03-18 16:45:51
 */
@Mapper
public interface AccountDao {

	AccountDO get(Integer id);
	
	AccountDO getbyAccountNO(String accountno); 
	
	List<AccountDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int updateAccountState(@Param("accountno")String accountno, @Param("operationType")String operationType);

	int update(AccountDO account);

}
