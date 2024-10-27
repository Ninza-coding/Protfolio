package com.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.dao.AboutFormDao;
import com.portfolio.model.AboutForm;

@Service
public class AboutFormServiceImpl implements AboutFormService {

	private AboutFormDao aboutFormDao;

	@Autowired
	public void setAboutFormDao(AboutFormDao aboutFormDao) {
		this.aboutFormDao = aboutFormDao;
	}

	@Override
	public AboutForm saveAboutService(AboutForm aboutForm) {
		return aboutFormDao.save(aboutForm);
	}

	@Override
	public List<AboutForm> readAboutService() {
		return aboutFormDao.findAll();
	}

	@Override
	public void deleteAboutService(int id) {
		aboutFormDao.deleteById(id);
	}

}
