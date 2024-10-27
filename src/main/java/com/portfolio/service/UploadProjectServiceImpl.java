package com.portfolio.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.dao.UploadProjectsDao;
import com.portfolio.model.UploadProjects;

import jakarta.transaction.Transactional;

@Service
public class UploadProjectServiceImpl implements UploadProjectService{

	private UploadProjectsDao uploadProjectsDao;
	@Autowired
	public void setUploadProjectsDao(UploadProjectsDao uploadProjectsDao) {
		this.uploadProjectsDao = uploadProjectsDao;
	}
	@SuppressWarnings("resource")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public UploadProjects addProjects(UploadProjects uploadProjects, MultipartFile multipartFile) throws Exception{
		UploadProjects save = null;
		try {
			save = uploadProjectsDao.save(uploadProjects);
		if(save!=null) {
			LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	        String timestamp = now.format(formatter);
			String path="/app/src/main/resources/static/myprimg/"
		+timestamp+multipartFile.getOriginalFilename();
			byte[] bytes=multipartFile.getBytes();
		FileOutputStream fos= new FileOutputStream(path);
		fos.write(bytes);
		}
		}catch(Exception e) {
			save=null;
			e.printStackTrace();
		}
		return save;
	}
	@Override
	public List<UploadProjects> readAllProjects() {
		return uploadProjectsDao.findAll();
	}	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteProject(Integer id) {
		String path="/app/src/main/resources/static/myprimg/"+uploadProjectsDao.findImageById(id);
		try {
			File fos= new File(path);
			fos.delete();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		uploadProjectsDao.deleteById(id);
	}
	
	//Used for testing only....
	@Override
	public String getImageById(Integer id) {
		return uploadProjectsDao.findImageById(id);
	}

}
