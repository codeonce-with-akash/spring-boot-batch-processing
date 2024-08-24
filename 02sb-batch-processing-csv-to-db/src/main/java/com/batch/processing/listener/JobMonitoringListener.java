package com.batch.processing.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobMonitoringListener implements JobExecutionListener {

	private Long startTime, endTime;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = System.currentTimeMillis();
		log.debug("JOB STARTED AT :: {}", new Date());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		endTime = System.currentTimeMillis();
		log.debug("JOB EXECUTION COMPLETED AT :: {}", new Date());
		log.debug("JOB EXECUTION TIME :: {}ms", (endTime - startTime));
		log.debug("JOB COMPLETION STATUS :: {}", jobExecution.getStatus());
	}

}
