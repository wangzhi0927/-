package com.slzr.operation.service.Impl;

import com.slzr.operation.dao.GuestMessageDao;
import com.slzr.operation.dao.OPTDepartmentDao;
import com.slzr.operation.domain.GuestMessageDO;
import com.slzr.operation.domain.OPTDepartmentDO;
import com.slzr.operation.service.GuestMessageService;
import com.slzr.operation.service.OPTDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OPTDepartmentServiceImpl implements OPTDepartmentService {
	@Autowired
	private OPTDepartmentDao oPTDepartmentDao;

	@Override
	public List<OPTDepartmentDO> list(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return oPTDepartmentDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return oPTDepartmentDao.count(map);
	}

	@Override
	public int save(OPTDepartmentDO oPTDepartmentDO) {
		// TODO Auto-generated method stub
		return oPTDepartmentDao.save(oPTDepartmentDO);
	}

	@Override
	public int remove(Integer id) {
		// TODO Auto-generated method stub
		return oPTDepartmentDao.remove(id);
	}

	public int update(OPTDepartmentDO oPTDepartmentDO) {
		return oPTDepartmentDao.update(oPTDepartmentDO);
	}

	public OPTDepartmentDO getByID(int id){
		return oPTDepartmentDao.getByID(id);
	}
	
	@Override
	public boolean exist(Map<String, Object> params) {
		boolean exist;
		exist = oPTDepartmentDao.list(params).size() > 0;
		return exist;
	}
}
