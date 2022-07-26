package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.api.EmailSendServiceIpm;

@Component
public class SchedulingTask {
	
	@Autowired
	public EmailSendServiceIpm emailSendServiceIpm;
	
    //表示每隔3秒
    // @Scheduled(fixedRate = 3000)
    
    // 表示方法执行完成后5秒
    // @Scheduled(fixedDelay = 5000)

    // 表示每五秒执行一次
    //@Scheduled(cron = "*/30 * * * * ?")
    @Scheduled(fixedDelay = 30000)
    public void TestTask() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
        emailSendServiceIpm.sendAttachmentEmail("html");
    }
}
