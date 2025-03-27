<%-- 
    Document   : address
    Created on : Oct 13, 2024, 11:01:47 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style><%@include file="css/address.css"%></style>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta charshet="utf-8"/>
        <script src="js/address.js"></script>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <h1>Thông Tin Tài Khoản</h1>
        <table>
            <thead>
                <tr>
                    <th style="width: 25%;">Địa chỉ</th>
                    <th style="width: 15%;">Số điện thoại</th>
                    <th style="width: 10%;">Năm sinh</th>
                    <th style="width: 15%;">Email</th>
                    <th style="width: 10%;">Địa chỉ mặc định</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="address" items="${customer.getAddressList()}">
                    <tr>
                        <td>${address.addressDetail}</td>
                        <td>${customer.phoneNumber}</td>
                        <td>${customer.birthDate}</td>
                        <td>${customer.email}</td>
                        <td>${address.defaultAddress ? "Yes" : "No"}</td>
                        <td>
                            <span><a class="button" href="EditServlet?id=${address.addressID}">Edit</a></span>
                            <span><a class="button" href="DeleteServlet?id=${address.addressID}"onclick="return confirm('Bạn có chắc chắn muốn xóa không?');">Delete</a></span>
                        </td>
                    </tr>
                </c:forEach>  
            </tbody> 

        </table>

        <button class="button" onclick="showAddressForm()">Thêm địa chỉ mới</button>

        <div id="addressForm" style="display:none;">
            <h2>Thêm Địa Chỉ</h2>
            <form action="AddressServlet" method="post">
                <div class="form-group">
                    <label for="firstName">Họ:</label>
                    <input type="text" class="form-control" id="firstName" name="firstName">
                </div>
                <div class="form-group">
                    <label for="lastName">Tên:</label>
                    <input type="text" class="form-control" id="lastName" name="lastName">
                </div>
                <div class="form-group">
                    <label for="addressDetail">Địa chỉ:</label>
                    <input type="text" class="form-control" id="addressDetail" name="addressDetail">
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại:</label>
                    <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                </div>
                <div class="form-group">
                    <label for="birthDate">Ngày sinh:</label>
                    <input type="date" class="form-control" id="birthDate" name="birthDate">
                </div>
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="defaultAddress" name="defaultAddress">
                    <label class="form-check-label" for="defaultAddress">Địa chỉ mặc định</label>
                </div>
                <button type="submit" class="btn btn-success">Lưu</button>
                <button type="button" class="btn btn-secondary" onclick="hideAddressForm()">Hủy</button>
            </form>
        </div>
        <c:import url="footer.jsp"/>  
    </body>
</html>
