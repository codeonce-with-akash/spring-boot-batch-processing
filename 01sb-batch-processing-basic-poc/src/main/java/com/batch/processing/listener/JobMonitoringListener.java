package com.batch.processing.listener;

import java.util.Date;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("jmListener")
public class JobMonitoringListener implements JobExecutionListener {

	private Long startTime, endTime;

	public JobMonitoringListener() {
		System.out.println("JOBMONITORINGLISTENER :: 0-PARAM CONSTRUCTOR");
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JobMonitoringListener.beforeJob()");
		System.out.println("JOB IS ABOUT TO BEGING AT :: " + new Date());
		startTime = System.currentTimeMillis();
		System.out.println("JOB STATUS :: " + jobExecution.getStatus());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		System.out.println("JobMonitoringListener.afterJob()");
		System.out.println("JOB COMPLETED AT :: " + new Date());
		endTime = System.currentTimeMillis();
		System.out.println("JOB STATUS :: " + jobExecution.getStatus());
		System.out.println("JOB EXECUTION TIME :: " + (endTime - startTime));
		System.out.println("JOB EXIT STATUS :: " + jobExecution.getExitStatus());
	}

}
