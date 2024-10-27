package com.portfolio.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.portfolio.service.DatabaseService;

@Controller
public class DatabaseController {
	
	private DatabaseService databaseService;
	
	@Autowired
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}
	@EventListener
	public void dropDatabase(ContextClosedEvent closedEvent) {
		databaseService.dropDatabaseService();
		String path= "/app/src/main/resources/static/myprimg/";
		String path1="/app/src/main/resources/static/myresume/";
		
		File directory= new File(path);
		File directory1= new File(path1);
		databaseService.deleteDirectoryContents(directory);
		databaseService.deleteDirectoryContents(directory1);
		
		System.out.println("Database Droped");
	}
}
