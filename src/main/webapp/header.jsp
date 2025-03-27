<%-- 
Document   : header
Created on : Oct 12, 2024, 4:23:03 PM
Author     : anhkc
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <script src="https://kit.fontawesome.com/bfab6e6450.js" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css/styleHeaderFooter.css"/>
        <!-- Thêm CSS và JS của Bootstrap (hoặc tuỳ theo framework bạn đang dùng) -->
        <style>
            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-menu {
                display: none;
                position: absolute;
                top: 100%;
                left: 0;
                background-color: white;
                border: 1px solid #ccc;
                min-width: 150px;
                z-index: 1000;
            }

            .dropdown:hover .dropdown-menu {
                display: block;
            }

            .dropdown-item {
                padding: 8px 12px;
                text-decoration: none;
                display: block;
                color: black;
            }

            .dropdown-item:hover {
                background-color: #f1f1f1;
            }
        </style>
    </head>
    <body>
        <c:import url="Layout/darkmode.jsp"/>
        <div id="google_translate_element" style="position: absolute; top: 10px; right: 10px;"></div>
        <script type="text/javascript">
            function removeUnwantedLanguages() {
                let allowedLanguages = ["vi", "ko", "ja", "en", "zh-CN", "th", "ar"]; // Chỉ giữ lại các ngôn ngữ này
                let dropdown = document.querySelector('.goog-te-combo');

                if (dropdown) {
                    for (let i = dropdown.options.length - 1; i >= 0; i--) {
                        let langCode = dropdown.options[i].value;
                        if (!allowedLanguages.includes(langCode) && langCode !== '') {
                            dropdown.remove(i);
                        }
                    }
                }
            }

            // Dùng MutationObserver để theo dõi sự thay đổi trên DOM và ẩn ngay lập tức
            const observer = new MutationObserver(() => {
                removeUnwantedLanguages();
            });

            observer.observe(document.body, {childList: true, subtree: true});

            function googleTranslateElementInit() {
                new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
            }

        </script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <script>
            document.getElementById('languageSelector').addEventListener('change', function () {
                let lang = this.value;
                let selectedOption = this.options[this.selectedIndex];
                let flagCode = selectedOption.getAttribute('data-flag');
                document.getElementById('flag-icon').src = `https://flagcdn.com/w40/${flagCode}.png`;

                // Danh sách ngôn ngữ được hỗ trợ
                const allowedLanguages = {
                    "vi": "🇻🇳", // Việt Nam
                    "ko": "🇰🇷", // Hàn Quốc
                    "ja": "🇯🇵", // Nhật Bản
                    "en": "🇬🇧", // Tiếng Anh
                    "zh-CN": "CN", // Tiếng Trung
                    "th": "🇹🇭", // Thái

                    "ar": "🇸🇦"  // Ả Rập Xê Út
                };

                // Kiểm tra nếu ngôn ngữ không hợp lệ
                if (!allowedLanguages.hasOwnProperty(lang)) {
                    console.warn(`Ngôn ngữ "${lang}" không được hỗ trợ.`);
                    return;
                }

                let textElements = document.querySelectorAll('[data-translate]');

                textElements.forEach(el => {
                    let originalStyle = el.getAttribute('style');
                    let text = el.innerText.trim();

                    if (!text) {
                        console.warn("Bỏ qua phần tử rỗng hoặc không có nội dung.");
                        return;
                    }

                    try {
                        let encodedText = encodeURIComponent(text);
                        fetch(`https://translation.googleapis.com/language/translate/v2?key=YOUR_API_KEY&q=${encodedText}&target=${lang}`)
                                .then(response => response.json())
                                .then(data => {
                                    if (data.data && data.data.translations.length > 0) {
                                        el.innerText = data.data.translations[0].translatedText;
                                        if (originalStyle) {
                                            el.setAttribute('style', originalStyle);
                                        }
                                    }
                                })
                                .catch(error => console.error('Lỗi dịch:', error));
                    } catch (e) {
                        console.error('Lỗi mã hóa chuỗi:', e);
                    }
                });
            });
        </script>
        <div class="jumpToTop">
            <a href="#topOfPage">
                <i class="fa-solid fa-circle-chevron-up"></i>
            </a>
        </div>

        <section id="mobile-bottom-navigation" class="d-block d-md-none fixed-bottom">
            <div class="row mg-left-0">
                <div class="col-4">
                    <div class="mobile-nav-item">
                        <a href="contact.jsp" class="link mobile-quick-link">
                            <i class="fa-solid fa-phone"></i>Liên hệ
                        </a>
                    </div>
                </div>
                <div class="col-4">
                    <div class="mobile-nav-item">
                        <a href="SanPhamcontrol" class="link mobile-quick-link">
                            <i class="fas fa-gift"></i>Hot Deals
                        </a>
                    </div>
                </div>
                <div class="col-4">
                    <div class="mobile-nav-item x">
                        <c:if test="${sessionScope.LOGIN_USER != null}">
                            <a class="link mobile-quick-link" href="listOrder" title="${sessionScope.LOGIN_USER.getUsername()}">
                                <i class="fas fa-user"></i>Chào ${sessionScope.LOGIN_USER.getUsername()}
                            </a>
                            <a class="link mobile-quick-link" href="Logout">Đăng xuất</a>
                        </c:if>
                        <c:if test="${sessionScope.LOGIN_USER == null}">
                            <a class="link mobile-quick-link" href="login" title="Đăng nhập"> 
                                <i class="fas fa-user"></i>Đăng nhập
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </section>
        <header>
            <div class="header-topbar d-none d-sm-block">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-sm-9">
                        </div>
                        <div class="col-sm-3">
                            <div class="account float-end me-5">
                                <c:if test="${sessionScope.LOGIN_USER != null}">
                                    <div class="dropdown ">
                                        <a class="link dropdown-toggle" href="#" id="accountDropdown" role="button">
                                            Xin chào, ${sessionScope.LOGIN_USER.getUsername()}
                                        </a>
                                        <ul class="dropdown-menu" aria-labelledby="accountDropdown">
                                            <li><a class="dropdown-item" href="ShowProfileServlet?customerID=${customer.customerID}"> Hồ sơ</a></li>
                                                <c:choose>
                                                    <c:when test="${sessionScope.LOGIN_USER.roleId eq 'ROL003'}">
                                                    <li><a class="dropdown-item" href="listOrder">Đơn mua</a></li>
                                                    <li><a class="dropdown-item" href="Favorite">Yêu thích</a></li>
                                                    </c:when>
                                                    <c:when test="${sessionScope.LOGIN_USER.roleId eq 'ROL004'}">
                                                    <li><a class="dropdown-item" href="ShipperOrder">Vận Chuyển</a></li>
                                                    </c:when>
                                                </c:choose>
                                            <li><a class="dropdown-item" href="Logout">Đăng xuất</a></li>
                                        </ul>
                                    </div>
                                </c:if>
                                <c:if test="${sessionScope.LOGIN_USER == null}">
                                    <div>
                                        <a class="link" href="login" title="Đăng ký">Đăng ký</a>
                                        <a class="link" href="login" title="Đăng nhập">Đăng nhập</a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="header-midbar">
                <div class="container">
                    <div class="row">
                        <div class="mobile-navigation col-3">
                            <a href="#" type="button" data-bs-toggle="offcanvas"
                               data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">
                                <i class="fa fa-bars"></i>
                            </a>
                            <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false"
                                 tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
                                <div class="offcanvas-header">
                                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas"
                                            aria-label="Close"></button>
                                </div>
                                <div class="offcanvas-body">
                                    <ul class="nav flex-column">
                                        <li class="nav-item">
                                            <a class="nav-link" href="home">TRANG CHỦ</a>
                                        </li>

                                        <li class="nav-item">
                                            <a class="nav-link" href="about-us.jsp">ABOUT US</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="contact.jsp">LIÊN HỆ</a>
                                        </li>
                                        <li class="nav-item">
                                            <c:if test="${sessionScope.LOGIN_USER.roleId == 'ROL001'}">
                                            <li><a class="nav-link" href="manageUser">Quản lý user</a></li>
                                            <li><a class="nav-link" href="AdminStatistic">Quản lý doanh thu</a></li>
                                            </c:if>
                                            <c:if test="${sessionScope.LOGIN_USER.roleId == 'ROL002' or sessionScope.LOGIN_USER.roleId == 'ROL001'}">
                                            <li><a class="nav-link" href="ListServlet">Quản lý sách</a></li>
                                            <li><a class="nav-link" href="viewReports">Xem góp ý</a>
                                            <li><a class="nav-link" href="https://app.chatra.io/conversations/sqzzSvkKoxdYQkpHN" target="_blank">Chat</a></li>
                                            </li>
                                        </c:if>
                                        <c:if test="${sessionScope.LOGIN_USER.roleId == 'ROL004'}">
                                            <li><a class="nav-link" href="ShipperStatistic">Lương</a></li>
                                            </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="header-logo col-6" id="topOfPage">
                            <h1>
                                <a href="home" class="link logo-content d-flex align-items-center">
                                    <i class="fa-solid fa-book-open me-2"></i>
                                    <p class="slogan text-center mb-0">WRITE & READ WORLD</p>
                                    <i class="fa-solid fa-pen-nib me-2"></i>
                                </a>
                            </h1>
                        </div>
                        <div class="header-cart col-3">
                            <div class="midbar-cart">

                                <div class="shopping_cart">
                                    <c:if test="${sessionScope.LOGIN_USER.getRoleId() eq 'ROL003'}">
                                        <h1 class="cart-brief-content">
                                            <a href="cart" title="Giỏ hàng" rel="nofollow" class="link-to-cart float-end">
                                                <i class="fa-solid fa-cart-shopping cart-icon"></i>
                                                <p class="cart-more-info d-none d-md-block">
                                                    <span class="cart-icon-detail">Giỏ hàng</span>
                                                    <span class="display_cart_quantity">${sessionScope.cart.totalQuantity}</span> sản phẩm
                                                </p>
                                            </a>
                                        </h1>
                                    </c:if>
                                    <div class="cart-content">
                                        <div class="cart-container-header">
                                            <form action="cart" method="post" id="cart-form-mini">
                                                <c:forEach var="item" items="${sessionScope.cart.itemList}">
                                                    <div class="row" style="height: fit-content">
                                                        <div class="col-4">
                                                            <input type="hidden" name="pid" value="${item.getBook().getBookID()}"/>
                                                            <img src="${item.book.bookCover}" alt="Hình bìa ${item.book.bookTitle}" class="img-fluid">
                                                        </div>
                                                        <div class="col-8">
                                                            <div class="col-12 book-title">
                                                                <strong>${item.book.bookTitle}</strong>
                                                            </div>
                                                            <div class="col-12 book-info">
                                                                ${item.book.bookVersion}
                                                            </div>
                                                            <div class="col-12 book-info">
                                                                <fmt:formatNumber value="${item.finalPrice}" type="currency" currencySymbol="VND" groupingUsed="true"/>
                                                                x ${item.quantity}
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                                <div class="row">
                                                    <div class="col-5 text-start" >
                                                        <div class="total">
                                                            <strong>Tổng cộng:</strong>
                                                        </div>
                                                    </div>
                                                    <div class="col-7 text-end" >
                                                        <p><fmt:formatNumber value="${sessionScope.cart.totalAmount}" type="currency" currencySymbol="₫" groupingUsed="true"/></p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="header-search col-12">
                            <form id="J_searchForm" class="search-form clearfix" action="search" method="post">
                                <label for="search" class="hide"></label>
                                <!-- <input type="hidden" name="type" value="product"> -->
                                <input class="search-text" name="query" type="search" id="search"
                                       placeholder="   Tìm kiếm...">
                                <button type="submit" class="search-btn">
                                    <i class="fa-solid fa-magnifying-glass"></i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="header-navbar-main d-none d-lg-block">
                <div class="container">
                    <ul class="nav nav-fill">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/home">TRANG CHỦ</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="SanPhamcontrol">SẢN PHẨM</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/about-us.jsp">VỀ ICHIBOOKS</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/contact.jsp">LIÊN HỆ</a>
                        </li>
                    </ul>
                </div>
            </div>
        </header>
        <!-- Bootstrap JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </body>
</html>
