package com.portfolio.service;

import java.util.List;

import com.portfolio.model.ExperinceUpload;



public interface ExperienceUploadService {
	
	public ExperinceUpload saveExperience(ExperinceUpload experinceUpload);
	
	public List<ExperinceUpload> readExperince();

	public void deleteExperince(int id);
}
