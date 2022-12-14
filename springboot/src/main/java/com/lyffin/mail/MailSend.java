package com.lyffin.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RestController
public class MailSend {
    @Autowired
    private JavaMailSenderImpl mailSender;

    //发送简单邮件
    @GetMapping("/mail")
    public String sendMail(String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试");
        message.setText(msg);
        message.setTo("229114260@qq.com");
        message.setFrom("229114260@qq.com");
        mailSender.send(message);
        return "send success!!!";
    }

    //发送复杂邮箱
    @GetMapping("/mail2")
    public String sendMail2() throws MessagingException {
        //一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //标题
        helper.setSubject("复杂邮件");
        //这里可以识别html标签
        helper.setText("<p style='color:red'>听我说谢谢你</p>", true);
        //附件
        helper.addAttachment("1.jpg", new File("C:\\Users\\li.feng39\\Downloads\\test.jpg"));
        helper.addAttachment("2.jpg", new File("C:\\Users\\li.feng39\\Downloads\\test.jpg"));
        helper.setTo("229114260@qq.com");
        helper.setFrom("229114260@qq.com");
        mailSender.send(mimeMessage);
        return "send success!!!";
    }
}
