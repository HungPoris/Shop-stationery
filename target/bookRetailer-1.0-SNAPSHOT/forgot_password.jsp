<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Quên Mật Khẩu | </title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Be+Vietnam+Pro:wght@400;500;600&display=swap" rel="stylesheet">

        <style>
            body {
                background: url("images/system_login/registerban.gif") center/cover no-repeat fixed;
                font-family: "Be Vietnam Pro", sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                padding: 20px;
                color: #2d3748;
            }

            .forget-password-container {
                background: rgba(255, 255, 255, 0.95);
                padding: 2.5rem;
                border-radius: 24px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
                max-width: 450px;
                width: 100%;
                text-align: center;
                position: relative;
                backdrop-filter: blur(10px);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }

            .header-icon {
                width: 90px;
                height: 90px;
                background: linear-gradient(135deg, #6366f1, #4f46e5);
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin: 0 auto 1.5rem;
                box-shadow: 0 10px 20px rgba(99, 102, 241, 0.2);
                transition: transform 0.3s ease;
            }

            .header-icon:hover {
                transform: scale(1.05);
            }

            .header-icon i {
                color: white;
                font-size: 2.2rem;
            }

            h2 {
                color: #1a202c;
                font-weight: 600;
                margin-bottom: 1rem;
                font-size: 1.75rem;
            }

            p {
                color: #4a5568;
                margin-bottom: 2rem;
                font-size: 0.95rem;
                line-height: 1.5;
            }

            .form-control {
                border-radius: 12px;
                padding: 0.75rem 1rem;
                border: 2px solid #e2e8f0;
                font-size: 0.95rem;
                transition: all 0.3s ease;
                background: rgba(255, 255, 255, 0.9);
            }

            .form-control:focus {
                border-color: #6366f1;
                box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
            }

            .form-control::placeholder {
                color: #a0aec0;
            }

            .btn {
                padding: 0.75rem 1.5rem;
                font-weight: 500;
                border-radius: 12px;
                transition: all 0.3s ease;
            }

            .btn-primary {
                background: linear-gradient(135deg, #6366f1, #4f46e5);
                border: none;
                box-shadow: 0 4px 12px rgba(99, 102, 241, 0.2);
            }

            .btn-primary:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 15px rgba(99, 102, 241, 0.3);
            }

            .btn-secondary {
                background: #f8fafc;
                border: 2px solid #e2e8f0;
                color: #4a5568;
            }

            .btn-secondary:hover {
                background: #f1f5f9;
                border-color: #cbd5e1;
                color: #1a202c;
            }

            .btn:disabled {
                opacity: 0.7;
                cursor: not-allowed;
                transform: none;
            }
        </style>
    </head>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <body>
        <div class="forget-password-container">
            <div class="header-icon">
                <i class="fas fa-lock"></i>
            </div>
            <h2>Quên Mật Khẩu?</h2>
            <p>Vui lòng nhập email đã đăng ký tài khoản. Chúng tôi sẽ gửi mã xác minh để đặt lại mật khẩu của bạn.</p>

            <form id="verifyForm" action="OTPServlet" method="post">
                <div class="mb-3">
                    <input type="email" 
                           class="form-control" 
                           id="email" 
                           name="email" 
                           placeholder="Nhập địa chỉ email" 
                           required />
                </div>

                <div class="mb-3">
                    <button type="button" 
                            class="btn btn-secondary w-100" 
                            id="sendCode">
                        <i class="fas fa-paper-plane me-1"></i> Gửi mã OTP
                    </button>
                </div>

                <div class="mb-3">
                    <input type="text" 
                           class="form-control" 
                           id="verificationCode" 
                           name="verificationCode" 
                           placeholder="Nhập mã xác minh" 
                           required 
                           disabled />
                </div>

                <button type="submit" 
                        class="btn btn-primary w-100" 
                        id="verifyCode" 
                        disabled>
                    <i class="fas fa-check me-2"></i> Xác Nhận
                </button>
            </form>
        </div>

        <script>
            document.getElementById("sendCode").addEventListener("click", function () {
                let email = document.getElementById("email").value;
                if (!email) {
                    Swal.fire({
                        icon: "warning",
                        title: "Vui lòng nhập email!",
                        toast: true,
                        position: "top-end",
                        showConfirmButton: false,
                        timer: 3000,
                        background: "#FF204E",
                        color: "#FFFFFF"
                    });
                    return;
                }

                // Vô hiệu hóa nút gửi OTP để tránh spam
                let sendButton = document.getElementById("sendCode");
                sendButton.disabled = true;
                sendButton.innerText = "Đang gửi...";
                fetch("OTPServlet", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "action=send&email=" + encodeURIComponent(email)
                })
                        .then(response => response.text())
                        .then(data => {
                            if (data === "success") {
                                Swal.fire({
                                    icon: "success",
                                    title: "Mã OTP đã được gửi!",
                                    text: "Vui lòng kiểm tra email.",
                                    toast: true,
                                    position: "top-end",
                                    showConfirmButton: false,
                                    timer: 3000,
                                    background: "#00ff9c",
                                    color: "#ffffff"

                                });
                                document.getElementById("verificationCode").disabled = false;
                                document.getElementById("verifyCode").disabled = false;
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "Lỗi!",
                                    text: data,
                                    toast: true,
                                    position: "top-end",
                                    showConfirmButton: false,
                                    timer: 3000,
                                    background: "#ff204e",
                                    color: "#ffffff"
                                });
                            }
                        })
                        .catch(error => {
                            console.error("Lỗi:", error);
                            Swal.fire({
                                icon: "error",
                                title: "Có lỗi xảy ra!",
                                text: "Vui lòng thử lại.",
                                toast: true,
                                position: "top-end",
                                showConfirmButton: false,
                                timer: 3000,
                                background: "#ff204e",
                                color: "#ffffff"
                            });
                        })
                        .finally(() => {
                            sendButton.disabled = false;
                            sendButton.innerText = "Gửi OTP";
                        });
            });
            document.getElementById("verifyCode").addEventListener("click", function () {
                let email = document.getElementById("email").value;
                let otp = document.getElementById("verificationCode").value;
                if (!email) {
                    Swal.fire({
                        icon: "warning",
                        title: "Vui lòng nhập email!",
                        toast: true,
                        position: "top-end",
                        showConfirmButton: false,
                        timer: 3000,
                        background: "#ff204e",
                        color: "#ffffff"
                    });
                    return;
                }
                if (!otp) {
                    Swal.fire({
                        icon: "warning",
                        title: "Vui lòng nhập mã OTP!",
                        toast: true,
                        position: "top-end",
                        showConfirmButton: false,
                        timer: 3000,
                        background: "#ff204e",
                        color: "#ffffff"
                    });
                    return;
                }

                let verifyButton = document.getElementById("verifyCode");
                verifyButton.disabled = true;
                verifyButton.innerText = "Đang kiểm tra...";
                fetch("OTPServlet", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "action=verify&email=" + encodeURIComponent(email) + "&verificationCode=" + encodeURIComponent(otp)
                })
                        .then(response => response.text())
                        .then(data => {
                            if (data === "verified") {
                                Swal.fire({
                                    icon: "success",
                                    title: "Xác thực OTP thành công!",
                                    text: "Bạn có thể đặt lại mật khẩu.",
                                    toast: true,
                                    position: "top-end",
                                    timer: 2000,
                                    showConfirmButton: false,
                                    background: "#00ff9c",
                                    color: "#ffffff"
                                });
                                setTimeout(() => {
                                    window.location.href = "reset_password.jsp";
                                }, 2000);
                            } else {
                                Swal.fire({
                                    icon: "error",
                                    title: "Lỗi xác thực!",
                                    text: data,
                                    toast: true,
                                    position: "top-end",
                                    showConfirmButton: false,
                                    timer: 3000,
                                    background: "#ff204e",
                                    color: "#ffffff"
                                });
                            }
                        })
                        .catch(error => {
                            console.error("Lỗi:", error);
                            Swal.fire({
                                icon: "error",
                                title: "Có lỗi xảy ra!",
                                text: "Vui lòng thử lại.",
                                toast: true,
                                position: "top-end",
                                showConfirmButton: false,
                                timer: 3000,
                                background: "#ff204e",
                                color: "#ffffff"
                            });
                        })
                        .finally(() => {
                            verifyButton.disabled = false;
                            verifyButton.innerText = "Xác thực OTP";
                        });
            });

        </script>
    </body>
</html>
