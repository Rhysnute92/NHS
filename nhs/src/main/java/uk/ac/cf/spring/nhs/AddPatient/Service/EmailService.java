package uk.ac.cf.spring.nhs.AddPatient.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    // Simple Mail Sender Setup
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("derbyandburton.mscproject@gmail.com");
        mailSender.send(message);
    }

    // Setup for sending an email to the provider following questionnaire submission
    public void sendEmailWithQuestionnaire(String to, String subject, String text, byte[] attachmentBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom("derbyandburton.mscproject@gmail.com");

        ByteArrayResource attachment = new ByteArrayResource(attachmentBytes);
        helper.addAttachment("questionnaire.pdf", attachment);
        mailSender.send(message);
    }
}

