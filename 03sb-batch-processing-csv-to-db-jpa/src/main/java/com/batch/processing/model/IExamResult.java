package com.batch.processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IExamResult {
	private Long id;
	private String name;
	private String lastName;
	private String dob;
	private Double percentage;
	private Integer semester;
}
