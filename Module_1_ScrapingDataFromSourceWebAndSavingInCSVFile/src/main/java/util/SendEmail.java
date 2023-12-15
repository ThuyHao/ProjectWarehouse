package util;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
    public static void sendMail(String addressTo, String title, String message) {
        Util util = new Util();
        // Properties for mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        // Create an authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(util.from, util.password);
            }
        };

        // Create a mail session
        Session session = Session.getInstance(props, auth);

        // Create a MimeMessage
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setFrom(new InternetAddress(util.from, "Data WareHouse"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addressTo, false));
            msg.setSubject(title);
            msg.setSentDate(new Date());

            // Email content
            msg.setText(message, "UTF-8");

            // Send the email
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMail("newsofgame2023@gmail.com","Thành công", "lấy dữ liệu ngày ... thành công");
    }
}