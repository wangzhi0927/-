package com.slzr.ureport.service;

import com.slzr.ureport.domain.AccountCountDo;

import java.util.List;
import java.util.Map;

public interface AccountCountService {
    List<AccountCountDo> list(Map<String, Object> parameters);

    List<AccountCountDo> get();
}
