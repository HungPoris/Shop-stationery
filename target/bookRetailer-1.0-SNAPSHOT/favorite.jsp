<%-- 
    Document   : favorite
    Created on : Mar 26, 2025, 2:48:08 PM
    Author     : Tran Viet Minh - CE182526
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Favorite Item</title>
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
                    <h2>Danh Sách Quan Tâm</h2>
                    <table class="table table-striped">
                        <tr class="table-dark" style="border: none;">
                            <th style="white-space: nowrap">Sản Phẩm</th>
                            <th style="white-space: nowrap">Tiêu đề</th>
                            <th style="white-space: nowrap">Giá</th>
                            <th style="white-space: nowrap">Phiên bản</th>
                            <th style="white-space: nowrap">Action</th>
                        </tr>
                        <c:forEach var="book" items="${bookList}">
                            <tr>
                                <td>
                                    <a style="width:100%" href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                        <img src="${book.bookCover}" alt="cover ${book.bookTitle}" class="img-fluid" style="max-width: 80px;">
                                    </a>
                                </td>
                                <td>
                                    <a href="detail?pid=${book.bookID}" title="${book.bookTitle}" class="text-reset text-decoration-none">
                                        <strong>${book.bookTitle}</strong><br/>
                                    </a>
                                </td>
                                <td>
                                    <p><fmt:formatNumber value="${book.bookPrice}" type="currency" currencySymbol="₫" groupingUsed="true"/></p>
                                </td>
                                <td>
                                    <p>${book.bookVersion}</p>
                                </td>
                                <td>
                                    <form action="Favorite" method="POST" >
                                        <input type="hidden" name="bookID" value="${book.bookID}"/>
                                        <button type="submit" class="btn btn-danger"name="action" value="delete">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
        <c:import url="footer.jsp"/>
        <!-- Bootstrap JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
    </body>
</html>