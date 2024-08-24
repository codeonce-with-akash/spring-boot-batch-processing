package com.batch.processing.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.processing.model.Employee;

public class EmployeeInfoItemProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee emp) throws Exception {
		if (emp.getEmpSal() >= 90000) {
			emp.setEmpGrossSal(emp.getEmpSal() + (emp.getEmpSal() * 0.4));
			emp.setEmpNetSal(emp.getEmpGrossSal() - (emp.getEmpGrossSal() * 0.2));
			return emp;
		} 
		else
			return null;

		/*
		 else {
		 	emp.setEmpGrossSal(emp.getEmpGrossSal());
			emp.setEmpNetSal(getEmpNetSal());
		 }
		 */
	}

}
