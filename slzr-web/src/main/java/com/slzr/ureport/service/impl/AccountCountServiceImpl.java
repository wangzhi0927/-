package com.slzr.ureport.service.impl;

import com.slzr.ureport.dao.AccountCountDao;
import com.slzr.ureport.domain.AccountCountDo;
import com.slzr.ureport.service.AccountCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AccountCountServiceImpl implements AccountCountService{

    @Autowired
    private AccountCountDao accountCountDao;
    @Override

    public List<AccountCountDo> list(Map<String, Object> parameters) {
        return accountCountDao.list(parameters);
    }

    @Override
    public List<AccountCountDo> get() {
        return accountCountDao.get();
    }
}
