package com.portfolio.service;

import java.util.List;

import com.portfolio.model.AboutForm;

public interface AboutFormService {

	public AboutForm saveAboutService(AboutForm aboutForm);
	
	public List<AboutForm> readAboutService();
	
	public void deleteAboutService(int id);
}
