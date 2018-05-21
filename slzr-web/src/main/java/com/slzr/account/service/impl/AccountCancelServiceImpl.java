package com.slzr.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

import com.slzr.account.dao.AccountCancelDao;
import com.slzr.account.domain.AccountCancelDO;
import com.slzr.account.service.AccountCancelService;



@Service
public class AccountCancelServiceImpl implements AccountCancelService {
	@Autowired
	private AccountCancelDao accountCancelDao;
	
	@Override
	public AccountCancelDO get(Integer id){
		return accountCancelDao.get(id);
	}
	
	@Override
	public List<AccountCancelDO> list(Map<String, Object> map){
		return accountCancelDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return accountCancelDao.count(map);
	}
	
	
	@Override
	public int update(AccountCancelDO accountCancel){
		return accountCancelDao.update(accountCancel);
	}
	
	
}
