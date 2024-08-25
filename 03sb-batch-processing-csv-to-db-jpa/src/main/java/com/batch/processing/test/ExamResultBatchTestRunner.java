package com.batch.processing.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExamResultBatchTestRunner implements CommandLineRunner{
	
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	
	
	@Override
	public void run(String... args) throws Exception {
		//1. prepare additional parameters
		JobParameters parameters = new JobParametersBuilder()
						.addLong("sys-Time", System.currentTimeMillis())
						.toJobParameters();
				
		//2. launch the job
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		log.info("JOB STATUS FROM TEST CLASS :: {}", jobExecution.getStatus());
		log.info("JOB EXIT STATUS FROM TEST CLASS :: {}", jobExecution.getExitStatus());
	}
	
	
	
	/*
	@Scheduled(cron = "${my.cron}")
	public void runJob() {
		//1. prepare additional parameters
		JobParameters parameters = new JobParametersBuilder()
				.addLong("sys-Time", System.currentTimeMillis())
				.toJobParameters();
		
		//2. launch the job
		try {
			JobExecution jobExecution = jobLauncher.run(job, parameters);
			log.info("JOB STATUS FROM TEST CLASS :: {}", jobExecution.getStatus());
			log.info("JOB EXIT STATUS FROM TEST CLASS :: {}", jobExecution.getExitStatus());
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}
	*/
}
