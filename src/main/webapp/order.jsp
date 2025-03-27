<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh Toán</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            :root {
                --shopee-orange: #ee4d2d;
                --shopee-light-orange: #feefeb;
                --shopee-gray: #f8f8f8;
                --shopee-dark-gray: #757575;
                --shopee-black: #222222;
                --shopee-border: #e8e8e8;
            }

            body {
                background-color: #f5f5f5;
                font-family: Helvetica, Arial, sans-serif;
                color: var(--shopee-black);
            }

            .shopee-container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px 15px;
            }

            .shopee-header {
                display: flex;
                align-items: center;
                padding: 10px 0;
                margin-bottom: 20px;
                border-bottom: 1px solid var(--shopee-border);
            }

            .shopee-logo {
                color: var(--shopee-orange);
                font-size: 28px;
                font-weight: 700;
                text-decoration: none;
                margin-right: 15px;
            }

            .shopee-title {
                font-size: 18px;
                color: var(--shopee-black);
                padding-left: 15px;
                border-left: 1px solid var(--shopee-border);
            }

            .shopee-card {
                background-color: white;
                border-radius: 3px;
                box-shadow: 0 1px 2px 0 rgba(0,0,0,.13);
                margin-bottom: 12px;
                padding: 20px;
            }

            .shopee-card-title {
                display: flex;
                align-items: center;
                font-size: 18px;
                font-weight: 500;
                color: var(--shopee-black);
                margin-bottom: 15px;
            }

            .shopee-card-icon {
                color: var(--shopee-orange);
                font-size: 22px;
                margin-right: 10px;
            }

            .shopee-address-info {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
            }

            .shopee-address-label {
                background-color: var(--shopee-light-orange);
                color: var(--shopee-orange);
                padding: 2px 5px;
                border-radius: 2px;
                font-size: 12px;
                margin-right: 15px;
            }

            .shopee-address-select {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--shopee-border);
                border-radius: 2px;
                background-color: white;
                color: var(--shopee-black);
            }

            .shopee-address-select:focus {
                border-color: var(--shopee-orange);
                outline: none;
            }

            .shopee-item-table {
                width: 100%;
                margin-bottom: 15px;
            }

            .shopee-item-table th {
                background-color: var(--shopee-gray);
                padding: 16px;
                text-align: center;
                font-weight: 400;
                color: var(--shopee-dark-gray);
            }

            .shopee-item-table td {
                padding: 16px;
                text-align: center;
                vertical-align: middle;
                border-bottom: 1px solid var(--shopee-border);
            }

            .shopee-item-info {
                display: flex;
                align-items: center;
                text-align: left;
            }

            .shopee-item-img {
                width: 80px;
                height: 80px;
                object-fit: contain;
                border: 1px solid var(--shopee-border);
                margin-right: 10px;
            }

            .shopee-item-name {
                font-size: 14px;
                color: var(--shopee-black);
                text-decoration: none;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
            }

            .shopee-item-price {
                font-size: 14px;
                color: var(--shopee-orange);
            }

            .shopee-item-qty {
                color: var(--shopee-black);
            }

            .shopee-payment-options {
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .shopee-payment-option {
                border: 1px solid var(--shopee-border);
                border-radius: 2px;
                padding: 10px 15px;
                display: flex;
                align-items: center;
                cursor: pointer;
                transition: all 0.2s;
            }

            .shopee-payment-option.selected {
                border-color: var(--shopee-orange);
            }

            .shopee-payment-radio {
                position: relative;
                width: 18px;
                height: 18px;
                border: 1px solid #ddd;
                border-radius: 50%;
                margin-right: 15px;
            }

            .shopee-payment-option.selected .shopee-payment-radio::after {
                content: '';
                position: absolute;
                width: 10px;
                height: 10px;
                background-color: var(--shopee-orange);
                border-radius: 50%;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }

            .shopee-payment-icon {
                font-size: 20px;
                margin-right: 10px;
                color: var(--shopee-dark-gray);
            }

            .shopee-payment-title {
                font-size: 14px;
                font-weight: 500;
            }

            .shopee-footer {
                background-color: white;
                padding: 10px 20px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 -1px 4px 0 rgba(0,0,0,.13);
                position: sticky;
                bottom: 0;
            }

            .shopee-total {
                display: flex;
                align-items: center;
            }

            .shopee-total-text {
                font-size: 14px;
                color: var(--shopee-dark-gray);
                margin-right: 15px;
            }

            .shopee-total-amount {
                font-size: 24px;
                color: var(--shopee-orange);
                font-weight: 500;
            }

            .shopee-order-btn {
                background-color: var(--shopee-orange);
                color: white;
                border: none;
                padding: 12px 40px;
                font-size: 16px;
                font-weight: 500;
                border-radius: 2px;
                cursor: pointer;
                transition: background-color 0.2s;
            }

            .shopee-order-btn:hover {
                background-color: #d73211;
            }

            .shopee-section-divider {
                height: 12px;
                background-color: #f5f5f5;
                margin: 0 -20px 20px -20px;
            }

            .shopee-note-textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid var(--shopee-border);
                border-radius: 2px;
                min-height: 80px;
                resize: vertical;
            }

            .shopee-note-textarea:focus {
                border-color: var(--shopee-orange);
                outline: none;
            }

            @media (max-width: 768px) {
                .shopee-item-table th:nth-child(3) {
                    display: none;
                }

                .shopee-item-table td:nth-child(3) {
                    display: none;
                }

                .shopee-item-img {
                    width: 60px;
                    height: 60px;
                }

                .shopee-total-amount {
                    font-size: 18px;
                }

                .shopee-order-btn {
                    padding: 10px 20px;
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="shopee-container">
            <form method="POST" onsubmit="return validatePaymentMethod()">
                <!-- Địa chỉ giao hàng -->
                <div class="shopee-card">
                    <div class="shopee-card-title">
                        <i class="shopee-card-icon fas fa-map-marker-alt"></i>
                        Địa Chỉ Nhận Hàng
                    </div>

                    <div class="shopee-address-info">
                        <div class="shopee-address-label">Mặc định</div>
                        <div>
                            <div><strong>${sessionScope.customerInfo.firstName} ${sessionScope.customerInfo.lastName}</strong> | ${sessionScope.customerInfo.phoneNumber}</div>
                        </div>
                    </div>

                    <select id="address" name="address" class="shopee-address-select">
                        <c:forEach var="address" items="${sessionScope.customerInfo.addressList}">
                            <option value="${address.addressID}" ${address.defaultAddress ? 'selected' : ''}>
                                ${address.addressDetail}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Sản phẩm -->
                <div class="shopee-card">
                    <div class="shopee-card-title">
                        <i class="shopee-card-icon fas fa-shopping-cart"></i>
                        Sản Phẩm
                    </div>

                    <table class="shopee-item-table">
                        <thead>
                            <tr>
                                <th style="width: 50%; text-align: left;">Sản Phẩm</th>
                                <th style="width: 20%;">Đơn Giá</th>
                                <th style="width: 15%;">Số Lượng</th>
                                <th style="width: 15%;">Thành Tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${sessionScope.newCart.itemList}">

                                <tr data-price="${item.finalPrice}" data-quantity="${item.quantity}">
                                    <td style="text-align: left;">
                                        <div class="shopee-item-info">
                                            <img src="${item.book.bookCover}" alt="${item.book.bookTitle}" class="shopee-item-img">
                                            <a href="detail?pid=${item.book.bookID}" class="shopee-item-name">${item.book.bookTitle}</a>
                                        </div>
                                    </td>
                                    <td class="price-column shopee-item-price">
                                        <fmt:formatNumber value="${item.finalPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </td>
                                    <td class="shopee-item-price item-total">
                                        <fmt:formatNumber value="${item.book.bookPrice * item.quantity}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </td>
                                    <td class="shopee-item-qty">${item.quantity}</td>
                                    <td class="shopee-item-price item-total">
                                        <fmt:formatNumber value="${item.finalPrice * item.quantity}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- Phương thức thanh toán -->
                <div class="shopee-card">
                    <div class="shopee-card-title">
                        <i class="shopee-card-icon fas fa-wallet"></i>
                        Phương Thức Thanh Toán
                    </div>

                    <div class="shopee-payment-option" onclick="selectPayment('PAY002', this)">
                        <i class="shopee-payment-icon fas fa-money-bill-wave"></i>
                        <div class="shopee-payment-title">Thanh toán khi nhận hàng</div>
                    </div>
                </div>
                <c:set var="originalTotal" value="0"/>
                <c:forEach var="item" items="${sessionScope.newCart.itemList}">
                    <c:set var="originalTotal" value="${originalTotal + (item.book.bookPrice * item.quantity)}"/>
                </c:forEach>

                <!-- Chi tiết thanh toán -->
                <div class="shopee-card">
                    <div class="shopee-card-title">
                        <i class="shopee-card-icon fas fa-file-invoice"></i>
                        Chi Tiết Thanh Toán
                    </div>

                    <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                        <div style="color: var(--shopee-dark-gray);">Tổng tiền hàng</div>
                        <div id="subtotal">
                            <fmt:formatNumber value="${originalTotal}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                        </div>
                    </div>
                    <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                        <div style="color: var(--shopee-dark-gray);">Giảm giá</div>
                        <div>
                            <fmt:formatNumber value="${originalTotal - sessionScope.newCart.totalAmount}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                        </div>
                    </div>

                    <div class="shopee-section-divider"></div>

                    <div style="display: flex; justify-content: space-between; margin-bottom: 10px; font-weight: 500;">
                        <div>Tổng thanh toán</div>
                        <div id="totalAmount" style="color: var(--shopee-orange); font-size: 20px;">
                            <fmt:formatNumber value="${sessionScope.newCart.totalAmount}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                        </div>
                    </div>
                </div>

                <!-- Footer cố định -->
                <div class="shopee-footer">
                    <div class="shopee-total">
                        <div class="shopee-total-text">Tổng thanh toán:</div>
                        <div class="shopee-total-amount" id="footerTotal">
                            <fmt:formatNumber value="${sessionScope.newCart.totalAmount}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                        </div>
                    </div>

                    <button type="submit" class="shopee-order-btn">
                        Đặt hàng
                    </button>
                </div>
            </form>
        </div>
        <c:import url="footer.jsp"/>

        <script>
            document.addEventListener("DOMContentLoaded", function () {

                // Tính tổng tiền
                let total = 0;
                document.querySelectorAll('tr[data-price]').forEach(row => {
                    let price = parseInt(row.getAttribute('data-price')) || 0;
                    let quantity = parseInt(row.getAttribute('data-quantity')) || 1;
                    total += price * quantity;
                });

                document.getElementById('totalAmount').innerText = formatCurrency(total);
                document.getElementById('footerTotal').innerText = formatCurrency(total);
                document.getElementById('subtotal').innerText = formatCurrency(total);
            });

            // Chọn phương thức thanh toán
            function selectPayment(value, element) {
                // Bỏ chọn tất cả
                document.querySelectorAll('.shopee-payment-option').forEach(option => {
                    option.classList.remove('selected');
                });

                // Chọn option hiện tại
                element.classList.add('selected');

                // Chọn radio button
                document.querySelector(`input[value="${value}"]`).checked = true;
            }
        </script>
    </body>
</html>