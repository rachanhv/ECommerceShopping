package vinformax.vinmart.service;

import android.content.Intent;
import android.net.Uri;


import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import vinformax.vinmart.user.LoginScreen;

public class EmailAttachmentSender{
	

	public static String result="Sending failed";
    public static void sendEmailWithAttachments(String host, String port,
                                                final String userName, final String password, String toAddress,
                                                String subject, String message, String[] attachFiles)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        
        
        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        messageBodyPart.setText(message);

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        String filename = "/storage/sdcard0/VinKart"+ LoginScreen.csvfilename
				+ ".csv";
//        File file = new File("/storage/sdcard0/VinKart"+ Assignallvarible.csvfilename
//				+ ".csv");
        String fName;
        fName = "VinKart"+ LoginScreen.csvfilename
				+ ".csv";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fName);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
//        message.setContent(multipart);

        // Send message
//        Transport.send(message);
        
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
        result="sent successfully..";
        
        
 
    }
 
    /**
     * Test sending e-mail with attachments
     */
//    public static void main(String[] args) {
    public void mail(){
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "androidt763@gmail.com";
        String password = "android321";
        Uri u1=null;
 
        // message info

//        String aa="Html";
//        String link_val = "www.google.com";
//        		String body = "<a href=\"" + link_val + "\">" + link_val+ "</a>";
//
////        		intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));
//        		String s=android.content.Intent.EXTRA_TEXT.format(aa, Html.fromHtml(body));
//        		s.Html.fromHtml(body);
        String firstCharUpperCase = Character.toUpperCase(LoginScreen.customer_nameglobal.charAt(0)) + LoginScreen.customer_nameglobal.substring(1);
//        String firstCharUpperCase = Assignallvarible.customer_nameglobal ;
        String mailTo = "subbiangoutham@gmail.com";
        String subject = "Order details of "+ firstCharUpperCase+"("+LoginScreen.customerid_cartandfav+")";
        String message = "Hello, \n\nPlease find the attached order details of "
				+firstCharUpperCase+"("+LoginScreen.customerid_cartandfav+")";
 
        // attachments
        String[] attachFiles = new String[1];
        File file = new File("/storage/sdcard0/VinKart"+ LoginScreen.csvfilename
				+ ".csv");
				
				u1 = Uri.fromFile(file);
				Intent sendIntent=new Intent(Intent.EXTRA_STREAM,u1);
        attachFiles[0] = String.valueOf(sendIntent);
//        attachFiles[1] = "e:/Test/Music.mp3";
//        attachFiles[2] = "e:/Test/Video.mp4";
 
        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
   //         Toast.makeText(EmailAttachmentSender.this,"Email sent..", 3000).show();
            System.out.println("Email sent.");
        } catch (Exception ex) {
     //   	Toast.makeText(EmailAttachmentSender.this,"Could not send email..", 3000).show();
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
       
    }

}
