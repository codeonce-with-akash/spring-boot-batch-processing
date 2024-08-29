package com.batch.processing.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {
	private Long id;
	private Date dob;
	private String grade;
	private String lastName;
	private String name;
	private Double percentage;
	private String result;
	private Integer semester;
}
