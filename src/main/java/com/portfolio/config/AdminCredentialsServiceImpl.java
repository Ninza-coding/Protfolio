package com.portfolio.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.portfolio.dao.AdminDao;
import com.portfolio.model.Admin;

@Service
public class AdminCredentialsServiceImpl implements AdminCredentialsService{

	private AdminDao adminDao;
	
	@Autowired
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String checkAdminCredentials(String oldusername, String oldpassword) {
		Optional<Admin> byUsername = adminDao.findByUsername(oldusername);
		if(byUsername.isPresent()) {
			
			Admin admin=byUsername.get();
			boolean matches =passwordEncoder.matches(oldpassword, admin.getPassword());
			if(matches) {
				return "SUCCESS";
			}else {
				return "Wrong Old Credentials";
			}
		}else {
			return "Wrong Old Credentials";
		}	
	}
	

	@Override
	public String updateAdminCredentialsService(String newusername, String newpassword, String oldusername) {
		int updateCredentials = adminDao.updateCredentials
				(newusername, passwordEncoder.encode(newpassword), oldusername);
		if(updateCredentials==1) {
			return "Credential Updated Successfully";
		}else {
			return "Failed to Credential Updated Successfully";
		}
	}

}
