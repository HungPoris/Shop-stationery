<%-- 
    Document   : searchResults
    Created on : Nov 3, 2024, 7:49:55 PM
    Author     : conkg
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://kit.fontawesome.com/bfab6e6450.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css\style.css">
        <link rel="stylesheet" href="css\sanpham.css">
        <link rel="stylesheet" href="css\seach.css">
        <title>JSP Page</title>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="container">
            <!-- Search Bar -->


            <!-- Display Search Keyword -->
            <div class="row">
                <div class="col-md-12">
                    <div class="search-keyword">
                        Results for: "<strong>${requestScope.query}</strong>"
                    </div>
                </div>
            </div>

            <!-- No Results Message -->
            <c:if test="${empty requestScope.books}">
                <div class="row">
                    <div class="col-md-12">
                        <div class="alert alert-warning" role="alert">
                            Không tìm thấy kết quả nào cho từ khóa "<strong>${requestScope.query}</strong>"
                        </div>
                    </div>
                </div>
            </c:if>

            <!-- Search Results -->
            <div class="row">
                <c:forEach var="book" items="${requestScope.books}">
                    <div class="product-container col-md-3">
                        <div class="cover-container">
                            <div class="discount">
                                <span class="discount-amount">${book.bookDiscount}%</span>
                            </div>
                            <div class="cover">
                                <a href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                    <img src="${book.bookCover}" alt="Hình bìa ${book.bookTitle}" />
                                </a>
                                <c:if test="${book.bookQuantity == 0}">
                                    <div class="out-of-stock">Hết hàng</div>
                                </c:if>
                            </div>
                        </div>
                        <div class="info-container">
                            <h3 class="product-title">
                                <a href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                    ${book.bookTitle}
                                </a>
                            </h3>
                            <div class="product-price">
                                <p class="product-price-discounted">
                                    ${book.bookPrice * (100 - book.bookDiscount) / 100} VNÐ
                                </p>
                                <p class="product-price-original">
                                    <del>${book.bookPrice} VNÐ</del>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
        <c:import url="footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kQtW33rZJAHjgefvhyyzcGFwErFC5pkCddS3z2kYhDaUVD4vELt8q3Rx9LxjxRPV" crossorigin="anonymous"></script>
    </body>
</html>