package com.portfolio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.EducationUpload;

@Repository
public interface EducationDao extends JpaRepository<EducationUpload, Integer>{

	@Override
	public List<EducationUpload> findAll();
}
