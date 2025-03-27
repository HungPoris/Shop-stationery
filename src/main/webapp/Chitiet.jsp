<%-- 
    Document   : Chitiet
    Created on : Oct 12, 2024, 4:23:03 PM
    Author     : anhkc
--%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <script src="https://kit.fontawesome.com/bfab6e6450.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css\style.css">
        <link rel="stylesheet" href="css\Chitiet.css">
        <link rel="stylesheet" href="css\Chitiet3.css">
    </head>
    <body>
        <c:import url="header.jsp"/>
        <main class="mt-5 pt-4">
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-6">
                        <img alt="Placeholder image" class="img-fluid" height="660" src="${Chitiet.bookCover}" width="500"/>
                    </div>
                    <div class="col-md-6">
                        <h1 class="product-title">
                            ${Chitiet.bookTitle}
                        </h1>
                        <div class="d-flex flex-column align-items-start">
                            <span class="discount-badge">
                                Khuyến Mãi: ${Chitiet.bookDiscount}%
                            </span>
                            <div class="product-price">
                                <p class="price discounted-price product-price-discounted">
                                    <fmt:formatNumber value="${Chitiet.bookPrice * (100 - Chitiet.bookDiscount)/100}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                </p>
                                
                                <p class=" original-price product-price-original">
                                    <fmt:formatNumber value="${Chitiet.bookPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                </p>
                            </div>
                        </div>
                        <div class="mt-3">  
                            <div >
                                <c:forEach var="author" items="${Chitiet1}">
                                    <p style="font:" class="mt-3 "> <strong > Tác giả     </strong>: ${author.authorName}</p>
                                </c:forEach>
                            </div>
                            <p><strong>Hình thức:</strong>${Chitiet.bookVersion}</p>
                            <p><strong>Nhà xuất bản:</strong>${Chitiet2.publisherName}</p>
                        </div>
                        <div class="product-details mt-3">
                            <p><strong>Nội dung:</strong></p>
                            <p>${Chitiet.bookIntro}</p>
                        </div>
                        <c:if test="${Chitiet.bookQuantity == 0}">

                            <div class="out-of-stock">
                                <strong>
                                    <h1>HẾT HÀNG</h1>
                                </strong>
                            </div>
                        </c:if>
                        <div class="d-flex align-items-center mt-3">
                            <form class="d-flex justify-content-left" action="cart" method ="post">
                                <input type="hidden" name="id" value="${Chitiet.bookID}">
                                <div class="form-outline me-1" style="width: 100px;">
                                    <input name="quantity"class="form-control" type="number" value="1" min="1" oninput="validity.valid || (value = 1)" />
                                </div>
                                <button class="btn ${Chitiet.bookQuantity == 0 ? 'btn-secondary' : 'btn-primary'} ms-1" type="submit" name="action" value="add"
                                        ${Chitiet.bookQuantity == 0 ? 'disabled' : ''}>
                                    THÊM VÀO GIỎ
                                    <i class="fas fa-shopping-cart ms-1"></i>
                                </button>
                            </form>
                            <form class="d-flex justify-content-left" action="Favorite" method ="post">
                                <input type="hidden" name="bookID" value="${Chitiet.bookID}">
                                <button class="btn btn-danger ms-1" 
                                        type="submit" name="action" value="favorite">
                                    <i class="fas fa-heart "></i>
                                </button>
                            </form>
                        </div>
                        <div class="category-tags mt-3">
                            <p><strong>DANH MỤC:</strong>
                                <c:forEach var="category" items="${Chitiet3}" varStatus="status">
                                    ${category.categoryName}<c:if test="${!status.last}">, </c:if>
                                </c:forEach>
                            </p>
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <!-- Cột chứa đánh giá và form -->
                        <div class="col-md-8">
                            <!-- Danh sách đánh giá -->
                            <c:choose>
                                <c:when test="${not empty feedbackList}">
                                    <div class="list-group border rounded p-3 mb-3" style="max-height: 400px; overflow-y: auto;">
                                        <c:forEach var="feedback" items="${feedbackList}">
                                            <div class="list-group-item d-flex">
                                                <!-- Thông tin người đánh giá -->
                                                <div class="me-3 text-center" style="width: 180px;">
                                                    <strong>${feedback.customer.firstName} ${feedback.customer.lastName}</strong>
                                                    <br>
                                                    <small class="text-muted">${feedback.createdAt}</small>
                                                </div>
                                                <!-- Nội dung đánh giá -->
                                                <div class="flex-grow-1">
                                                    ${feedback.content}
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-danger text-center mt-3">❌ No feedback available.</p>
                                </c:otherwise>
                            </c:choose>
                            <!-- Form nhập đánh giá -->
                            <div class="border rounded bg-light p-4">
                                <form action="detail" method="POST">
                                    <input type="hidden" name="bookID" value="<%= request.getParameter("pid")%>">
                                    <div class="mb-3">
                                        <label for="content" class="form-label fw-bold">Viết đánh giá của bạn:</label>
                                        <textarea name="content" id="content" class="form-control" rows="3" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary w-100">Gửi đánh giá</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <hr/>
            </div>
            <h2 style="text-align: center; margin-top: 50px; margin-bottom: 30px; color: #333;">Những sản phẩm liên quan</h2>
            <div class="suggested-books">
                <c:forEach var="book" items="${randomBooks}" begin="0" end="4">
                    <c:if test="${not empty book.bookTitle}">
                        <div class="book-card">
                            <div class="cover-container">
                                <c:if test="${book.bookDiscount > 0}">
                                    <div class="discount-label">${book.bookDiscount}%</div>
                                </c:if>
                                <a href="detail?pid=${book.bookID}" title="${book.bookTitle}">
                                    <img src="${book.bookCover}" alt="Hình bìa ${book.bookTitle}" class="book-cover">
                                </a>
                                <c:if test="${book.bookQuantity == 0}">
                                    <div class="out-of-stock">HẾT HÀNG</div>
                                </c:if>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </main>
        <!-- Form gửi feedback -->
        <c:import url="footer.jsp"/>
    </footer>
    <script crossorigin="anonymous" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" src="https://code.jquery.com/jquery-3.3.1.slim.min.js">
    </script>
    <script crossorigin="anonymous" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa2d4H9m1FMRk6a5ld5I5w1TB" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js">
    </script>
    <script crossorigin="anonymous" integrity="sha384-smHYkdEdqU2uM3a0C5I5cc3mozpSxMZ6b2d6drsZ8g8y5ztQx2b4fRIv8y1cHf65" src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js">
    </script>
    <script>
        function scrollLeft() {
            document.querySelector('.product-carousel').scrollBy({
                left: -200,
                behavior: 'smooth'
            });
        }
        function scrollRight() {
            document.querySelector('.product-carousel').scrollBy({
                left: 200,
                behavior: 'smooth'
            });
        }
        document.getElementById("openFeedbackModal").addEventListener("click", function () {
            document.getElementById("feedbackModal").style.display = "block";
        });
        document.querySelector(".close").addEventListener("click", function () {
            document.getElementById("feedbackModal").style.display = "none";
        });
        document.getElementById("feedbackForm").addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn chặn reload trang
            let formData = new FormData(this);
            fetch("feedback", {// Gửi request đến FeedbackServlet
                method: "POST",
                body: new URLSearchParams(formData)
            })
                    .then(response => response.json()) // Chuyển response thành JSON
                    .then(data => {
                        if (data.success) {
                            let feedbackList = document.getElementById("feedbackList");

                            // Tạo phản hồi mới và chèn vào đầu danh sách
                            let newFeedback = document.createElement("li");
                            newFeedback.innerHTML = `<strong>${data.createdAt}</strong> - <b>${data.accountID}</b>: ${data.content}`;
                            feedbackList.prepend(newFeedback);

                            document.getElementById("feedbackModal").style.display = "none"; // Ẩn modal
                            document.getElementById("feedbackForm").reset(); // Reset form
                        } else {
                            alert("Lỗi: " + data.error);
                        }
                    })
                    .catch(error => console.error("Error:", error));
        });
    </script>
</body>
</html>