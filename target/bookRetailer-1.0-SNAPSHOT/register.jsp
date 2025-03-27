<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký</title>
        <link rel="stylesheet" href="css/styleLogin.css">
    </head>
    <body>
        <div class="container">
            <h2>Đăng ký</h2>
            <form action="register" method="POST">
                <p style="color: red;">${ERROR}</p>
                <p id="successMessage" style="color: green;">${SUCCESS}</p> <!-- Thêm thông báo thành công -->

                <label for="id">Id</label>
                <input type="text" id="id" name="id" required>

                <label for="username">Tên đăng nhập</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Mật khẩu</label>
                <input type="password" id="password" name="password" required>

                <label for="confirm_password">Xác thực mật khẩu</label>
                <input type="password" id="confirm_password" name="confirm_password" required>

                <button type="submit" class="submit-btn">Đăng ký</button>
                <a href="login.jsp" class="back-btn">Quay về Đăng nhập</a>
            </form>
        </div>

        <script>
            // Ẩn thông báo thành công sau 3 giây
            setTimeout(function () {
                var successMessage = document.getElementById("successMessage");
                if (successMessage) {
                    successMessage.style.display = "none";
                }
            }, 3000);
        </script>
    </body>
</html>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f0f8ff;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    .container {
        width: 100%;
        max-width: 400px;
        background-color: #ffffff;
        border: 2px solid #92c4ec;
        border-radius: 8px;
        padding: 20px;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
    }

    h2 {
        color: #92c4ec;
        text-align: center;
    }

    form {
        display: flex;
        flex-direction: column;
    }

    label {
        font-weight: bold;
        margin-top: 10px;
        color: #555;
    }

    input[type="text"],
    input[type="password"] {
        padding: 10px;
        margin-top: 5px;
        border: 1px solid #92c4ec;
        border-radius: 4px;
        outline: none;
    }

    input[type="text"]:focus,
    input[type="password"]:focus {
        border-color: #659ec7;
    }

    .submit-btn {
        margin-top: 20px;
        padding: 12px;
        border: none;
        background-color: #92c4ec;
        color: white;
        font-weight: bold;
        border-radius: 4px;
        cursor: pointer;
    }

    .submit-btn:hover {
        background-color: #659ec7;
    }

    .back-btn {
        display: block;
        text-align: center;
        margin-top: 15px;
        color: #92c4ec;
        text-decoration: none;
        font-weight: bold;
    }

    .back-btn:hover {
        color: #659ec7;
    }

</style>
