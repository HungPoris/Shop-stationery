<%-- 
    Document   : newjsp
    Created on : Oct 27, 2024, 4:09:12 PM
    Author     : anhkc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">

    <head>
        <title>ICHIBOOKS | Cart</title>
        <!-- Required meta tags -->
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

        <!-- Bootstrap CSS v5.2.1 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css\styleCart.css">

    </head>

    <body>
        <c:import url="header.jsp"/>
        <c:choose>
            <c:when test="${cart == null || cart.itemList == null || cart.itemList.isEmpty()}">
                <div class="cart-container">
                    <h2>GIỎ HÀNG</h2>
                    <div>
                        <p style="text-align:center">Không có sản phẩm trong giỏ hàng</p>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="cart-container">
                    <h2>Giỏ hàng</h2>
                    <c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert">
        ${error}
    </div>
</c:if>
                    <form action="cart" method="post" id="cart-form">
                        <input type="hidden" name="action" id="cart-action" value="" />
                        <table class="table table-striped">
                            <tr class="table-dark" style="border: none;">
                                <th style="white-space: nowrap"></th>
                                <th style="white-space: nowrap">Sản Phẩm</th>
                                <th style="white-space: nowrap">Tiêu đề</th>
                                <th style="white-space: nowrap">Giá</th>
                                <th style="width: 1%; white-space: nowrap">Số Lượng</th>
                                <th style="white-space: nowrap">Phiên bản</th>
                                <th style="white-space: nowrap"></th>
                            </tr>
                            <c:forEach var="item" items="${sessionScope.cart.itemList}">
                                <tr>
                                    <td>
                                        <input type="checkbox" class="item-checkbox" value="${item.book.bookID}" data-price="${item.finalPrice}" data-quantity="${item.quantity}"/>
                                        <input type="hidden" name="id" value="${item.book.bookID}"/>
                                    </td>
                                    <td>
                                        <a style="width:100%" href="detail?pid=${item.book.bookID}" title="${item.book.bookTitle}">
                                            <img src="${item.book.bookCover}" alt="cover ${item.book.bookTitle}" class="img-fluid" style="max-width: 80px;">
                                        </a>
                                    </td>
                                    <td>
                                        <a href="detail?pid=${item.book.bookID}" title="${item.book.bookTitle}" class="text-reset text-decoration-none">
                                            <strong>${item.book.bookTitle}</strong><br/>
                                        </a>
                                    </td>
                                    <td id="final-price" class="price-column">
                                        ${item.finalPrice}
                                    </td>
                                    <td>
                                        <input id="quantity" style="width: 100%;border: none;background-color: transparent;outline: none" 
                                               type="number" name="quantity" value="${item.quantity}" min="1" max="${item.book.bookQuantity}" required />
                                    </td>
                                    <td>
                                        <p>${item.book.bookVersion}</p>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger" onclick="deleteItem('${item.book.bookID}')">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="5" class="text-right"><strong>Tổng: </strong></td>
                                <td colspan="2" id="totalAmount">${sessionScope.cart.totalAmount} VNÐ</td>
                            </tr>

                        </table>
                        <div class="row">
                            <div class="col text-start">
                                <div class="pay-custom clear-fix">
                                    <button type="button" class="btn btn-primary" onclick="submitForm('update')">Cập nhật</button>
                                    <button type="button" class="btn btn-success" onclick="submitForm('pay')">Thanh toán</button>
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </c:otherwise>
        </c:choose>
        <c:import url="footer.jsp"/>
        <script>
            function submitForm(action) {
                document.getElementById("cart-action").value = action;
                document.getElementById("cart-form").submit();
            }

            function deleteItem(bookID) {
                //Tạo Form
                let form = document.createElement("form");
                form.method = "post";
                form.action = "cart";

                let actionInput = document.createElement("input");
                //Tạo parameter action
                actionInput.name = "action";
                actionInput.value = "delete";
                let idInput = document.createElement("input");
                //Tạo parameter id
                idInput.name = "id";
                idInput.value = bookID;
                form.appendChild(actionInput);
                form.appendChild(idInput);
                document.body.appendChild(form);
                form.submit();
            }

            document.addEventListener("DOMContentLoaded", function () {
                document.querySelectorAll(".price-column").forEach(priceElement => {
                    let price = parseInt(priceElement.innerText.replace(/\D/g, '')) || 0;
                    priceElement.innerText = price.toLocaleString("vi-VN") + " VNÐ";
                });
            });
            document.addEventListener("DOMContentLoaded", function () {
                function updateTotal() {
                    let total = 0;
                    document.querySelectorAll('.item-checkbox:checked').forEach(checkbox => {
                        let price = parseInt(checkbox.getAttribute('data-price')) || 0;
                        let quantity = parseInt(checkbox.getAttribute('data-quantity')) || 1;
                        total += price * quantity;
                    });

                    document.getElementById('totalAmount').innerText = total.toLocaleString("vi-VN") + " VNÐ";
                }

                //Gán sự kiện thay đổi cho checkbox
                document.querySelectorAll('.item-checkbox').forEach(checkbox => {
                    checkbox.addEventListener('change', updateTotal);
                });

                //Cập nhật tổng tiền khi trang load xong
                updateTotal();
            });


            document.querySelectorAll('.item-checkbox').forEach(function (checkbox) {
                checkbox.addEventListener('change', updateTotal);
            });
            function submitForm(action) {
                if (action === "pay") {
                    let selectedItems = document.querySelectorAll('.item-checkbox:checked');
                    if (selectedItems.length === 0) {
                        alert("Vui lòng chọn ít nhất một sản phẩm để thanh toán!");
                        return;
                    }

                    //Tạo form mới
                    let form = document.createElement("form");
                    form.method = "post";
                    form.action = "cart";
                    let actionInput = document.createElement("input");
                    //Tạo parameter action
                    actionInput.name = "action";
                    actionInput.value = "pay";
                    form.appendChild(actionInput);
                    //Thêm các item đã chọn vào form
                    selectedItems.forEach(checkbox => {
                        let bookID = checkbox.value;
                        let quantity = checkbox.closest('tr').querySelector('input[name="quantity"]').value;
                        let idInput = document.createElement("input");
                        idInput.name = "id";
                        idInput.value = bookID;
                        form.appendChild(idInput);
                        let quantityInput = document.createElement("input");
                        quantityInput.name = "quantity_" + bookID;
                        quantityInput.value = quantity;
                        form.appendChild(quantityInput);
                    });
                    document.body.appendChild(form);
                    form.submit();
                } else {
                    document.getElementById("cart-action").value = action;
                    document.getElementById("cart-form").submit();
                }
            }
        </script>
        <!-- Bootstrap JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
        
   <script     document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('input[name="quantity"]').forEach(input => {
        input.addEventListener("input", function () {
            let minValue = 1;
            let maxValue = parseInt(this.getAttribute("max"), 10) || 1;
            let value = parseInt(this.value, 10);

            if (isNaN(value) || value < minValue) {
                this.value = minValue;
            } else if (value > maxValue) {
                this.value = maxValue;
            }
        });
    });
});
</script>
<script>
    document.getElementById("cart-form").addEventListener("submit", function (event) {
        let isValid = true;
        let quantityInputs = document.querySelectorAll("input[name='quantity']");
        
        quantityInputs.forEach(input => {
            let quantity = input.value.trim();
            if (quantity === "" || isNaN(quantity) || parseInt(quantity) <= 0) {
                alert("Số lượng không được để trống và phải là số nguyên dương!");
                isValid = false;
            }
        });

        if (!isValid) {
            event.preventDefault();
        }
    });
</script>
    </body>
</html>
