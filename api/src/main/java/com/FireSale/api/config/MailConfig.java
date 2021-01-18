package com.FireSale.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MailConfig {

    public String from = "";

    @Bean
    public JavaMailSender javaMailSender() {
        String smtpserver = "";
        String username = "";
        String password = "";

        try (InputStream input = new FileInputStream("c:/tmp/firesalemail.properties")) {
            Properties mailconfig = new Properties();
            mailconfig.load(input);
            smtpserver = mailconfig.getProperty("mail.smtpserver");
            username = mailconfig.getProperty("mail.username");
            password = mailconfig.getProperty("mail.password");
            from = mailconfig.getProperty("mail.from");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpserver);
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
