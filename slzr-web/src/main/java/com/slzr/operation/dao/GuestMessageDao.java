package com.slzr.operation.dao;


import com.slzr.operation.domain.GuestMessageDO;


import java.util.List;
import java.util.Map;

//@Mapper
public interface GuestMessageDao {

	List<GuestMessageDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(GuestMessageDO guestmessage);
	
	int remove(Integer ID);

	int update(GuestMessageDO guestmessage);

	GuestMessageDO getByID(Integer ID);


}