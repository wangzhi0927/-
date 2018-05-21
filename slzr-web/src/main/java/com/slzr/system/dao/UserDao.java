package com.slzr.system.dao;

import java.util.List;
import java.util.Map;

import com.slzr.common.utils.Query;
import org.apache.ibatis.annotations.Mapper;

import com.slzr.system.domain.UserDO;

/** 
 * 用户
 * 2018.02.08
 * @author Administrator
 */
@Mapper
public interface UserDao {

	UserDO get(Long userId);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

	int updateNameAndPasW(Map<String, Object> params);

	List<UserDO> getOneUser(Query query);
}
