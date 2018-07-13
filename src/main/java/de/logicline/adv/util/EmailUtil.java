package de.logicline.adv.util;

import de.logicline.adv.model.Adv;
import de.logicline.adv.model.Customer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class EmailUtil {
    private static final String CONTENT_TYPE_STRING = "Content-type";
    private static final String CONTENT_TYPE = "text/HTML; charset=UTF-8";
    private  static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

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


    public boolean sendEmail(String toEmail, String subject, String body,Customer customer) {
        try {

            List lst = new ArrayList<>();
            lst.add(new Adv());
            customer.setAdv(lst);
            customer.getAdv().get(0).setAdvInPdfFormat(IOUtils.toByteArray(pdfUtil.createPdf(customer)));
            ByteArrayDataSource bds = new ByteArrayDataSource(customer.getAdv().get(0).getAdvInPdfFormat(), "application/pdf");
           String html="<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                   "<!-- saved from url=(0050)https://epay.kwa.kerala.gov.in/paymentResponse.php -->\n" +
                   "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>\n" +
                   "\n" +
                   "Sample html to test email\n" +
                   "</body></html>";
            Message message  = new MimeMessage(getSession());
            Multipart multipart = new MimeMultipart();

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(html,CONTENT_TYPE);
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

            return  true;


        } catch (Exception e) {
            LOGGER.error("error sending email ",e);
        }
        return false;
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
