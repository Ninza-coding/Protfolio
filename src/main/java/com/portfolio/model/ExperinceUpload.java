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
@Table(name="experince")
public class ExperinceUpload {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message = "Year can't be Valid")
	@Min(value = 1950, message = "Year Must Be Valid")
	@Max(value = 2050, message = "Year Must Be Valid")
	private Integer year;
	
	@NotEmpty(message = "Title cannot be Empty")
	@Size(min = 2, max = 100, message = "invalid  Size")
	@Column(length=100)
	private String title;
	
	@NotEmpty(message = "subtitle cannot be Empty")
	@Size(min = 2, max = 100, message = "invalid  Size")
	@Column(length=100)
	private String subtitle;
	
	@NotEmpty(message = "description cannot be Empty")
	@Size(min = 5, max = 2000, message = "Atleaste Write 5 characters and less than 530 charaters")
	@Column(length = 2000)
	private String description;
	
}
