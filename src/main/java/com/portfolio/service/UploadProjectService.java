package com.portfolio.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.model.UploadProjects;

public interface UploadProjectService {

	public UploadProjects addProjects(UploadProjects uploadProjects, MultipartFile multipartFile) throws Exception;

	public List<UploadProjects> readAllProjects();
	
	public void deleteProject(Integer id);

	public String getImageById(Integer id);
	//Testing code only check will it getting name correct or not which is used in deleting.
}
