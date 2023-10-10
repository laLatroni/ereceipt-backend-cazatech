package com.ereceipt.CAZAEORPROJECT.ERECEIPT_EMAIL_IMPLEMENTATION.EOR;

import com.ereceipt.CAZAEORPROJECT.ERECEIPT_SERVICE.EOR.EMAIL_SERVICE;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EMAIL_SERVICE_IMPL implements EMAIL_SERVICE{

    public static final String NEW_EMAIL = "Your Debit to Account Transaction is succesfull";
    @Value("$spring.mail.host")
    private String host;
    @Value("$spring.mail.username")
    private String fromEmail;

    @Autowired
    private final JavaMailSender emailSender;

    @Override
    public void sendEmailAttachmentMessage(String name, String to, String body)throws MessagingException {

    }
    @Override
    public void sendEmailAttachmentMessage(String to, String subject, String body, byte[] byteArray, String filename){
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(NEW_EMAIL);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(body);
            ByteArrayResource pdfAttachment = new ByteArrayResource(byteArray);
            mimeMessageHelper.addAttachment(filename, pdfAttachment);
            emailSender.send(mimeMessage);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }
}
