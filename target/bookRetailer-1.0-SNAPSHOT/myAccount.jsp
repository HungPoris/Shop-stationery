<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Tài Khoản Của Tôi</title>
        <link rel="stylesheet" href="css/styleLogin.css">
    </head>
    <body>
        <div class="container">
            <h2>Tài Khoản Của Tôi</h2>
            <div class="account-details">
                <p><strong>Id: </strong> ${sessionScope['LOGIN_USER'].accountId}</p>
                <p><strong>Tên đăng nhập:</strong> ${sessionScope['LOGIN_USER'].username}</p>
                <p><strong>Ngày đăng ký:</strong> ${sessionScope['LOGIN_USER'].registrationDate}</p>
                <a href="verify_code.jsp" class="action-link">Quên mật khẩu</a>
                <a href="reset_password.jsp" class="action-link">Thay đổi mật khẩu</a>
                <form action="/Logout" method="POST">
                    <button type="submit" class="logout-btn">Đăng xuất</button>
                </form>
            </div>
        </div>
    </body>
</html>
<style>
    /* Định dạng tổng thể cho container của trang */
    .container {
        max-width: 600px;
        margin: 0 auto;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    h2 {
        color: #333;
        font-size: 24px;
        margin-bottom: 20px;
    }

    .account-details {
        line-height: 1.6;
        font-size: 16px;
        color: #555;
    }

    .action-link {
        display: inline-block;
        margin-top: 10px;
        padding: 8px 15px;
        color: #007bff;
        text-decoration: none;
        font-weight: bold;
    }

    .action-link:hover {
        color: #0056b3;
        text-decoration: underline;
    }

    /* Định dạng cho nút đăng xuất */
    .logout-btn {
        display: inline-block;
        background-color: #d9534f;
        color: #fff;
        font-size: 16px;
        font-weight: bold;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 15px;
        transition: background-color 0.3s;
    }

    .logout-btn:hover {
        background-color: #c9302c;
    }

</style>