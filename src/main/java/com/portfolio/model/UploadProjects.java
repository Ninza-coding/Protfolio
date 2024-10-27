package com.portfolio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class UploadProjects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String image;
	private String imagetitle;
	public LocalDate date;
}
