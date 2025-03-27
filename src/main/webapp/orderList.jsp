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
        <c:import url="header.jsp"/>
        <div class="d-flex">
            <!-- Sidebar -->
            <div class="sidebar">
                <h5><i class="fas fa-user-circle"></i> ${sessionScope.LOGIN_USER.getUsername()}</h5>
                <hr>
                <ul class="list-unstyled">
                    <li><a href="listOrder" class="text-decoration-none text-danger fw-bold"><i class="fas fa-shopping-cart"></i> Đơn Mua</a></li>
                </ul>
            </div>
            <!-- Main Content -->
            <div class="content container">
                <form action="listOrder" method="POST">
                    <h4 class="mt-4">Đơn Mua</h4>
                    <ul class="nav nav-tabs">
                        <li class="nav-item">
                            <button type="submit" class="nav-link">Tất cả</button>
                        </li>
                        <li class="nav-item">
                            <button type="submit" name="DeliveryStatus" value="2" class="nav-link">Chờ lấy hàng</button>
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
                        <li class="nav-item">
                            <button type="submit" name="DeliveryStatus" value="1" class="nav-link">Đã hủy</button>
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
                                        <th colspan="2">Ngày đặt hàng</th>
                                        <th colspan="7">Địa chỉ</th>
                                    </tr>
                                    <tr>
                                        <td colspan="2">${order.orderDate}</td>
                                        <td colspan="7">
                                            <c:forEach var="address" items="${addressList}">
                                                <c:choose>
                                                    <c:when test="${address.addressID == order.orderAddressID}">
                                                        ${address.addressDetail}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <!-- Danh sách CartItem -->
                                    <tr>
                                        <td colspan="9">
                                            <div>
                                                <table class="table custom-table">
                                                    <thead>
                                                        <tr>
                                                            <th style="white-space: nowrap">Sản phẩm</th>
                                                            <th style="white-space: nowrap">Tên</th>
                                                            <th style="white-space: nowrap">Số lượng</th>
                                                            <th style="white-space: nowrap">Giá</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="item" items="${order.itemList}">
                                                            <tr>
                                                                <td>
                                                                    <a style="width:100%" href="detail?pid=${item.book.bookID}" title="${item.book.bookTitle}">
                                                                        <img src="${item.book.bookCover}" alt="cover ${item.book.bookTitle}" class="img-fluid" style="max-width: 80px;">
                                                                    </a>
                                                                </td>
                                                                <td>
                                                                    <a href="detail?pid=${item.book.bookID}" title="${item.book.bookTitle}" class="text-reset text-decoration-none">
                                                                        <strong>${item.book.bookTitle}</strong><br/>
                                                                    </a>
                                                                    <p>${item.book.bookVersion}</p>
                                                                </td>
                                                                <td>${item.quantity}</td>
                                                                <td class="price-column">${item.finalPrice}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <td colspan="2"></td>
                                                            <td><strong>Tổng:</strong></td>
                                                            <td class="price-column">${order.orderTotalAmount}</td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2">
                                                                Vui lòng chỉ nhấn "Đã nhận được hàng" khi đơn hàng đã được giao đến bạn và sản phẩm nhận được không có vấn đề nào.
                                                            </td>
                                                            <td colspan="2">
                                                                <form action="listOrder" method="POST">
                                                                    <input type="hidden" name="orderID" value="${order.orderID}">

                                                                    <button type="submit" name="DeliveryUpdate" value="${order.deliveryStatusID + 1}" 
                                                                            class="btn ${order.deliveryStatusID == 4 ? 'btn-primary' : 'btn-secondary'}"
                                                                            ${order.deliveryStatusID == 4 ? '' : 'disabled'}>
                                                                        Đã nhận
                                                                    </button>

                                                                    <button type="submit" name="DeliveryUpdate" value="1" 
                                                                            class="btn ${order.deliveryStatusID == 2 ? 'btn-primary' : 'btn-secondary'}"
                                                                            ${order.deliveryStatusID == 2 ? '' : 'disabled'}>
                                                                        Hủy đơn
                                                                    </button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="order-empty mt-4 text-center">
                            <img src="https://cdn-icons-png.flaticon.com/512/6791/6791087.png" width="100">
                            <h5>Lịch sử mua hàng trống</h5>
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
