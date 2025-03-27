package controller.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@WebServlet(name = "SendEmailServlet", urlPatterns = {"/SendEmailServlet"})
public class SendEmailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Chuyển hướng GET về POST để xử lý
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        if (email != null && !email.isEmpty() && name != null && !name.isEmpty()) {
            boolean success = sendWelcomeEmail(email, name);
            if (success) {
                System.out.println("✅ Email sent successfully to " + email);
                // Chuyển hướng về trang login sau khi gửi email thành công
                response.sendRedirect("login.jsp");
            } else {
                System.err.println("❌ Failed to send email to " + email);
                // Có thể chuyển hướng đến trang báo lỗi hoặc vẫn về trang login với thông báo lỗi
                response.sendRedirect("login.jsp?error=email_failed");
            }
        } else {
            System.err.println("⚠️ Invalid email or name.");
            // Chuyển hướng với thông báo lỗi
            response.sendRedirect("login.jsp?error=invalid_input");
        }
    }

    private boolean sendWelcomeEmail(String recipientEmail, String name) throws UnsupportedEncodingException {
        final String senderEmail = "fptotpr1@gmail.com";
        final String senderPassword = "wxdqbyntvujufwjv";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.charset", "UTF-8");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Shop Stationery"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(MimeUtility.encodeText("Chào mừng bạn đến với Shop Stationery!", "UTF-8", "B"));

            String loginLink = "http://localhost:8080/login.jsp"; // Thay bằng link đăng nhập thật
            String emailContent = "<html><body>"
                    + "<h2>Chào mừng bạn đến với <b>Shop Stationery!</b></h2>"
                    + "<p>Kính gửi <b>" + name + "</b>,</p>"
                    + "<p>Chúng tôi vô cùng vui mừng chào đón bạn đã trở thành thành viên của Shop Stationery - địa chỉ mua sắm văn phòng phẩm uy tín nhất! Cảm ơn bạn đã tin tưởng và lựa chọn dịch vụ của chúng tôi.</p>"
                    + "<h3>Tài khoản của bạn đã sẵn sàng</h3>"
                    + "<p>Tài khoản của bạn đã được kích hoạt thành công và bạn có thể bắt đầu mua sắm ngay bây giờ.</p>"
                    + "<ul>"
                    + "<li><b>Đăng nhập</b>: Sử dụng email và mật khẩu bạn đã đăng ký</li>"
                    + "<li><b>Cá nhân hóa hồ sơ</b>: Cập nhật thông tin liên hệ và địa chỉ giao hàng</li>"
                    + "<li><b>Khám phá sản phẩm</b>: Trải nghiệm bộ sưu tập văn phòng phẩm đa dạng của chúng tôi</li>"
                    + "</ul>"
                    + "<p><a href='" + loginLink + "' style='display: inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;'>Đăng nhập ngay</a></p>"
                    + "<h3>Những bước tiếp theo</h3>"
                    + "<ol>"
                    + "<li>Khám phá danh mục sản phẩm đa dạng: bút viết, sổ tay, dụng cụ học tập và văn phòng</li>"
                    + "<li>Theo dõi trang <b>Khuyến mãi</b> để không bỏ lỡ các ưu đãi hấp dẫn</li>"
                    + "<li>Kết nối với Shop Stationery trên các mạng xã hội để cập nhật sản phẩm mới</li>"
                    + "</ol>"
                    + "<h3>Hỗ trợ</h3>"
                    + "<p>Nếu bạn có bất kỳ câu hỏi hoặc cần hỗ trợ, đừng ngần ngại liên hệ với chúng tôi qua:</p>"
                    + "<ul>"
                    + "<li><b>Email:</b> <a href='mailto:hotro@shopstationery.com'>hotro@shopstationery.com</a></li>"
                    + "<li><b>Điện thoại:</b> 0338 858 270</li>"
                    + "<li><b>Chat trực tuyến:</b> Có sẵn trên trang web, từ 8:00 - 22:00 hàng ngày</li>"
                    + "</ul>"
                    + "<p>Một lần nữa, chào mừng bạn đã gia nhập cộng đồng Shop Stationery! Chúng tôi rất mong được đồng hành cùng bạn trên hành trình làm việc và học tập hiệu quả.</p>"
                    + "<p><b>Trân trọng,</b><br>Ban Quản Trị<br><b>Shop Stationery</b></p>"
                    + "</body></html>";

            message.setContent(emailContent, "text/html; charset=UTF-8");
            Transport.send(message);

            System.out.println("Welcome email sent successfully to " + recipientEmail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
