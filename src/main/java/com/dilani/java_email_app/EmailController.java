package com.dilani.java_email_app;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @RequestMapping("/send-email")
    public String sendEmail() {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("dilani.shyamali01@gmail.com");
            simpleMailMessage.setTo("dilani.shyamali01@gmail.com");
            simpleMailMessage.setSubject("Sample Email");
            simpleMailMessage.setSubject("This is my first email body");

            mailSender.send(simpleMailMessage);
            return "Email sent successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @RequestMapping("/send-email-with-attachment")
    public String sendEmailWithAttachment() {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("dilani.shyamali01@gmail.com");
            helper.setTo("dilani.shyamali01@gmail.com");
            helper.setSubject("Java Email with Attachment");

            helper.setText("Please refer the attachment attached below");


            mailSender.send(mimeMessage);
            return "Email sent successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    @RequestMapping("/send-email-with-html")
    public String sendEmailWithHtml() {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("dilani.shyamali01@gmail.com");
            helper.setTo("dilani.shyamali01@gmail.com");
            helper.setSubject("Java Email with Attachment");

            try(var inputStream = Objects.requireNonNull(EmailController.class.getResourceAsStream("/templates/email-content.html"))){
                helper.setText(new String(inputStream.readAllBytes() , StandardCharsets.UTF_8) ,true
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            mailSender.send(mimeMessage);
            return "Email sent successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
