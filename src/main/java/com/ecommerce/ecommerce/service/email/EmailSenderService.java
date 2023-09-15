package com.ecommerce.ecommerce.service.email;

public interface EmailSenderService {
    void sendEmail(final String toEmail , final String subject, String body);
    String emailTemplateConfirmation(String name , String link);

    public String emailTemplateContact(String senderEmail,String message);
}
