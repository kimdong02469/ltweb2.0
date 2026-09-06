package kimdong.vn.utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils {
	public static void sendEmail(String toEmail, String subject, String messageText) {
		final String username = "tkd2k6@gmail.com"; // Thay bằng email của bạn
		final String password = "jgqvuqqfkbrycthn"; // Thay bằng App Password vừa tạo

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);
			System.out.println("Gửi email thành công!");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}