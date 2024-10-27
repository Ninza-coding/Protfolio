package com.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.dao.EducationDao;
import com.portfolio.model.EducationUpload;

@Service
public class EducationServiceIMpl implements EducationService {

	private EducationDao educationDao;

	@Autowired
	public void setEducationDao(EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	@Override
	public EducationUpload saveEducationService(EducationUpload educationUpload) {
		return educationDao.save(educationUpload);
	}

	@Override
	public List<EducationUpload> readEducationService() {

		return educationDao.findAll();
	}

	@Override
	public void deleteEducationService(int id) {
		educationDao.deleteById(id);
	}
}
