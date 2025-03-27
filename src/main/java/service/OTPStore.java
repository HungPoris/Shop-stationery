/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author HungTQ
 */
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OTPStore {

    private static final Map<String, OTPData> otpDataMap = new ConcurrentHashMap<>();
    private static final int OTP_EXPIRY_TIME = 5 * 60 * 1000; // 5 phút
    private static final int MAX_ATTEMPTS = 10; // Giới hạn gửi OTP

    public static void storeOTP(String email, String otp) {
        OTPData data = otpDataMap.get(email);

        if (data == null) {
            data = new OTPData(otp, System.currentTimeMillis(), 1);
        } else {
            if (data.getAttempts() >= MAX_ATTEMPTS) {
                throw new RuntimeException("Bạn đã yêu cầu OTP quá 10 lần! Vui lòng thử lại sau.");
            }
            data.setOtp(otp);
            data.setTimestamp(System.currentTimeMillis());
            data.incrementAttempts();
        }
        otpDataMap.put(email, data);
    }

    public static boolean validateOTP(String email, String otp) {
        OTPData data = otpDataMap.get(email);
        if (data == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - data.getTimestamp() > OTP_EXPIRY_TIME) {
            otpDataMap.remove(email); // OTP hết hạn, xóa khỏi bộ nhớ
            return false;
        }

        if (data.getOtp().equals(otp)) {
            otpDataMap.remove(email); // Xác thực thành công, xóa OTP
            return true;
        }
        return false;
    }
}
