package com;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class DemoServlet extends HttpServlet {
	
	static final String FROM = "notifications@tpohub.com";
    static final String FROMNAME = "Metacampus Admin";
    static final String TO = "chetna.sharma@metacube.com";
    
    static final String SMTP_USERNAME = "AKIAJWGPVTS34N2TYDLQ";
    /*AKIAIVC7DMQ7T67PGSOQ*/
    static final String SMTP_PASSWORD = "AtFVVdgBahigTaHfn+xvjMjdRPdKYeYdJL4SWDKmPVeu";
    /*"AljuZdO6/i7I+DbQNpHIwu//+taeoYajsWcGA6VlHyKE"*/
    static final String HOST = "email-smtp.us-east-1.amazonaws.com";
    static final int PORT = 587;
    
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java) GAE";
    
    static final String BODY = new String(
            "<h1>Amazon SES SMTP Email Test GAE</h1>"+
            "<p>This email was sent with Amazon SES using the "+ 
            "<a href='https://github.com/javaee/javamail'>Javamail Package</a>"+
            " for <a href='https://www.java.com'>Java</a>."
        );
    
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			System.out.println("in-do-get...");
			System.err.println(SUBJECT);

			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", 587); 
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", HOST);
			resp.getWriter().println(props);
			Session session = Session.getDefaultInstance(props);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(FROM,FROMNAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setContent(BODY,"text/html");
			
			resp.getWriter().println("sending...");
			
			Transport transport = session.getTransport();
			try
			{
			    System.err.println("Sending...");
			    try {
					transport.connect(HOST,PORT, SMTP_USERNAME, SMTP_PASSWORD);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					resp.getWriter().println("in error....");
					resp.getWriter().println("Error message: " + e.getMessage());
				}
			    resp.getWriter().println(transport);
			    transport.sendMessage(msg, msg.getAllRecipients());
			    resp.getWriter().println("after send message...");
			    System.err.println("Email sent!");
			    resp.getWriter().println("Mail sent >>>");
			}
			catch (Exception ex) {
			    ex.printStackTrace();
			    System.err.println("The email was not sent.");
			    System.err.println("Error message: " + ex.getMessage());
			    resp.getWriter().println("Error message: " + ex.getMessage());
			}
			finally
			{
			    // Close and terminate the connection.
			    transport.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().println("Error in send mail");
		}
	}
}
