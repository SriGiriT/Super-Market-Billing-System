package com.billingsystem.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.billingsystem.Model.CartItem;
import com.billingsystem.Model.Invoice;
import com.billingsystem.service.VerificationService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class EmailUtility {

	private static String host = "smtp.gmail.com"; 
    private static String from = "srigiriboopathy@gmail.com";
    private static String username = "srigiriboopathy@gmail.com"; 
    private static String password = System.getenv("SMTP_SECRET_KEY"); 
	
    public static String[] sendInvoiceEmail(String recipientEmail, String subject, Invoice invoice, Double providedOffer) throws Exception {
		

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
		
        String pdfPath = generateInvoicePDF(invoice, providedOffer);
        String invoiceBody = "";
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(invoice.getCustomer().getEmail()));
            message.setSubject("Invoice for your Purchase");

            BodyPart messageBodyPart = new MimeBodyPart();
            invoiceBody = generateInvoiceEmailBody(invoice, providedOffer);
            messageBodyPart.setText(invoiceBody);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.attachFile(pdfPath);
            multipart.addBodyPart(attachPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully with PDF attached.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new String[] {invoiceBody, pdfPath};
    }
    
    // Email Validation
    public static void sendVerificationEmail(String recipientEmail, String token, long user_id) {        
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); 
        properties.put("mail.smtp.auth", "true");
        VerificationService verificationService = new VerificationService();
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "Super Market"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Email Verification");
            
            String verificationLink = "http://172.24.123.28:9000/SuperMarketBilling/verify?token=" + token+"&user_id="+user_id;
            message.setContent("Please click the following link to verify your email: <a href='" + verificationLink + "'>Verify Email</a>", "text/html");
            
            Transport.send(message);
            verificationService.saveVerificationCode(user_id, token);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
	 private static String generateInvoiceEmailBody(Invoice invoice, double providedOffer) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("Dear ").append(invoice.getCustomer().getUserName()).append(",\n\n");
	        sb.append("Thank you for your purchase! Here is your invoice:\n\n");

	        sb.append("Invoice Details:\n");
	        sb.append("--------------------------------------------------\n");
	        for (CartItem item : invoice.getCart().getItems()) {
	            sb.append(item.getProduct().getName())
	                .append(" - Quantity: ").append(item.getQuantity())
	                .append(" - Price: ").append(item.getProduct().getPrice())
	                .append("\n");
	        }
	        sb.append("--------------------------------------------------\n");
	        if(providedOffer>0.0) {
	        	sb.append("Total Amount: Rs. ").append(invoice.getTotalAmount()+providedOffer).append("\n");
	        	sb.append("Offer Applied: Rs. ").append(providedOffer).append("\n");
	        	sb.append("Paid Amount: Rs. ").append(invoice.getTotalAmount()).append("\n\n");
	        }else {
	        	sb.append("Total Amount: Rs. ").append(invoice.getTotalAmount()).append("\n\n");
	        }
	        sb.append("Date: ").append( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(invoice.getDate()).toString()).append("\n");
	        sb.append("Thank you for shopping with us!\n");
	        return sb.toString();
	    }
	
	
	
	 private static String generateInvoicePDF(Invoice invoice, double providedOffer) throws DocumentException, FileNotFoundException {
	        Document document = new Document();
	        String pdfPath = "C:\\Users\\srigiri-20969\\prog\\git_clone\\Super-Market-Billing-System\\src\\main\\resources\\files\\Invoice_"+invoice.getId()+".pdf";
	        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));

	        document.open();
	        document.add(new Paragraph("Invoice ID: " + invoice.getId()));
	        document.add(new Paragraph("Customer: " + invoice.getCustomer().getUserName()));
	        document.add(new Paragraph("Date: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(invoice.getDate()).toString()));
	        document.add(new Paragraph("Items: "));
	        
	        for (CartItem item : invoice.getCart().getItems()) {
	            document.add(new Paragraph(item.getProduct().getName() + " - " + item.getQuantity() + " x " + item.getProduct().getPrice()));
	        }
	        
	        if(providedOffer>0.0) {
	        	document.add(new Paragraph("Total Amount: Rs. " + (invoice.getTotalAmount()+providedOffer)));
	        	document.add(new Paragraph("Offer Applied: Rs. "+providedOffer));
	        	document.add(new Paragraph("Paid Amount: Rs. "+invoice.getTotalAmount()));
	        }else {
	        	document.add(new Paragraph("Total Amount: Rs. " + invoice.getTotalAmount()));
	        }
	        document.close();

	        return pdfPath;
	    }
	
    public static void sendEmail(String recipientEmail, String subject, String messageText) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); 

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
            System.out.println("Invoice email sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MessagingException("Failed to send email.");
        }
    }
}
