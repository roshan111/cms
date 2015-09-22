package com.iitms.cms.masters.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iitms.cms.masters.dao.ComplaintItemDao;
import com.iitms.cms.masters.entity.ComplaintItemEntity;

@Service
public class ComplaintItemServiceImpl implements ComplaintItemService{

	@Autowired
	private ComplaintItemDao complaintItemDao;
	
	@Override
	@Transactional
	public boolean addComplaintItem(ComplaintItemEntity entity) {
		return complaintItemDao.addComplaintItem(entity);
	}

	@Override
	@Transactional
	public List<ComplaintItemEntity> listComplaintItem() {
		return complaintItemDao.listComplaintItem();
	}

	@Override
	@Transactional
	public boolean deleteComplaintItem(Integer complaintItemId) {
		return complaintItemDao.deleteComplaintItem(complaintItemId);
	}

	@Override
	@Transactional
	public boolean updateComplaintItem(ComplaintItemEntity complaintItem) {
		return complaintItemDao.updateComplaintItem(complaintItem);
	}

	@Override
	@Transactional
	public ComplaintItemEntity getComplaintItem(int complaintItemId) {
		return complaintItemDao.getComplaintItem(complaintItemId);
	}

	
}
