package com.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.dao.ExperienceUploadDao;
import com.portfolio.model.ExperinceUpload;

@Service
public class ExperienceUploadServiceImpl implements ExperienceUploadService {

	private ExperienceUploadDao experienceUploadDao;
	
	@Autowired
	public void setExperienceUploadDao(ExperienceUploadDao experienceUploadDao) {
		this.experienceUploadDao = experienceUploadDao;
	}

	@Override
	public ExperinceUpload saveExperience(ExperinceUpload experinceUpload) {
		
		return experienceUploadDao.save(experinceUpload);
	}

	@Override
	public List<ExperinceUpload> readExperince() {
		
		return experienceUploadDao.findAll();
	}

	@Override
	public void deleteExperince(int id) {
		experienceUploadDao.deleteById(id);
		
	}

}
