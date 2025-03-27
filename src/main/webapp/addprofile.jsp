<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm Hồ Sơ Khách Hàng</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Thêm Hồ Sơ Khách Hàng</h2>

            <form action="AddProfileController" method="post">
                <div class="form-group">
                    <label for="customerID">Mã khách hàng:</label>
                    <input type="text" id="customerID" name="customerID" class="form-control" value="${sessionScope.LOGIN_USER.getAccountId()}" readonly>
                </div>

                <div class="form-group">
                    <label for="firstName">Họ:</label>
                    <input type="text" id="firstName" name="firstName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Tên:</label>
                    <input type="text" id="lastName" name="lastName" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="birthDate">Ngày sinh:</label>
                    <input type="date" id="birthDate" name="birthDate" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <textarea id="address" name="address" class="form-control" required></textarea>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Thêm Hồ Sơ</button>
                    <a href="profile.jsp?customerID=${param.customerID}" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </body>
</html>
