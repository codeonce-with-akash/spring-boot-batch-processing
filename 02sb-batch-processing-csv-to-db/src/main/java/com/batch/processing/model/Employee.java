package com.batch.processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private Integer empId;
	private String empName;
	private String empDesg;
	private Double empSal;
	private String empAddrs;
	private Double empGrossSal;
	private Double empNetSal;
}
