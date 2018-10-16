package com.njit.sharefair.network;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtilities {

	public static boolean sendEmail(String email, String text) {

        final String username = "shivasrinivasgadicherla@gmail.com";
        final String password = "srini547";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("shivasrinivasgadicherla@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
            message.setSubject("Share Fair");
            message.setText(text);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
	}

}
