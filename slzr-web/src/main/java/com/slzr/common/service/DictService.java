package com.slzr.common.service;

import com.slzr.common.domain.DictDO;
import com.slzr.system.domain.UserDO;

import java.util.List;
import java.util.Map;


public interface DictService {
	
	DictDO get(Long id);
	
	List<DictDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DictDO sysDict);
	
	int update(DictDO sysDict);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<DictDO> listType();
	
	int findMaxid();


	/**
	 * 获取性别列表
 	 * @return
	 */
	List<DictDO> getSexList();

	/**
	 * 根据type获取数据
	 * @param map
	 * @return
	 */
	List<DictDO> listByType(String type);

	List<DictDO> list();
}
