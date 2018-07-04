package de.logicline.dsgvo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailUtil {
    private static final String CONTENT_TYPE_STRING = "Content-type";
    private static final String CONTENT_TYPE = "text/HTML; charset=UTF-8";

    @Value("${email.username}")
    private String emailUserName;

    @Value("${email.password}")
    private String emailPassword;

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.auth}")
    private boolean smtpAuthRequired;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttlsEnabled;

    @Value("${mail.smtp.port}")
    private String smtpPort;


    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(getSession());
            //set message headers
            msg.addHeader(CONTENT_TYPE_STRING, CONTENT_TYPE);
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(emailUserName, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(emailUserName, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);


        } catch (Exception e) {
           System.out.println(e);
        }
    }

    private Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", smtpAuthRequired);
        props.put("mail.smtp.starttls.enable", starttlsEnabled);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUserName, emailPassword);
            }
        });
    }

}
