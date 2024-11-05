/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import java.util.Properties;
import model.Message;





/**
 *
 * @author admin
 */

public class EmailSender {

//   public static void sendEmail(String recipient, String otp) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        String yourEmail = "namhhhe182163@fpt.edu.vn";
//        String yourPassword = "jcyzpcsicejwzwpu";
//
//        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(yourEmail, yourPassword);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(yourEmail));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
//            message.setSubject("OTP Verification Code");
//            message.setText("Your OTP code is: " + otp);
//
//            Transport.send(message);
//            System.out.println("OTP email sent successfully!");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
}
