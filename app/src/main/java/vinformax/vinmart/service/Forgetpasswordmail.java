package vinformax.vinmart.service;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class Forgetpasswordmail {  
	String result="Sending failed";
 public static void main(String args) {
 
 }
 public String Forgetpass(String toadd, String maarppass){
	  String host="smtp.gmail.com";
	  final String user="androidt763@gmail.com";//change accordingly
	  final String password="android321";//change accordingly
	    
	  String to=toadd;//change accordingly
	  
	 //  Get the session object  
	   Properties props = new Properties();
	   props.put("mail.smtp.host",host);  
	   props.put("mail.smtp.auth", "true");  
	   props.put("mail.smtp.starttls.enable", "true");
	   Session session = Session.getDefaultInstance(props,  
	    new javax.mail.Authenticator() {  
	      protected PasswordAuthentication getPasswordAuthentication() {  
	    return new PasswordAuthentication(user,password);  
	      }  
	    });  
	  
	   //Compose the message  
	    try {  
	     MimeMessage message = new MimeMessage(session);  
	     message.setFrom(new InternetAddress(user));  
	     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	     message.setSubject("VinMart");  
	     message.setText(maarppass);  	       

	     result="sent successfully..";
	     
	        	 
		    	Transport.send(message);
		    	result="success";
		    	
	    }
	    catch(Exception tt)
	    {
	    	result="failed";
	    }
	    
	     
	  
//	  try{    	 
//	    	Transport.send(message);
//	    	 
//	    	} catch( javax.mail.SendFailedException  mx) { 
//	    	 
//	    	    StringBuilder errorSB = null;
//	    	 
//	    	    if(mx.getInvalidAddresses() != null) {
//	    	        errorSB = new StringBuilder();
//	    	        for(Address email: mx.getInvalidAddresses()) {
//	    	            errorSB.append(email.toString());
//	    	            errorSB.append(", ");
//	    	        }
//	    	        System.out.println("Invalid Address Found: "+ errorSB);
//	    	    }
//	    	 
//	    	    if(mx.getValidSentAddresses() != null) {
//	    	        errorSB = new StringBuilder();
//	    	        for(Address email: mx.getValidSentAddresses()) {
//	    	            errorSB.append(email.toString());
//	    	            errorSB.append(", ");
//	    	        }
//	    	        System.out.println("Email sent to valid address: "+ errorSB);
//	    	    }
//	    	 
//	    	    if(mx.getValidUnsentAddresses() != null) {
//	    	        errorSB = new StringBuilder();
//	    	        for(Address email: mx.getValidUnsentAddresses()) {
//	    	            errorSB.append(email.toString());
//	    	            errorSB.append(", ");
//	    	        }
//	    	        System.out.println("Email not sent to valid address: "+ errorSB);
//	    	    }
//	    	 
//	    	} catch(javax.mail.MessagingException mx) {
//	    	 
//	    	    System.out.println(mx.getMessage());
//	    	 
//	    	} catch (Exception ex) {
//	    	 
//	    	    System.out.println(ex.getMessage());
//	    	 
//	    	}
 
	   
//	     } catch (MessagingException e) {
//	    	 e.printStackTrace();} 
	return result; 
 }
}  



//
//	import java.util.Properties; 
//    import javax.mail.*;  
//    import javax.mail.Message;
//    import javax.mail.PasswordAuthentication;
//    import javax.mail.Session;
//    import javax.mail.Transport;
//    import javax.mail.internet.InternetAddress;
//    import javax.mail.internet.MimeMessage;
//    public class Forgetpasswordmail {  
//     public static void main(String[] args) {  
//        
//     }
//        public String mailfun(String a,String b)
//         {
//            String result="failed"; 
//         
//     String to=a;//change accordingly  
//      
//      //Get the session object  
//      Properties props = new Properties();  
//      props.put("mail.smtp.host", "smtp.gmail.com");  
//      props.put("mail.smtp.socketFactory.port", "465");  
//      props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
//      props.put("mail.smtp.auth", "true");  
//      props.put("mail.smtp.port", "465"); 
//      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
//          protected PasswordAuthentication getPasswordAuthentication() {  
//          return new PasswordAuthentication("ramesh.vjm@gmail.com","ramesh!3969");//change accordingly  
//           }  
//          });  
//      //compose message  
//      try {  
//       MimeMessage message = new MimeMessage(session);  
//       message.setFrom(new InternetAddress("ramesh.vjm@gmail.com"));//change accordingly  
//       message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
//       message.setSubject("Maarp Supplies Password");  
//       message.setText("Your Login Password is : "+b);  
//         
//       //send message  
//       Transport.send(message);  
//       System.out.println("message sent successfully");
//       result="success";
//       
//      } catch (MessagingException e) {throw new RuntimeException(e);}  
//       return result;
//     }  
//    }  






