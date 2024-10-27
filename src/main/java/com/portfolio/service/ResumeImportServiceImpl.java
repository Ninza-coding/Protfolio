package com.portfolio.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.dao.ResumeImportDao;
import com.portfolio.model.ResumeImport;

import jakarta.transaction.Transactional;

@Service
public class ResumeImportServiceImpl implements ResumeImportService {

	private ResumeImportDao resumeImportDao;

	@Autowired
	public void setResumeImportDao(ResumeImportDao resumeImportDao) {
		this.resumeImportDao = resumeImportDao;
	}

	@SuppressWarnings("resource")
	@Override
	@Transactional(rollbackOn = Exception.class)
	public ResumeImport saveResume(ResumeImport resumeImport, MultipartFile multipartFile) throws Exception {
		ResumeImport save = null;
		try {
			save = resumeImportDao.save(resumeImport);
			if (save != null) {
				String path = "/app/src/main/resources/static/myresume/"
						+ multipartFile.getOriginalFilename();
				byte[] bytes = multipartFile.getBytes();
				
				FileOutputStream fos = new FileOutputStream(path);
				fos.write(bytes);
			}
		} catch (Exception e) {
			save = null;
			e.printStackTrace();
		}
		return save;
	}
	
	@Override
	 public String getResumeFileName(String resumeName) {
	        ResumeImport resumeImport = resumeImportDao.findByResume(resumeName);
	        return resumeImport != null ? resumeImport.getResume() : null; // Return the filename or null
	    }
	@Override
	 public ResumeImport findById(int id) {
	        return resumeImportDao.findById(id).orElse(null);
	        
	    }

	@Override
	public List<ResumeImport> findAll() {
		// TODO Auto-generated method stub
		return resumeImportDao.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteResumeBYId(int id) throws Exception{
		ResumeImport resume=findById(id);
		String filename=resume.getResume();
		String path="/portfolio-1/src/main/resources/static/myresume/"+filename;
		try {
			File fos= new File(path);
			fos.delete();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		resumeImportDao.deleteById(id);	
	}

}
