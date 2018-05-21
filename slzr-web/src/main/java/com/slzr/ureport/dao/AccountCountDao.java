package com.slzr.ureport.dao;




import com.slzr.ureport.domain.AccountCountDo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface AccountCountDao {

    List<AccountCountDo> list(Map<String, Object> parameters);

    List<AccountCountDo> get();
}
