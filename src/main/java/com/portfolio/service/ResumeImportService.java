package com.portfolio.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portfolio.model.ResumeImport;

public interface ResumeImportService {

	public ResumeImport saveResume(ResumeImport resumeImport, MultipartFile multipartFile) throws Exception;

	String getResumeFileName(String resumeName);

	public List<ResumeImport> findAll();

	public ResumeImport findById(int id);
	
	public void deleteResumeBYId(int id) throws Exception;
}
