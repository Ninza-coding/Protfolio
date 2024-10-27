package com.portfolio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.model.AboutForm;

@Repository
public interface AboutFormDao extends JpaRepository<AboutForm, Integer>{

}
