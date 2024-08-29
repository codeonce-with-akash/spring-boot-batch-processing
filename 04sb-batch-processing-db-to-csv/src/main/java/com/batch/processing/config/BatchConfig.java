package com.batch.processing.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.batch.processing.listener.JobMonitoringListener;
import com.batch.processing.mapper.ExamResultRowMapper;
import com.batch.processing.model.ExamResult;
import com.batch.processing.processor.ExamResultProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public JobExecutionListener createListener() {
		return new JobMonitoringListener();
	}
	
	@Bean
	public ItemReader<ExamResult> createReader(){
		//1. create JdbcCursorItemReader class object.
		JdbcCursorItemReader<ExamResult> reader = new JdbcCursorItemReader<>();
		//2. set logical name
		reader.setName("exam-result-reader");
		//3. specify DataSource
		reader.setDataSource(dataSource);
		//4. specify SQL query
		reader.setSql("SELECT ID,DOB,GRADE,LAST_NAME,NAME,PERCENTAGE,RESULT,SEMESTER FROM STUDENT_INFO");
		//5. specify RowMapper
		reader.setRowMapper(new ExamResultRowMapper());
		return reader;
	}
	
	@Bean
	public ItemProcessor<ExamResult, ExamResult> createProcessor(){
		return new ExamResultProcessor();
	}
	
	@Bean
	public ItemWriter<ExamResult> createWriter(){
		//1. FlatFileItemWriter class object
		FlatFileItemWriter<ExamResult> writer = new FlatFileItemWriter<>();
		//2. set logical name
		writer.setName("exam-result-writer");
		//3. specify the destination csv file location
		writer.setResource(new FileSystemResource("D:\\batch-processing-csv-files\\exam-result.csv"));
		//4. specify LineAggregator by passing delimiter and field extractor
		writer.setLineAggregator(new DelimitedLineAggregator<>() {{
			//delimiter
			setDelimiter(",");
			//field extractor
			setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
				//specify name to extracted field values
				setNames(new String[]{"id", "dob", "grade", "lastName", "name", "percentage", "result", "semester"});
			}});
			
		}});
		return writer;
	}
	
	@Bean(name = "step1")
	public Step createStep1() {
		return stepBuilderFactory.get("step1")
				.<ExamResult, ExamResult>chunk(5)
				.reader(createReader())
				.processor(createProcessor())
				.writer(createWriter())
				.build();
	}
	
	@Bean(name = "job")
	public Job createJob() {
		return jobBuilderFactory.get("job")
		.incrementer(new RunIdIncrementer())
		.listener(createListener())
		.start(createStep1())
		.build();
	}
	
}
