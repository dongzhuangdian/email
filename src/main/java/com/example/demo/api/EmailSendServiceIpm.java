package com.example.demo.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.common.EmailSendService;

@Component
public class EmailSendServiceIpm implements EmailSendService {


    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;
	
	@Override
	public boolean sendSimpleEmail(String toEmailAddress, String subject, String text) {
        //定制纯文本邮件信息SimpleMailMessage
        System.out.println("sendSimpleEmail函数入口");
		System.out.println("发件人地址："+ fromEmailAddress);
		SimpleMailMessage message=new SimpleMailMessage();
        try {
            message.setFrom(fromEmailAddress);  //设置发件箱
            message.setTo(toEmailAddress);  //设置收件箱
            message.setSubject(subject);  //设置邮件主题
            message.setText(text);  //设置邮件内容
            System.out.println("邮件内容封装完毕"+message.toString());
            mailSender.send(message);  //调用Java封装好的发送方法
            return true;
        }catch (Exception e){
            System.err.println(e);
            return false;
        }
	}

	@Override
	public boolean sendAttachmentEmail(String id) {
		
		//计划向txt中依次写入四行，收件人姓名、收件人内容、收件人邮箱、收件人附件、邮件主题
		String filePathhtml = "D:\\soft\\jdk8u314\\workspace\\demo\\src\\main\\java\\com\\example\\demo\\api\\"+ id +".txt";
		String strTmp = "";
		String toEmailAddress=null;
		String subject=null;
		String attachFilePath=null;
		ArrayList <String> arlist = new ArrayList <String>();
		try {
			FileInputStream fileInputStream = new FileInputStream(filePathhtml);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
	        strTmp = bufferedReader.readLine();
	        while(strTmp != null) {
	        	arlist.add(strTmp);
	        	strTmp = bufferedReader.readLine();
	        }
	        bufferedReader.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.out.println(arlist.toString());
		
		if(arlist.size() != 5) {
			System.err.println("数量错误");;
		}else {
			toEmailAddress = arlist.get(2);
			subject = arlist.get(4);
			attachFilePath = arlist.get(3);
			strTmp = arlist.get(1);
		}
		
		//定制复杂邮件信息MimeMessage
        MimeMessage message=mailSender.createMimeMessage();
        try {
            //使用MimeMessageHelper帮助类，并设置multipart多部件使用为true。帮助将邮件信息封装到MimeMessage message中
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(fromEmailAddress,"BIISC"); //设置发件箱
//            helper.setFrom(fromEmailAddress, subject);
            helper.setTo(toEmailAddress);   //设置收件箱
            helper.setBcc("18310401860@163.com");
            helper.setSubject(subject);  //设置邮件主题
            helper.setText(strTmp,true);  //设置邮件内容
            //设置邮件静态资源
            //FileSystemResource res=new FileSystemResource(new File(rscPath));
            //helper.addInline(rscId, res);
            //设置附件地址
            FileSystemResource file=new FileSystemResource(new File(attachFilePath));
            helper.addAttachment("20220723.pptx", file);
            //发送邮件
            mailSender.send(message);
            System.out.println("复杂邮件发送成功");
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("复杂邮件发送失败。。。。。。");
            return false;
        }
	}

}
