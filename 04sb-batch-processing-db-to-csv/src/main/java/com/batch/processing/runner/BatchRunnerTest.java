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
public class BatchRunnerTest implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Override
	public void run(String... args) throws Exception {
		//prepare job parameters
		JobParameters parameters = new JobParametersBuilder().addLong("sys-time", System.currentTimeMillis()).toJobParameters();
		//launch the job by passing job object and additional parameters
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		log.info("JOB COMPLETION STATUS :: " + jobExecution.getStatus());
	}

}
