package de.logicline.dsgvo.util;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class EmailUtil {
    private static final  String CONTENT_TYPE_STRING = "Content-type";
    private static final  String CONTENT_TYPE= "text/HTML; charset=UTF-8";
//TODO hard coded for now
    public void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader(CONTENT_TYPE_STRING, CONTENT_TYPE);
           /* msg.addHeader("format", "flowed");*/
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("helpdesk@logicline.de", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@logicline.de", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
