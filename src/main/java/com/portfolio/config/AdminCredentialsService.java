package com.portfolio.config;

public interface AdminCredentialsService {
	public String checkAdminCredentials(String oldusername, String oldpassword);
	public String updateAdminCredentialsService(String newusername, String newpassword, String oldusername);
}
