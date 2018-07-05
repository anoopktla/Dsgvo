package de.logicline.dsgvo.util;

import de.logicline.dsgvo.model.Adv;
import de.logicline.dsgvo.model.Customer;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailUtil {
    private static final String CONTENT_TYPE_STRING = "Content-type";
    private static final String CONTENT_TYPE = "text/HTML; charset=UTF-8";

    @Autowired
    PdfUtil pdfUtil;

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


    public void sendEmail(String toEmail, String subject, String body,Customer customer) {
        try {

            List lst = new ArrayList<>();
            lst.add(new Adv());
            customer.setAdv(lst);
            customer.getAdv().get(0).setPdfDocument(IOUtils.toByteArray(pdfUtil.createPdf(customer)));
            ByteArrayDataSource bds = new ByteArrayDataSource(customer.getAdv().get(0).getPdfDocument(), "application/pdf");

            Message message  = new MimeMessage(getSession());
            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body,CONTENT_TYPE);
            MimeBodyPart attachPart = new MimeBodyPart();

            attachPart.setDataHandler(new DataHandler(bds));
            attachPart.setFileName("adv.pdf");
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachPart);


// sets the multipart as message's content
            message.setContent(multipart);
            //set message headers


            message.setFrom(new InternetAddress(emailUserName, "NoReply-JD"));

            message.setReplyTo(InternetAddress.parse(emailUserName, false));

            message.setSubject(subject);



            message.setSentDate(new Date());

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(message);


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
