package com.slzr.set.service;

import com.slzr.set.domain.MerchDo;

import java.util.List;
import java.util.Map;

public interface MerchService {

    List<MerchDo> list(Map<String,Object> map);

    int count(Map<String,Object> map);

    int remove(Long id);



    boolean exit(Map<String, Object> params);

    int save(MerchDo merchant);

    MerchDo get(Integer id);

    int update(MerchDo merchant);
}
