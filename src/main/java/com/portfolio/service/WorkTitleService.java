package com.portfolio.service;

import java.util.List;

import com.portfolio.model.WorkTitle;

public interface WorkTitleService {

	public WorkTitle saveTitle(WorkTitle workTitle);
	
	public List<WorkTitle> readTitle();
	
	public void deleteTitle(int id);
}
