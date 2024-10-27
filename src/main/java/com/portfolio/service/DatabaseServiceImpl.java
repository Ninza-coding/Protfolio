package com.portfolio.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void dropDatabaseService() {

		String sql = "DROP DATABASE IF EXISTS railway";
		jdbcTemplate.execute(sql);

	}

	@Override
	public void deleteDirectoryContents(File directory) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectoryContents(file); // Recursive call for subdirectories
					}
					boolean deleted = file.delete(); // Delete the file or empty directory
					if (!deleted) {
						System.err.println("Failed to delete: " + file.getAbsolutePath());
					} else {
						System.out.println("Deleted");
					}
				}
			}

		}
	}
}
