<%-- 
    Document   : login.jsp
    Created on : Feb 5, 2025, 11:14:45 AM
    Author     : HungTQ
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Login Page</title>
        <link rel="stylesheet" href="https://cdn.lineicons.com/4.0/lineicons.css" />
        <link rel="stylesheet" href="Layout/Css/style.css" />
        <script src="https://accounts.google.com/gsi/client" async defer></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <!-- Loading effect -->


        <!-- Background video -->
        <video id="background-video" autoplay loop muted>
            <source src="<%=request.getContextPath()%>/images/system_login/964df1d1e8c1bc21f634c7e268a2748e.mp4" type="video/mp4" />
        </video>
        <div id="mute-btn" onclick="toggleMute()" class="lni lni-volume-mute"></div>

        <!-- Main container -->
        <div class="container" id="container">
            <!-- Register form -->
            <div class="form-container register-container">
                <form action="/RegisterServlet" method="post">
                    <h1>Đăng kí</h1>
                    <input type="text" name="name" placeholder="Tên" required />
                    <input type="email" name="email" placeholder="Email" required />
                    <input type="password" name="password" placeholder="Mật khẩu" required />
                    <button type="submit">Đăng kí</button>
                    <span>hoặc sử dụng tài khoản của bạn</span>
                    <a href="GoogleAuthServlet" style="text-align: center; display: block;">
                        <img src="https://developers.google.com/identity/images/g-logo.png" 
                             alt="Signup with Google" 
                             style="width: 30px; cursor: pointer; display: block; margin: 0 auto;">
                        <span style="display: block; margin-top: 5px; font-size: 14px; color: #555;">
                            Đăng kí bằng Google
                        </span>
                    </a>

                </form>
            </div>

            <!-- Login form -->
            <div class="form-container login-container">
                <form action="<%= request.getContextPath()%>/login" method="post">
                    <h1>Đăng nhập</h1>
                    <input type="text" name="usernameOrEmail" placeholder="Email or Username" required />
                    <input type="password" name="password" placeholder="Password" required />
                    <div class="content">
                        <div class="checkbox">
                            <input type="checkbox" name="remember" id="checkbox" />
                            <label for="checkbox">Remember me</label>
                        </div>
                        <div class="pass-link">
                            <a href="forgot_password.jsp">Quên mật khẩu?</a>
                        </div>
                    </div>
                    <button type="submit">Đăng nhập</button>
                    <span>hoặc sử dụng tài khoản của bạn</span>
                    <a href="GoogleAuthServlet" style="text-align: center; display: block;">
                        <img src="https://developers.google.com/identity/images/g-logo.png" 
                             alt="Signup with Google" 
                             style="width: 30px; cursor: pointer; display: block; margin: 0 auto;">
                        <span style="display: block; margin-top: 5px; font-size: 14px; color: #555;">
                            Đăng nhập bằng Google
                        </span>
                    </a>

                </form>
            </div>




            <!-- Overlay container -->
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1 class="title">Hello <br />friends</h1>
                        <p>Nếu bạn có tài khoản, hãy đăng nhập tại đây.</p>
                        <button class="ghost" id="login">Login</button>
                    </div>

                    <div class="overlay-panel overlay-right">
                        <h1 class="title">Start your <br />journey now</h1>
                        <p>Nếu bạn chưa có tài khoản, hãy tham gia cùng chúng tôi và bắt đầu khám phá.</p>
                        <button class="ghost" id="register">Đăng kí</button>
                    </div>
                </div>
            </div>
        </div>




        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const registerButton = document.getElementById("register");
                const loginButton = document.getElementById("login");
                const container = document.getElementById("container");

                // Xử lý khi bấm nút đăng nhập
                loginButton.addEventListener("click", () => {
                    container.classList.remove("right-panel-active");
                    localStorage.setItem("formState", "login"); // Lưu trạng thái
                });

                // Xử lý khi bấm nút đăng ký
                registerButton.addEventListener("click", () => {
                    container.classList.add("right-panel-active");
                    localStorage.setItem("formState", "register"); // Lưu trạng thái
                });



                // Kiểm tra trạng thái lưu trong localStorage
                if (localStorage.getItem("formState") === "register") {
                    container.classList.add("right-panel-active");
                }
            });


            // Video mute toggle
            function toggleMute() {
                const video = document.getElementById("background-video");
                const muteBtn = document.getElementById("mute-btn");
                video.muted = !video.muted;
                muteBtn.className = video.muted ? "lni lni-volume-mute" : "lni lni-volume";
            }


            document.addEventListener("DOMContentLoaded", function () {
                google.accounts.id.initialize({
                    client_id: "<%= request.getAttribute("googleClientId")%>",
                    callback: handleCredentialResponse
                });

                // Render nút đăng nhập Google
                google.accounts.id.renderButton(
                        document.getElementById("google-signin-btn"),
                        {theme: "outline", size: "large"}
                );

                // Render nút đăng ký Google
                google.accounts.id.renderButton(
                        document.getElementById("google-signup-btn"),
                        {theme: "outline", size: "large"}
                );
            });

            function handleCredentialResponse(response) {
                fetch('GoogleAuthServlet', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: new URLSearchParams({credential: response.credential})
                })
                        .then(res => res.text())
                        .then(result => {
                            if (result === 'success') {
                                Swal.fire({
                                    title: 'Đăng nhập thành công!',
                                    icon: 'success',
                                    toast: true,
                                    position: 'top-end',
                                    showConfirmButton: false,
                                    timer: 1500
                                }).then(() => {
                                    window.location.href = 'homepage.jsp';
                                });
                            } else {
                                throw new Error('Đăng nhập thất bại!');
                            }
                        })
                        .catch(error => {
                            Swal.fire({
                                title: 'Lỗi!',
                                text: error.message,
                                icon: 'error',
                                toast: true,
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 3000
                            });
                        });
            }
            
            document.addEventListener("DOMContentLoaded", function () {
                const params = new URLSearchParams(window.location.search);

                if (params.has("message")) {
                    let messageType = params.get("message");
                    let title = "";
                    let icon = "";
                    let background = "";
                    let color = "";

                    switch (messageType) {
                        case "exist":
                            title = "Tài khoản đã tồn tại!";
                            icon = "error";
                            background = "#ff204e";
                            color = "#ffffff";
                            break;
                        case "empty":
                            title = "Vui lòng điền đầy đủ thông tin!";
                            icon = "warning";
                            background = "#ffae00";
                            color = "#ffffff";
                            break;
                        case "success":
                            title = "Tạo tài khoản thành công!";
                            icon = "success";
                            background = "#00ff9c";
                            color = "#ffffff";


                            let email = params.get("email");
                            let name = params.get("name");
                            if (email && name) {
                                setTimeout(() => {
                                    window.location.href = 'SendEmailServlet?email=' + email + '&name=' + name;
                                }, 4000);
                            }
                            break;
                        case "error":
                            title = "Có lỗi xảy ra, vui lòng thử lại!";
                            icon = "error";
                            background = "#ff204e";
                            color = "#ffffff";
                            break;
                    }

                    if (title && icon) {
                        Swal.fire({
                            title: title,
                            icon: icon,
                            toast: true,
                            position: "top-end",
                            showConfirmButton: false,
                            timer: 5000,
                            timerProgressBar: true,
                            background: background,
                            color: color,
                            didOpen: (toast) => {
                                toast.style.opacity = 1;
                            }
                        });
                    }
                }
            });

            document.addEventListener("DOMContentLoaded", function () {
                // Lấy thông báo lỗi từ request attribute
                var errorType = "${ERROR_TYPE}";
                var errorMessage = "${ERROR_MESSAGE}";

                if (errorType && errorMessage) {
                    var title = "";
                    var icon = "";
                    var background = "";
                    var color = "";

                    // Phân loại thông báo và gán các giá trị màu sắc và icon phù hợp
                    switch (errorType) {
                        case "yellow":

                            icon = "warning";
                            background = "#ffae00";  // Màu nền vàng
                            color = "#ffffff";       // Màu chữ trắng
                            break;
                        case "red":

                            icon = "error";
                            background = "#ff204e";  // Màu nền đỏ
                            color = "#ffffff";       // Màu chữ trắng
                            break;
                        default:
                            title = "Thông báo";
                            icon = "info";
                            background = "#007bff";  // Màu nền xanh dương
                            color = "#ffffff";       // Màu chữ trắng
                            break;
                    }

                    // Hiển thị thông báo qua SweetAlert2
                    Swal.fire({
                        title: title, // Tiêu đề hiển thị
                        text: errorMessage, // Nội dung thông báo
                        icon: icon,
                        toast: true,
                        position: "top-end",
                        showConfirmButton: false,
                        timer: 5000,
                        timerProgressBar: true,
                        background: background, // Màu nền
                        color: color, // Màu chữ
                        didOpen: (toast) => {
                            toast.style.opacity = 1;
                        }
                    });
                }
            });
        </script>
    </body>
</html>
