package com.batch2.batch2.all;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
public class BatchConfig {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private  JobRepository jobRepository;

    @Bean
    public Job jobBean(JobRepository jobRepository,
                       JobCompletionNotificationImpl listener,
                       Step steps
    ) {
        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(

            ItemReader<AnswerDto> reader,
            ItemProcessor<AnswerDto, Answer> processor,
            ItemWriter<Answer> writer
    ) {
        return new StepBuilder("jobStep", jobRepository)
                .<AnswerDto,Answer>chunk(5, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();

    }

    //    reader

    @Bean
    @StepScope
    public FlatFileItemReader<AnswerDto> reader(@Value("#{jobParameters['inputFilePath']}") String inputFilePath) {
        return new FlatFileItemReaderBuilder<AnswerDto>()
                .name("itemReader")
                .resource(new FileSystemResource(inputFilePath))
                .delimited()
                .names("name", "school", "clas")
                .targetType(AnswerDto.class)
                .build();
    }


//    processor

    @Bean
    public ItemProcessor<AnswerDto, Answer> itemProcessor() {
        return new CustomItemProcessor();
    }

//    writer

    @Bean
    public ItemWriter<Answer> itemWriter(DataSource dataSource) {
        return item->{
            item.getItems().forEach(
                    items->
                            writeJsonFile(items)
            );
        };

    }
    public void writeJsonFile(Answer student) {
        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert Student object to JSON string
            String json = objectMapper.writeValueAsString(student);

            // Specify the file path for the JSON file
            String filePath = "src/main/resources/folder/abc.json"; // Adjust the path as needed

            // Create a File object
            File file = new File(filePath);

            // Write JSON string to the file
            objectMapper.writeValue(file, student);

            // Print success message
            System.out.println("JSON file created successfully at: " + filePath);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}