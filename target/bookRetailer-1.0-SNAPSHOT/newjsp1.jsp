<%-- 
Document   : newjsp1
Created on : Oct 12, 2024, 4:23:03 PM
Author     : anhkc
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh Mục Sản Phẩm</title>
        <script src="https://kit.fontawesome.com/bfab6e6450.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/sanpham.css">
    </head>
    <body>
        <c:set var="sort" value="${sessionScope.sort.toString()}" />
        <c:import url="header.jsp"/>
        <div class="container mt-4">
            <div class="row">
                <div class="col-12 d-flex justify-content-between align-items-center mb-3">
                    <!-- Nút Tìm kiếm nâng cao -->
                    <button type="button" class="btn btn-primary" onclick="toggleFilter()">Tìm kiếm nâng cao</button>

                    <!-- Dropdown Sắp xếp -->
                    <div class="d-flex align-items-center">
                        <label class="me-2 fw-bold">Sắp xếp theo:</label>
                        <div class="dropdown">
                            <button class="btn btn-light dropdown-toggle" type="button" id="sortDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                                ${sessionScope.sort eq 'newest' ? 'Mới nhất' :
                                  sessionScope.sort eq 'bestselling' ? 'Bán chạy' :
                                  sessionScope.sort eq 'hotdeal' ? 'Hot Deal' :
                                  sessionScope.sort eq 'a-z' ? 'A-Z' : 'Mới nhất'}
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                                <li><a class="dropdown-item" href="SanPhamcontrol?sort=newest">Mới nhất</a></li>
                                <li><a class="dropdown-item" href="SanPhamcontrol?sort=bestselling">Bán chạy</a></li>
                                <li><a class="dropdown-item" href="SanPhamcontrol?sort=hotdeal">Hot Deal</a></li>
                                <li><a class="dropdown-item" href="SanPhamcontrol?sort=a-z">A-Z</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- BỘ LỌC NÂNG CAO -->
                <div class="advanced-filter" id="filterBox">
                    <form id="filterForm" action="SanPhamcontrol" method="POST">
                        <h4>Tìm Kiếm Nâng Cao</h4>
                        <div class="checkbox-group">
                            <c:forEach var="category" items="${bookCategory}">
                                <c:set var="isChecked" value="false" />
                                <c:forEach var="filter" items="${sessionScope.Filter}">
                                    <c:if test="${category.categoryID eq filter}">
                                        <c:set var="isChecked" value="true" />
                                    </c:if>
                                </c:forEach>
                                <label>
                                    <input type="checkbox" name="category" value="${category.categoryID}" 
                                           ${isChecked eq 'true' ? 'checked="checked"' : ''}> 
                                    ${category.categoryName}
                                </label>
                            </c:forEach>
                        </div>
                        <button type="submit" class="btn btn-primary mt-2">Tìm kiếm</button>
                        <button type="button" class="btn btn-success mt-2" onclick="resetFilter()">Reset</button>
                        <input type="hidden" id="groupList" name="groupList">
                    </form>
                </div>

                <!-- HÀNG THỨ 2: DANH MỤC BÊN TRÁI + SẢN PHẨM BÊN PHẢI -->
                <div class="col-md-3">
                    <!-- DANH MỤC SẢN PHẨM -->
                    <h4 class="mt-4">Danh Mục</h4>
                    <ul class="category-list">
                        <c:forEach var="entry" items="${groupedCategories}">
                            <li class="dropdown">
                                <a>${entry.key}</a>
                                <ul class="sub-category-list">
                                    <c:forEach var="o" items="${entry.value}">
                                        <li>
                                            <a href="#" onclick="submitCategory('${o.categoryID}'); return false;">${o.categoryName}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <!-- SẢN PHẨM BÊN PHẢI -->
                <div class="col-md-9">
                    <div class="row">
                        <c:forEach var="book" items="${requestScope.listP}">
                            <div class="product-container col-md-3">
                                <div class="cover-container">
                                    <div class="discount">
                                        <span class="discount-amount">${book.bookDiscount}%</span>
                                    </div>
                                    <div class="cover">
                                        <a href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                            <img class="cover-image w-100 h-75" src="${book.bookCover}" alt="Hình bìa ${book.bookTitle}" />
                                        </a>
                                        <c:if test="${book.bookQuantity == 0}">
                                            <div class="out-of-stock">Hết hàng</div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="info-container">
                                    <h3 class="product-title">
                                        <a href="#" title="${book.bookTitle}">${book.bookTitle}</a>
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
                </div>
            </div>
        </div>
        <ul class="pagination">
            <c:if test="${requestScope.currentPage > 1}">
                <li><a href="SanPhamcontrol?page=${requestScope.currentPage - 1}">Previous</a></li>
                </c:if>

            <!-- Page number links -->
            <c:forEach var="i" begin="1" end="${requestScope.totalPages}">
                <c:choose>
                    <c:when test="${i == requestScope.currentPage}">
                        <!-- Highlight the current page number -->
                        <li><span>${i}</span></li>
                            </c:when>
                            <c:otherwise>
                        <li><a href="SanPhamcontrol?page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

            <!-- Next button (shown if not on the last page) -->
            <c:if test="${requestScope.currentPage < requestScope.totalPages}">
                <li><a href="SanPhamcontrol?page=${requestScope.currentPage + 1}">Next</a></li>
                </c:if>                                
        </ul>
        <c:import url="footer.jsp"/>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let currentSort = "${sessionScope.sort}"; // Lấy giá trị sắp xếp từ session
                let sortDropdown = document.getElementById("sortDropdown");

                if (sortDropdown) {
                    document.querySelectorAll(".dropdown-item").forEach(item => {
                        if (item.getAttribute("href").includes("sort=" + currentSort)) {
                            sortDropdown.innerText = item.innerText;
                        }
                    });
                }
            });


            function submitForm() {
                document.getElementById("filterForm").submit();
            }

            function submitCategory(categoryID) {
                let input = document.getElementById("groupList");
                let form = document.getElementById("filterForm");

                if (!input || !form) {
                    console.error("Không tìm thấy input hoặc form!");
                    return;
                }

                input.value = categoryID;
                form.submit();
            }

            function toggleFilter() {
                var filterBox = document.getElementById("filterBox");
                filterBox.classList.toggle("show");
                console.log("Đã đổi class 'show'");
            }

            function resetFilter() {
                document.getElementById("filterForm").reset();
                window.location.href = "SanPhamcontrol?reset=true";
            }
        </script>
    </body>
</html>
