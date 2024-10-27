package com.portfolio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="edu_upload")
public class EducationUpload {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@NotNull
		@Min(value = 1950, message = "Year Must Be Valid")
		@Max(value = 2050, message = "Year Must Be Valid")
		private int year;
		
		@NotEmpty(message = "Email cannot be Empty")
		@Size(min = 2, max = 100, message = "invalid Email Size")
		@Column(length=100)
		private String title;
		
		@NotEmpty(message = "Email cannot be Empty")
		@Size(min = 2, max = 100, message = "invalid Email Size")
		@Column(length=100)
		private String subtitle;
		
		@NotEmpty(message = "message cannot be Empty")
		@Size(min = 5, max = 2000, message = "Atleaste Write 5 characters and less than 530 charaters")
		@Column(length = 2000)
		private String description;
}
