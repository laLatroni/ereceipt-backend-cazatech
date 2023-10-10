package com.ereceipt.CAZAEORPROJECT.ERECEIPT_SERVICE.EOR;

import jakarta.mail.MessagingException;

public interface EMAIL_SERVICE {
    void sendEmailAttachmentMessage(String name, String to, String body) throws MessagingException;
    void sendEmailAttachmentMessage(String recipientEmail, String subject, String body, byte[] byteArray, String s);
}
