package com.batch.processing.config;

import javax.batch.api.listener.JobListener;
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
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.batch.processing.listener.JobMonitoringListener;
import com.batch.processing.model.Employee;
import com.batch.processing.processor.EmployeeInfoItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	/** 3. inject JobBuilderFactory, StepBuilderFactory and Datasource using @Autowired **/
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	
	/** 1. configure ItemReader using @Bean method. **/
	@Bean(name = "empReader")
	public ItemReader<Employee> createItemReader() {
		// a. create object for FlatFileItemReader
		FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();

		// b. specify the source csv file location
		reader.setResource(new ClassPathResource("employee_info.csv"));
		// reader.setResource(new
		// FileSystemResource("C:\\Users\\akash\\OneDrive\\Desktop\\employee_info.csv"));
		// reader.setResource(new UrlResource("http://amazon.com/s3/csv/employee_info.csv"));

		// c. specify LineMapper to get each line from csv file.
		reader.setLineMapper(new DefaultLineMapper<Employee>() {
			{
				// inside this instance block set LineTokenizer to get values from the line based on given delimiter
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setDelimiter(",");
						setNames("empId", "empName", "empDesg", "empSal", "empAddrs");
					}
				});
				// d. specify FieldSetMapper to convert line content into model class.
				setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
					{
						setTargetType(Employee.class);
					}
				});
			}
		});
		return reader;
	} //close reader
	
	/** 2. configure ItemProcessor using @Bean method. **/
	@Bean
	public ItemProcessor<Employee, Employee> createItemProcessor(){
		return new EmployeeInfoItemProcessor();
	} //close processor
	
	/** 4. configure JobExecutionListener using @Bean method. **/
	@Bean
	public JobExecutionListener createJobListener() {
		return new JobMonitoringListener();
	} // close listener
	
	/** 5. configure ItemWriter using @Bean method. **/ 
	@Bean
	public ItemWriter<Employee> createItemWriter(){
		//a. create JdbcBatchItemWriter class object
		JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
		//b. set Datasource
		writer.setDataSource(dataSource);
		//c. set SQL query with named parameters
		writer.setSql("INSERT INTO BATCH_PRO_EMPLOYEE VALUES(:empId,:empName,:empDesg,:empSal,:empAddrs,:empGrossSal,:empNetSal)");
		//d. set model class object as SqlParameterSourceProvider
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return writer;
	} //close writer
	
	/** 5. create Step object **/
	@Bean(name = "step1")
	public Step createStep1() {
		return stepBuilderFactory.get("step1")
				.<Employee, Employee>chunk(5)
				.reader(createItemReader())
				.processor(createItemProcessor())
				.writer(createItemWriter())
				.build();		
	}
	
	/** 6. create Job object **/
	@Bean(name = "job")
	public  Job createJob() {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.listener(createJobListener())
				.start(createStep1())
				.build();
	}
	
}
