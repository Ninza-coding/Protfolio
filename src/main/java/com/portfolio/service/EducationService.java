package com.portfolio.service;

import java.util.List;

import com.portfolio.model.EducationUpload;

public interface EducationService {

	public EducationUpload saveEducationService(EducationUpload educationUpload);
	public List<EducationUpload> readEducationService();
	public void deleteEducationService(int id);
}
