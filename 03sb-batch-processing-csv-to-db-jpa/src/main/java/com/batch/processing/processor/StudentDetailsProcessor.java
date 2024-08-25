package com.batch.processing.processor;

import java.time.LocalDate;

import org.springframework.batch.item.ItemProcessor;

import com.batch.processing.constant.ExamConstants;
import com.batch.processing.entity.OExamResult;
import com.batch.processing.model.IExamResult;

public class StudentDetailsProcessor implements ItemProcessor<IExamResult, OExamResult> {

	@Override
	public OExamResult process(IExamResult result) throws Exception {
		if(result.getPercentage() >= 35) {
			OExamResult passResult = new OExamResult();
			passResult.setId(result.getId());
			passResult.setName(result.getName());
			passResult.setLastName(result.getLastName());
			passResult.setDob(LocalDate.parse(result.getDob()));
			passResult.setPercentage(result.getPercentage());
			passResult.setGrade(getGrade(result.getPercentage()));
			passResult.setResult(ExamConstants.PASS_RESULT);
			passResult.setSemester(result.getSemester());
			return passResult;
		}else{
			OExamResult failResult = new OExamResult();
			failResult.setId(result.getId());
			failResult.setName(result.getName());
			failResult.setLastName(result.getLastName());
			failResult.setDob(LocalDate.parse(result.getDob()));
			failResult.setPercentage(result.getPercentage());
			failResult.setGrade(getGrade(result.getPercentage()));
			failResult.setResult(ExamConstants.FAIL_RESULT);
			failResult.setSemester(result.getSemester());
			return failResult;
		}
	}

	private String getGrade(Double percentage) {
		if(percentage >= 80) 
			return "A";
		else if(percentage >= 70)
			return "B";
		else if(percentage >= 60)
			return "C";
		else 
			return "D";
	}

}
