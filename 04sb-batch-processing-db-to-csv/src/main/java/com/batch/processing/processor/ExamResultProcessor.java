package com.batch.processing.processor;

import org.springframework.batch.item.ItemProcessor;

import com.batch.processing.model.ExamResult;

public class ExamResultProcessor implements ItemProcessor<ExamResult, ExamResult> {

	@Override
	public ExamResult process(ExamResult result) throws Exception {
		if (result.getResult().equalsIgnoreCase("FAIL"))
			return result;
		else
			return null;
	}

}
