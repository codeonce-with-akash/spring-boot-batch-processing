package com.batch.processing.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENT_INFO")
public class OExamResult {
	@Id
	private Long id;
	private String name;
	private String lastName;
	private LocalDate dob;
	private Double percentage;
	private Integer semester;
	private String grade;
	private String result;
}
