package com.batch.processing.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CSVToDBBatchProcessingTestRunner implements CommandLineRunner {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	@Override
	public void run(String... args) throws Exception {
		//1. prepare additional job parameters
		JobParameters params = new JobParametersBuilder()
								.addLong("sysTime", System.currentTimeMillis())
								.toJobParameters();
		//2 launch the job
		JobExecution jobExecution = jobLauncher.run(job, params);
		log.info("JOB COMPLETION STATUS :: {}", jobExecution.getStatus());
	}

}
