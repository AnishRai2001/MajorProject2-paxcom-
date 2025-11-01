package com.example.jwtService.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;
    
    // Upload single file to a folder
    public List<String> uploadFile(MultipartFile[] files) {
    	List<String>urls=new ArrayList<>();
    	
    	for(MultipartFile file: files) {
    		String filename= UUID.randomUUID().toString()+ " " + file.getOriginalFilename();
    		try {
    			 PutObjectRequest putObjectRequest = PutObjectRequest.builder()//now we are creating request for  s3upload
                         .bucket(bucketName)
                         .key(filename)
                         .contentType(file.getContentType())
                         .build();
    			 
    		s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    		
    		
    		String url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + filename;
             urls.add(url);

    		
    		}
    		 catch (IOException e) {
                 throw new RuntimeException("Error uploading file to S3", e);
    	
    }
    }
    	return urls;
    }
}

     
    	
    	
    	
    	
    