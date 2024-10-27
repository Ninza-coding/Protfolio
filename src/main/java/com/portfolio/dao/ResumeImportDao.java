package com.portfolio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.ResumeImport;

@Repository
public interface ResumeImportDao extends JpaRepository<ResumeImport, Integer>{

	@Override
	public <S extends ResumeImport> S save(S entity);
	
	ResumeImport findByResume(String resumeName);

	public String findResumeById(int id);
}