package ru.leverx.dealerStatistics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan("ru.leverx.dealerStatistics.email")
@PropertySource(value = {"classpath:application.properties"})
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String MAIL_HOST;

    @Value("${spring.mail.port}")
    private Integer MAIL_PORT;

    @Value("${spring.mail.username}")
    private String MAIL_USERNAME;

    @Value("${spring.mail.password}")
    private String MAIL_PASSWORD;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String MAIL_SMTP_AUTH;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String MAIL_SMTP_STARTTLS_ENABLE;

    @Value("${spring.mail.debug}")
    private String DEBUG;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(MAIL_HOST);
        mailSender.setPort(MAIL_PORT);
        mailSender.setUsername(MAIL_USERNAME);
        mailSender.setPassword(MAIL_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
        props.put("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
        props.put("mail.debug", DEBUG);

        return mailSender;
    }
}
