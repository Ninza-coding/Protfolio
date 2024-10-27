package com.portfolio.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.portfolio.model.UploadProjects;

@Repository
public interface UploadProjectsDao extends JpaRepository<UploadProjects, Integer> {

	@Override
	public <S extends UploadProjects> S save(S entity);
	
	@Override
	public List<UploadProjects> findAll();
	
	@Override
	public void deleteById(Integer id);
	
	@Query("SELECT u.image FROM UploadProjects u WHERE u.id = :id") 
	public String findImageById(Integer id);
	//the method is used to get the image name saved in the database with respect to id
	}

