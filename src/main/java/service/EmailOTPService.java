package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import utils.DBConnect;

public class EmailOTPService {

    public static boolean isEmailRegistered(String email) {
        String query = "SELECT COUNT(*) FROM Account WHERE email = ?";
        try ( Connection conn = DBConnect.getConnection();  PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try ( ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendOTP(String recipient, String otp) throws UnsupportedEncodingException {
        if (!isEmailRegistered(recipient)) {
            System.out.println("❌ Email không tồn tại trong hệ thống!");
            return false;
        }

        final String senderEmail = "fptotpr1@gmail.com";
        final String senderPassword = "wxdqbyntvujufwjv";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            // ✅ Mã hóa tiêu đề để tránh lỗi tiếng Việt
            message.setSubject(MimeUtility.encodeText("Mã OTP đặt lại mật khẩu", "UTF-8", "B"));

            // Nội dung email có HTML format
            String emailContent = "<html><body style='font-family:Arial,sans-serif;'>"
                    + "<h2 style='color:#007bff;'>Yêu cầu đặt lại mật khẩu</h2>"
                    + "<p>Xin chào,</p>"
                    + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Dưới đây là mã OTP của bạn:</p>"
                    + "<h3 style='color:#ff0000;'><b>" + otp + "</b></h3>"
                    + "<p>Vui lòng nhập mã OTP này để tiếp tục đặt lại mật khẩu.</p>"
                    + "<p>Nếu bạn không yêu cầu, vui lòng bỏ qua email này.</p>"
                    + "<br><p style='font-size:12px;color:gray;'>Email được gửi tự động, vui lòng không trả lời.</p>"
                    + "</body></html>";

            message.setContent(emailContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✅ OTP đã gửi thành công đến " + recipient);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
