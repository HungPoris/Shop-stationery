<%@page import="model.Account"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Đơn Mua - Shopee Clone</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/style_1.css"/>
    </head>
    <body>
        <c:import url="header.jsp"/>2
        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <h5><i class="fas fa-user-circle"></i> ${sessionScope.LOGIN_USER.getUsername()}</h5>
                <hr>
                <ul class="list-unstyled">
                    <li><a href="listOrder" class="text-decoration-none text-danger fw-bold"><i class="fas fa-shopping-cart"></i>Đơn hàng</a></li>
                </ul>
            </div>
            <!-- Main Content -->
            <div class="content container">
                <form action="ShipperOrder" method="POST">
                    <h4 class="mt-4">Quản lý đơn hàng</h4>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <button type="submit" class="nav-link">Chờ lấy hàng</button>
                        </li>
                        <li class="nav-item">
                            <button type="submit" name="DeliveryStatus" value="3" class="nav-link">Đang vận chuyển</button>
                        </li>
                        <li class="nav-item">
                            <button type="submit" name="DeliveryStatus" value="4" class="nav-link">Đã giao</button>
                        </li>
                        <li class="nav-item">
                            <button type="submit" name="DeliveryStatus" value="5" class="nav-link">Hoàn thành</button>
                        </li>
                    </ul>
                </form>
                <!-- Kiểm tra nếu có đơn hàng -->
                <c:choose>
                    <c:when test="${not empty orderList}">
                        <table class="table mt-4 custom-table">
                            <c:forEach var="order" items="${orderList}" varStatus="status">
                                <tbody class="${status.index % 2 == 0 ? '' : 'table-secondary'}"> 
                                    <tr>
                                        <th colspan="2">Tên khách hàng</th>
                                        <th colspan="5">Địa chỉ</th>
                                        <th>Phương thức thanh toán</th>
                                        <th>Action</th>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <c:forEach var="customer" items="${customerList}">
                                                <c:choose>
                                                    <c:when test="${customer.customerID == order.customerID}">
                                                        ${customer.firstName} ${customer.lastName}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td colspan="5">
                                            <c:forEach var="address" items="${addressList}">
                                                <c:choose>
                                                    <c:when test="${address.addressID == order.orderAddressID}">
                                                        ${address.addressDetail}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:forEach var="paymentmethod" items="${paymentMethodList}">
                                                <c:choose>
                                                    <c:when test="${paymentmethod.paymentMethodID == order.paymentMethodID}">
                                                        ${paymentmethod.methodName}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${order.deliveryStatusID != 4 && order.deliveryStatusID != 5}">
                                                    <form action="ShipperOrder" method="POST">
                                                        <input type="hidden" name="orderID" value="${order.orderID}">
                                                        <button type="submit" name="DeliveryUpdate" value="${order.deliveryStatusID + 1}" class="btn btn-primary">
                                                            ${order.deliveryStatusID == 2 ? "Nhận đơn" : "Giao hàng"}
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="submit" class="btn btn-secondary" disabled>${order.deliveryStatusID == 4 ? "Đã giao" : "Hoàn thành"}</button>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    <!-- Danh sách CartItem -->
                                    <tr colspan="9">
                                        <td colspan="7"></td>
                                        <td><strong>Tổng:</strong></td>
                                        <td class="price-column">${order.orderTotalAmount}</td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="order-empty mt-4 text-center">
                            <img src="https://cdn-icons-png.flaticon.com/512/6791/6791087.png" width="100">
                            <h5>Hiện nay đang không có đơn hàng</h5>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <c:import url="footer.jsp"/>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".price-column").forEach(priceElement => {
                    let price = parseInt(priceElement.innerText.replace(/\D/g, '')) || 0;
                    priceElement.innerText = price.toLocaleString("vi-VN") + " đ";
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
