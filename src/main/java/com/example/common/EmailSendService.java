package com.example.common;


public interface EmailSendService {

    //纯文本邮件信息发送
    public boolean sendSimpleEmail(String toEmailAddress, String subject, String text);

    //带附件邮件信息发送
    public boolean sendAttachmentEmail(String id);

}
