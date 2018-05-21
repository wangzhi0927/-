package com.slzr.set.dao;

import com.slzr.set.domain.MerchDo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MerchDao {
    List<MerchDo> list(Map<String,Object> map);

    int count(Map<String, Object> map);

    int remove(Long id);

    int save(MerchDo merchant);

    MerchDo get(Integer id);

    int update(MerchDo merchant);
}
