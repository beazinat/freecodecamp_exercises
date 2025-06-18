package com.example.springredditclone.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.springredditclone.dto.NotificationEmail;
import com.example.springredditclone.exceptions.SpringRedditException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// This class is responsible for sending emails in the Reddit clone application. It uses Spring's JavaMailSender to send emails asynchronously, allowing the application to continue processing without waiting for the email to be sent. The service constructs the email message using a MimeMessagePreparator, which sets the recipient, subject, and body of the email. If an error occurs while sending the email, it logs the error and throws a custom exception, ensuring that any issues with email delivery are handled gracefully. The critical part of this service is the `sendMail` method, which encapsulates the logic for preparing and sending an email, making it reusable throughout the application.
@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient(), e);
        }
    }

}