<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Đặt Lại Mật Khẩu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
        <style>
            body {
                background: url("images/system_login/passw.gif") no-repeat center center fixed;
                background-size: cover;
                min-height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
                font-family: 'Roboto', sans-serif;
                padding: 20px;
            }

            .glass-container {
                background: rgba(255, 255, 255, 0.9);
                backdrop-filter: blur(10px);
                padding: 2rem;
                border-radius: 16px;
                box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 500px;
                position: relative;
                border: 1px solid rgba(255, 255, 255, 0.3);
            }

            h3 {
                color: #4f46e5;
                font-weight: 700;
                margin-bottom: 0.5rem;
                text-align: center;
            }

            .form-text {
                color: #64748b;
                margin-bottom: 1.5rem;
                text-align: center;
            }

            .form-group {
                position: relative;
                margin-bottom: 1.5rem;
                width: 100%;
            }

            .form-control {
                width: 100%;
                padding: 0.75rem 2.5rem 0.75rem 1rem;
                border: 2px solid #e2e8f0;
                border-radius: 8px;
                font-size: 1rem;
                background: transparent;
                transition: all 0.3s ease;
            }

            .form-control::placeholder {
                color: #a0aec0;
                opacity: 0.7;
            }

            .form-control:focus {
                border-color: #4f46e5;
                box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
                outline: none;
            }

            .form-label {
                display: block;
                margin-bottom: 0.5rem;
                color: #4a5568;
                font-weight: 500;
            }

            .toggle-password {
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
                background: none;
                border: none;
                color: #718096;
                cursor: pointer;
                display: flex;
                align-items: center;
                justify-content: center;
                width: 30px;
                height: 30px;
            }

            .toggle-password:focus {
                outline: none;
            }

            .password-requirements {
                background: transparent;
                padding: 1rem 0;
                border-radius: 8px;
                margin: 1rem 0;
                border: none;
            }

            .requirement {
                display: flex;
                align-items: center;
                padding: 0.375rem 0;
                color: #718096;
                transition: all 0.2s ease;
            }

            .requirement i {
                margin-right: 0.5rem;
                font-size: 0.875rem;
                width: 1.25rem;
                text-align: center;
            }

            .requirement.valid {
                color: #10b981;
            }

            .requirement.valid i {
                color: #10b981;
            }

            .error-message {
                display: none;
                background: #fee2e2;
                color: #ef4444;
                padding: 0.75rem;
                border-radius: 8px;
                margin-top: 0.5rem;
                font-size: 0.875rem;
                border-left: 4px solid #ef4444;
            }

            .btn-primary {
                width: 100%;
                padding: 0.75rem;
                background: #4f46e5;
                border: none;
                border-radius: 8px;
                color: white;
                font-weight: 600;
                margin: 1.5rem 0;
                transition: all 0.3s ease;
            }

            .btn-primary:hover {
                background: #4338ca;
                transform: translateY(-1px);
            }

            .back-link {
                display: block;
                text-align: center;
                color: #4f46e5;
                text-decoration: none;
                font-weight: 500;
                transition: color 0.3s ease;
            }

            .back-link:hover {
                color: #4338ca;
            }

            .back-link i {
                margin-right: 0.5rem;
            }

            @media (max-width: 576px) {
                .glass-container {
                    padding: 1.5rem;
                }
            }
        </style>
    </head>
    <body>
        <div class="glass-container">
            <h3>Đặt Lại Mật Khẩu</h3>
            <p class="form-text">Vui lòng nhập mật khẩu mới của bạn bên dưới</p>

            <form id="resetForm" action="${pageContext.request.contextPath}/ResetPasswordServlet" method="POST">
                <div class="form-group">
                    <label class="form-label" for="newPassword">Mật khẩu mới</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" 
                           placeholder="Nhập mật khẩu mới" required />
                    <button type="button" class="toggle-password">

                    </button>
                </div>

                <div class="password-requirements">
                    <div class="requirement" id="lengthReq">
                        <i class="fas fa-circle"></i>
                        Ít nhất 8 ký tự
                    </div>
                    <div class="requirement" id="upperReq">
                        <i class="fas fa-circle"></i>
                        Ít nhất 1 chữ hoa
                    </div>
                    <div class="requirement" id="numberReq">
                        <i class="fas fa-circle"></i>
                        Ít nhất 1 số
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
                    <input type="password" class="form-control" id="confirmPassword" 
                           name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required />
                    <button type="button" class="toggle-password">

                    </button>
                    <div class="error-message" id="passwordError">
                        <i class="fas fa-exclamation-circle"></i>
                        Mật khẩu không khớp
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-lock me-2"></i>Đặt Lại Mật Khẩu
                </button>

                <a href="${pageContext.request.contextPath}/login.jsp" class="back-link">
                    <i class="fas fa-arrow-left"></i>Quay lại đăng nhập
                </a>
            </form>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const newPassword = document.getElementById("newPassword");
                const confirmPassword = document.getElementById("confirmPassword");
                const passwordError = document.getElementById("passwordError");
                const resetForm = document.getElementById("resetForm");
                const toggleButtons = document.querySelectorAll(".toggle-password");

                const lengthReq = document.getElementById("lengthReq");
                const upperReq = document.getElementById("upperReq");
                const numberReq = document.getElementById("numberReq");

                toggleButtons.forEach(button => {
                    button.addEventListener("click", function (e) {
                        e.preventDefault();
                        const input = this.previousElementSibling;
                        const icon = this.querySelector('i');

                        if (input.type === "password") {
                            input.type = "text";
                            icon.classList.remove("fa-eye-slash");
                            icon.classList.add("fa-eye");
                        } else {
                            input.type = "password";
                            icon.classList.remove("fa-eye");
                            icon.classList.add("fa-eye-slash");
                        }
                    });
                });

                function checkPasswordRequirements(password) {
                    const hasLength = password.length >= 8;
                    const hasUpper = /[A-Z]/.test(password);
                    const hasNumber = /[0-9]/.test(password);

                    updateRequirement(lengthReq, hasLength);
                    updateRequirement(upperReq, hasUpper);
                    updateRequirement(numberReq, hasNumber);

                    return hasLength && hasUpper && hasNumber;
                }

                function updateRequirement(element, isValid) {
                    if (isValid) {
                        element.classList.add("valid");
                        element.querySelector("i").className = "fas fa-check-circle";
                    } else {
                        element.classList.remove("valid");
                        element.querySelector("i").className = "fas fa-circle";
                    }
                }

                function checkPasswordMatch() {
                    if (newPassword.value !== confirmPassword.value) {
                        passwordError.style.display = "block";
                        return false;
                    }
                    passwordError.style.display = "none";
                    return true;
                }

                newPassword.addEventListener("input", function () {
                    checkPasswordRequirements(this.value);
                    if (confirmPassword.value)
                        checkPasswordMatch();
                });

                confirmPassword.addEventListener("input", checkPasswordMatch);

                resetForm.addEventListener("submit", function (e) {
                    const isValid = checkPasswordRequirements(newPassword.value);
                    const isMatch = checkPasswordMatch();

                    if (!isValid || !isMatch) {
                        e.preventDefault();
                    }
                });
            });

            function validateForm() {
                const password = document.getElementById("newPassword").value;
                const confirmPassword = document.getElementById("confirmPassword").value;

                if (password.length < 8) {
                    showToast('error', 'Mật khẩu phải có ít nhất 8 ký tự!', '#d9534f');
                    return false;
                }

                if (!/[A-Z]/.test(password)) {
                    showToast('error', 'Mật khẩu phải chứa ít nhất 1 chữ hoa!', '#d9534f');
                    return false;
                }

                if (!/[0-9]/.test(password)) {
                    showToast('error', 'Mật khẩu phải chứa ít nhất 1 số!', '#d9534f');
                    return false;
                }

                if (password !== confirmPassword) {
                    showToast('warning', 'Mật khẩu không khớp!', '#f0ad4e');
                    return false;
                }

                showToast('success', 'Mật khẩu hợp lệ!', '#5cb85c');
                return true;
            }
        </script>
    </body>
</html>