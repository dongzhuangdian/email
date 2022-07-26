package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.EmailSendServiceIpm;

@RestController
@SpringBootApplication
@EnableScheduling
public class TestSpringBoot{

    @Value("${spring.mail.username}")
    private String fromEmailAddress;
    
    @Autowired
    private EmailSendServiceIpm emailSendServiceIpm;
    
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @RequestMapping(value="/mail")
    public String greeting(@RequestParam Map<String, String> params){
    	
//    	emailSendServiceIpm.sendSimpleEmail("1171335116@qq.com", "ceshi", "ceshi");
    	try {
    		emailSendServiceIpm.sendAttachmentEmail(params.get("id"));
		} catch (Exception e) {
			System.err.println(e);
		}
    	
    	
        return "hello world";
    }
}