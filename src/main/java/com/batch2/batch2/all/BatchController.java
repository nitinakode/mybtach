package com.batch2.batch2.all;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@RestController
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;
    @PostMapping("/uploadAndProcess")
    public ResponseEntity<String> uploadAndProcessFile(@RequestParam("file") MultipartFile file) {
        try {
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            // Save the uploaded file to a temporary location
            String uploadDir = "src/main/resources/folder/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path tempFilePath = uploadPath.resolve(file.getOriginalFilename()+""+ file.getOriginalFilename());
            System.out.println("fileName");
            System.out.println(file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFilePath);

            // Trigger the batch job with the uploaded file path
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("inputFilePath", tempFilePath.toString())
                    .addString("fileName", file.getOriginalFilename())
                    .toJobParameters();
            System.out.println(         jobParameters.getParameters().get("inputFilePath"));

            jobLauncher.run(job, jobParameters);

            // Cleanup: Delete the temporary file
            Files.deleteIfExists(tempFilePath);

            return ResponseEntity.ok("File uploaded and batch processing started successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file or starting batch job: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file or starting batch job: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> get()
    {
        return new ResponseEntity<>("HI AM I AVAILABLE",HttpStatus.OK);
    }

}
