package com;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
public class AmazonSESSample {
    static final String FROM = "info@metacampus.in";
    static final String FROMNAME = "Metacampus Admin";
    static final String TO = "chetna.sharma@metacube.com";
    
    static final String SMTP_USERNAME = "AKIAIVC7DMQ7T67PGSOQ";
    static final String SMTP_PASSWORD = "AljuZdO6/i7I+DbQNpHIwu//+taeoYajsWcGA6VlHyKE";
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    static final int PORT = 587;
    
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";
    
    static final String BODY = new String(
            "<h1>Amazon SES SMTP Email Test</h1>"+
            "<p>This email was sent with Amazon SES using the "+ 
            "<a href='https://github.com/javaee/javamail'>Javamail Package</a>"+
            " for <a href='https://www.java.com'>Java</a>."
        );
    public static void main(String[] args) throws Exception {
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT); 
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST);
        Session session = Session.getDefaultInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html");
        
        Transport transport = session.getTransport();
        try
        {
            System.out.println("Sending...");
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}