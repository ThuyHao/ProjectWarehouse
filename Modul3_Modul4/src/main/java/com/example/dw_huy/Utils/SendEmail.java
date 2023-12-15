package com.example.dw_huy.Utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class SendEmail {


    public static void sendMail(String title, String message) {
        Properties emailProperties = new Properties();
        try (InputStream input = SendEmail.class.getClassLoader().getResourceAsStream("config.properties")) {
            emailProperties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String email_from = emailProperties.getProperty("email_from");
        String password = emailProperties.getProperty("email_app_password");
        // Properties for mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        // Create an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email_from, password);
            }
        };

        // Create a mail session
        Session session = Session.getInstance(props, auth);

        // Create a MimeMessage
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(email_from, "Data WareHouse"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailProperties.getProperty("email_to"), false));
            msg.setSubject(title);
            msg.setSentDate(new Date());

            // Email content
            msg.setText(message, "UTF-8");

            // Send the email
            Transport.send(msg);
            System.out.println("Email sent successfully");
        } catch (Exception e) {
            System.out.println("Error in sending email");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMail("Thành công", "lấy dữ liệu ngày ... thành công");

    }
}