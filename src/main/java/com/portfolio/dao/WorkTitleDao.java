package com.portfolio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.WorkTitle;

@Repository
public interface WorkTitleDao extends JpaRepository<WorkTitle, Integer>{

}
