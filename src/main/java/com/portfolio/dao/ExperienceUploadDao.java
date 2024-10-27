package com.portfolio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.ExperinceUpload;

@Repository
public interface ExperienceUploadDao extends JpaRepository<ExperinceUpload, Integer>{

	@Override
	public <S extends ExperinceUpload> S save(S entity);;
	
	@Override
	public List<ExperinceUpload> findAll();
	
	@Override
	public void deleteById(Integer id);
}
