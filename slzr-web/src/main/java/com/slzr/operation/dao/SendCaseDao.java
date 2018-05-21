package com.slzr.operation.dao;

import com.slzr.operation.domain.SendCaseDo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SendCaseDao {
    List<SendCaseDo> list(Map<String, Object> map);

    List<SendCaseDo> lists(Map<String, Object> map);


    int count(Map<String,Object> map);

    void save1(SendCaseDo SendCaseDo);
    int save2(SendCaseDo SendCaseDo);

    int update1(SendCaseDo SendCaseDo);
    int update2(SendCaseDo SendCaseDo);

    int remove1(Integer id);
    int remove2(Integer pid);

    /**
     *不需要判断是超级管理员还是商户
     * @return
     */
    int countUnEanble(SendCaseDo sendCaseDo);

    /**
     * 将原来状态是启用状态的修改为停止状态
     */
    int updateUnEnable(Map<String, Object> map);
    /**
     * 将对应的赠送方案修改为启用
     */
    int updateEnable(Map<String, Object> map);
}
