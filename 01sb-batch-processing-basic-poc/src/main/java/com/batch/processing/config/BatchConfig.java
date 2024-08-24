package com.batch.processing.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.batch.processing.listener.JobMonitoringListener;
import com.batch.processing.processor.BookDetailsProcessor;
import com.batch.processing.reader.BookDetailsReader;
import com.batch.processing.writer.BookDetailsWirter;

/**
 * @EnableBatchProcessing: Gives spring batch features through AutoConfiguration like 
 * giving InMemoryJobRepository, JobBuilderFactory, StepBuilerFactory and etc...
 * **/

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private JobMonitoringListener jobMonitoringListener;
	
	@Autowired
	private BookDetailsReader bookDetailsReader;
	
	@Autowired
	private BookDetailsProcessor bookDetailsProcessor;
	
	@Autowired
	private BookDetailsWirter bookDetailsWirter;
	
	//1. create Step object using StepBuilderFactory.
	@Bean(name = "step1")
	Step createStep1() {
		System.out.println("BatchConfig.createStep1()");
		return stepBuilderFactory.get("step1")
				.<String, String>chunk(2)
				.reader(bookDetailsReader)
				.writer(bookDetailsWirter)
				.processor(bookDetailsProcessor)
				.build();
	} 
	
	//2. create Job using JobBuilderFactory
	@Bean(name = "job1")
	Job createJob() {
		System.out.println("BatchConfig.createJob()");
		return jobBuilderFactory.get("job1")
				.incrementer(new RunIdIncrementer())
				.listener(jobMonitoringListener)
				.start(createStep1())
				.build();
	}
}
