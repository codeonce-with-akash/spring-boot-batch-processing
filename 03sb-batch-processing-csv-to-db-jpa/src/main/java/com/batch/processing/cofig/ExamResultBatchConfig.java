package com.batch.processing.cofig;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.batch.processing.entity.OExamResult;
import com.batch.processing.listener.JobMonitoringListener;
import com.batch.processing.model.IExamResult;
import com.batch.processing.processor.StudentDetailsProcessor;

@Configuration
@EnableBatchProcessing
public class ExamResultBatchConfig {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	//1. create job listener
	@Bean
	public JobExecutionListener createListener() {
		return new JobMonitoringListener();
	}
	
	//2. create reader
	@Bean(name = "reader")
	public FlatFileItemReader<IExamResult> createItemReader(){
		return new FlatFileItemReaderBuilder<IExamResult>()
				.name("csv-reader")
				.resource(new FileSystemResource("D:\\batch-processing-csv-files\\student_info.csv"))
				.delimited()
				.delimiter(",")
				.names("id", "name", "lastName", "dob", "percentage", "semester")
				.targetType(IExamResult.class)
				.build();
	}
	
	//3. create processor
	@Bean(name = "processor")
	public ItemProcessor<IExamResult, OExamResult> createItemProcessor(){
		return new StudentDetailsProcessor();
	}
	
	//4. create writer
	@Bean(name = "writer")
	public JpaItemWriter<OExamResult> createItemWriter(){
		return new JpaItemWriterBuilder<OExamResult>()
				.entityManagerFactory(entityManagerFactory)
				.build();
	}
	
	//5. create Step
	@Bean(name = "step1")
	public Step createStep1() {
		return stepBuilderFactory.get("step1")
				.<IExamResult, OExamResult>chunk(5)
				.reader(createItemReader())
				.processor(createItemProcessor())
				.writer(createItemWriter())
				.build();
	}
	
	//6. crate Job
	@Bean(name = "job")
	public Job createJob() {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.listener(createListener())
				.start(createStep1())
				.build();
	}
}
