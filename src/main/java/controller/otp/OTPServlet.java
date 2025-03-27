/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.otp;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import service.EmailOTPService;

public class OTPServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Map<String, String> otpStorage = new HashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");

        if (action == null || email == null || email.isEmpty()) {
            response.getWriter().write("Lỗi: Dữ liệu không hợp lệ!");
            return;
        }

        if (action.equals("send")) {
            // Tạo mã OTP ngẫu nhiên
            String otp = String.format("%06d", new Random().nextInt(999999));
            otpStorage.put(email, otp);

            // Gửi email chứa OTP
            boolean emailSent = EmailOTPService.sendOTP(email, otp);
            if (emailSent) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("Lỗi khi gửi OTP, vui lòng thử lại!");
            }
        } else if (action.equals("verify")) {
            String enteredOTP = request.getParameter("verificationCode");
            String storedOTP = otpStorage.get(email);

            if (storedOTP != null && storedOTP.equals(enteredOTP)) {
                otpStorage.remove(email);

                request.getSession().setAttribute("resetEmail", email);

                response.getWriter().write("verified");
            } else {
                response.getWriter().write("Mã OTP không hợp lệ hoặc đã hết hạn!");
            }
        }
    }
}
