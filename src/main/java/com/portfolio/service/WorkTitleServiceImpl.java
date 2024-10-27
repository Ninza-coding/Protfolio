package com.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.dao.WorkTitleDao;
import com.portfolio.model.WorkTitle;

@Service
public class WorkTitleServiceImpl implements WorkTitleService{

	private WorkTitleDao workTitleDao;
	
	@Autowired
	public void setWorkTitleDao(WorkTitleDao workTitleDao) {
		this.workTitleDao = workTitleDao;
	}

	@Override
	public WorkTitle saveTitle(WorkTitle workTitle) {
		// TODO Auto-generated method stub
		return workTitleDao.save(workTitle);
	}

	@Override
	public List<WorkTitle> readTitle() {
		// TODO Auto-generated method stub
		return workTitleDao.findAll();
	}

	@Override
	public void deleteTitle(int id) {
		workTitleDao.deleteById(id);
	}

}
