<%@taglib prefix="cus" uri="/WEB-INF/tlds/firstCustomTagLib.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ICHIBOOKS | Home</title>
        <script src="https://kit.fontawesome.com/bfab6e6450.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css/styleHeaderFooter.css"/>
        <link rel="stylesheet" href="css/styleHomepage.css"/>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="homepage-policy">
            <div class="container">
                <div class="row">
                    <div class="col-6 col-lg-3 eachPolicy">
                        <div class="eachPolicyContent">
                            <div class="policy-icon">
                                <i class="fa-solid fa-truck-fast"></i>
                            </div>
                            <div class="policy-detail">
                                ƯU ĐÃI
                                <br>
                                VẬN CHUYỂN
                            </div>
                        </div>
                    </div>
                    <div class="col-6 col-lg-3 eachPolicy">
                        <div class="eachPolicyContent">
                            <div class="policy-icon">
                                <i class="fa-solid fa-book-open"></i>
                            </div>
                            <div class="policy-detail">
                                ĐA DẠNG
                                <br>
                                THỂ LOẠI
                            </div>
                        </div>
                    </div>
                    <div class="col-6 col-lg-3 eachPolicy">
                        <div class="eachPolicyContent">
                            <div class="policy-icon">
                                <i class="fa-solid fa-sack-dollar"></i>
                            </div>
                            <div class="policy-detail">
                                KHUYẾN MÃI
                                <br>
                                HẤP DẪN
                            </div>
                        </div>
                    </div>
                    <div class="col-6 col-lg-3 eachPolicy">
                        <div class="eachPolicyContent">
                            <div class="policy-icon">
                                <i class="fa-solid fa-phone-volume"></i>
                            </div>
                            <div class="policy-detail">
                                HOT LINE:
                                <br>
                                0384 417 960
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="homepage-fast-view">
            <div class="fast-nav-container">
                <ul class="nav nav-fill">
                    <li class="nav-item">
                        <a class="nav-link" href="AddReportServlet">Phản hồi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#featuredBooks1" onclick="hideCountdown(); displayFeaturedBooks(this.href); return false;"> Sản Phẩm Mới</a>
                    </li>
                    <li class="nav-item" id="flash-sale-nav">
                        <a class="nav-link" href="#featuredBooks2" onclick="showCountdown(); displayFeaturedBooks(this.href); return false;">FLASH SALE</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#featuredBooks3" onclick="hideCountdown(); displayFeaturedBooks(this.href); return false;">BÁN CHẠY NHẤT</a>
                    </li>
                    <li class="nav-item" id="hot-deal-nav">
                        <a class="nav-link" href="#featuredBooks4" onclick="hideCountdown(); displayFeaturedBooks(this.href); return false;">HOT DEALS</a>
                    </li>
                </ul>
            </div>

            <div class="container" id="mainView">
                <div class="time-counter">
                    <div class="time-box">
                        <p class="hour" id="hourID">00</p>
                    </div>
                    <div class="time-box">
                        <p class="minute" id="minuteID">00</p>
                    </div>
                    <div class="time-box">
                        <p class="second" id="secondID">00</p>
                    </div>
                </div>

                <div class="row" id="featuredBooks1">
                    <c:forEach var="book" items="${requestScope.newBooks}">
                        <div class="product-container col-6 col-md-4">
                            <div class="cover-container">
                                <c:if test="${book.bookDiscount ne 0.0}">
                                    <div class="discount">
                                        <span class="discount-amount">${book.bookDiscount}%</span>
                                    </div>
                                </c:if>
                                <div class="cover">
                                    <a style="width:100%" href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                        <img class="cover-image" src="${book.bookCover}"
                                             alt="Hình bìa ${book.bookTitle}" />
                                    </a>
                                    <c:if test="${book.bookQuantity == 0}">
                                        <div class="out-of-stock">
                                            Hết hàng
                                        </div>
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
                                    <p class="product-price-discounted discounted-price">
                                        <fmt:formatNumber value="${book.bookPrice * (100 - book.bookDiscount) / 100}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>

                                    <p class="product-price-original original-price">
                                        <fmt:formatNumber value="${book.bookPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div class="row" id="featuredBooks2">
                    <c:forEach var="book" items="${requestScope.flashBooks}">
                        <div class="product-container col-6 col-md-4">
                            <div class="cover-container">
                                <div class="discount">
                                    <span class="discount-amount">${book.bookFlashSale}%</span>
                                </div>
                                <div class="cover">
                                    <a style="width:100%" href="detail?pid=${book.bookID}&isFlash=true" title="${book.bookTitle}" onclick="alertFlashSaleEnding(event)">
                                        <img class="cover-image" src="${book.bookCover}"
                                             alt="Hình bìa ${book.bookTitle}" />
                                    </a>
                                    <c:if test="${book.bookQuantity == 0}">
                                        <div class="out-of-stock">
                                            Hết hàng
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="info-container">
                                <h3 class="product-title">
                                    <a href="detail?pid=${book.bookID}&isFlash=true" title="${book.bookTitle}" onclick="alertFlashSaleEnding(event)">
                                        ${book.bookTitle}
                                    </a>
                                </h3>
                                <div class="product-price">
                                    <p class="product-price-discounted">
                                        <fmt:formatNumber value="${book.bookPrice * (100 - book.bookDiscount) / 100}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>

                                    <p class="product-price-original">
                                        <fmt:formatNumber value="${book.bookPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="row" id="featuredBooks3">
                    <c:forEach var="book" items="${requestScope.bestBooks}">
                        <div class="product-container col-6 col-md-4">
                            <div class="cover-container">
                                <div class="discount">
                                    <span class="discount-amount">${book.bookDiscount}%</span>
                                </div>
                                <div class="cover">
                                    <a style="width:100%" href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                        <img class="cover-image" src="${book.bookCover}"
                                             alt="Hình bìa ${book.bookTitle}" />
                                    </a>
                                    <c:if test="${book.bookQuantity == 0}">
                                        <div class="out-of-stock">
                                            Hết hàng
                                        </div>
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
                                        <fmt:formatNumber value="${book.bookPrice * (100 - book.bookDiscount) / 100}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>

                                    <p class="product-price-original">
                                        <fmt:formatNumber value="${book.bookPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="row" id="featuredBooks4">
                    <c:forEach var="book" items="${requestScope.discountBooks}">
                        <div class="product-container col-6 col-md-4">
                            <div class="cover-container">
                                <div class="discount">
                                    <span class="discount-amount">${book.bookDiscount}%</span>
                                </div>
                                <div class="cover">
                                    <a style="width:100%" href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                        <img class="cover-image" src="${book.bookCover}"
                                             alt="Hình bìa ${book.bookTitle}" />
                                    </a>
                                    <c:if test="${book.bookQuantity == 0}">
                                        <div class="out-of-stock">
                                            Hết hàng
                                        </div>
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
                                        <fmt:formatNumber value="${book.bookPrice * (100 - book.bookDiscount) / 100}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>

                                    <p class="product-price-original">
                                        <fmt:formatNumber value="${book.bookPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="col-12 text-center view-more">
                <a href="SanPhamcontrol" class="button-more">Xem thêm</a>
            </div>
        </div>        
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
        <script src="js/scriptHomepage.js"></script>
        <c:import url="footer.jsp"/>
    </body>
</html>