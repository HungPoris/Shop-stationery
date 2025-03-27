<%-- 
    Document   : darkmode
    Created on : Mar 23, 2025, 11:50:03 PM
    Author     : HungTQ
--%>

<%@ page contentType="text/html; charset=UTF-8" %>

<!-- CSS Dark Mode -->
<style>
    /* Light Mode (Mặc định) */
    body {
        background-color: white;
        color: black;
        transition: background-color 0.3s, color 0.3s;
    }

    /* Dark Mode */
    body.dark-mode {
        background-color: #121212;
        color: white;
    }

    /* Header Dark Mode */
    .dark-mode #header {
        background-color: #1e1e1e !important;
        color: white !important;
    }

    .dark-mode #header a,
    .dark-mode #header .nav-link {
        color: white !important;
    }

    /* Search Bar */
    .dark-mode .search-bar {
        background-color: #2b2b2b !important;
        color: white !important;
        border: 1px solid #ffffff;
    }


    .dark-mode .content-container {
        background-color: #222 !important;
        color: #fff !important;
    }


    .dark-mode .product-container {
        background-color: #1e1e1e;
        color: #ffffff;
        border-color: #333;
    }

    /* Đổi màu chữ cho toàn bộ nội dung */
    .dark-mode h1,
    .dark-mode h2,
    .dark-mode h3,
    .dark-mode h4,
    .dark-mode h5,
    .dark-mode h6,
    .dark-mode p,
    .dark-mode span,
    .dark-mode a,
    .dark-mode li {
        color: #E3FCBF !important;
    }

    /* Link màu xanh dương để dễ nhận diện */
    .dark-mode a {
        color: #1e90ff !important;
    }

    /* Button */
    .dark-mode button {
        background-color: #333 !important;
        color: white !important;
    }

    /* Input */
    .dark-mode input,
    .dark-mode textarea {
        background-color: #2b2b2b !important;
        color: white !important;
        border: 1px solid #ffffff !important;
    }

    /* Nút chuyển đổi Dark Mode (Đưa xuống cuối trang) */
    #toggleDarkMode {
        position: fixed;
        bottom: 100px; /* Giữ nguyên vị trí dọc */
        left: 20px; /* Chuyển sang bên trái */
        padding: 8px 12px;
        border: none;
        background-color: #007bff;
        color: white;
        cursor: pointer;
        border-radius: 5px;
        font-size: 14px;
        z-index: 9999;
    }

    body.dark-mode #toggleDarkMode {
        background-color: #ff9800;
    }

    /* Áp dụng Dark Mode cho toàn bộ section */
    .dark-mode section {
        background-color: #222 !important; /* Nền tối */
        color: #ffffff !important; /* Chữ trắng để dễ đọc */
    }

    /* Nếu section có lớp cụ thể */
    .dark-mode .main-section {
        background-color: #1e1e1e !important;
        color: #fff !important;
    }

    /* Đảm bảo các phần tử trong section có màu sắc phù hợp với Dark Mode */
    .dark-mode section h1 {
        color: #ffcc00 !important;
    } /* Màu vàng nhạt */
    .dark-mode section h2 {
        color: #ffa500 !important;
    } /* Cam nhạt */
    .dark-mode section h3 {
        color: #ff6666 !important;
    } /* Đỏ nhạt */
    .dark-mode section h4 {
        color: #66ccff !important;
    } /* Xanh da trời nhạt */
    .dark-mode section h5 {
        color: #00ffcc !important;
    } /* Xanh ngọc */
    .dark-mode section h6 {
        color: #cc99ff !important;
    } /* Tím nhạt */
    .dark-mode section p {
        color: #dcdcdc !important;
    } /* Xám sáng để dễ đọc */
    .dark-mode section span {
        color: #ff99cc !important;
    } /* Hồng pastel */
    .dark-mode section a {
        color: #4db8ff !important;
    } /* Xanh dương tươi */
    .dark-mode section li {
        color: #ffffff !important;
    } /* Trắng để dễ đọc */


    .dark-mode .header-midbar {
        background-color: #1e1e1e !important;
        color: white !important;
    }

    .dark-mode .header-midbar a,
    .dark-mode .header-midbar p,
    .dark-mode .header-midbar span {
        color: white !important;
    }

    .dark-mode .location-details {
        background-color: #1e1e1e !important; /* Nền tối */
        color: white !important; /* Chữ trắng */
        border-color: #ffffff !important; /* Viền trắng nếu có */
    }

    .dark-mode .location-details a {
        color: #1e90ff !important; /* Link có màu xanh dương để dễ nhìn */
    }

    .dark-mode .location-details p,
    .dark-mode .location-details span,
    .dark-mode .location-details li {
        color: white !important; /* Đảm bảo toàn bộ chữ đổi sang trắng */
    }

    .dark-mode .location-details input,
    .dark-mode .location-details textarea {
        background-color: #2b2b2b !important;
        color: white !important;
        border: 1px solid #ffffff !important;
    }
    /* Dropdown Menu - Dark Mode */
    .dark-mode .dropdown-menu {
        background-color: #2b2b2b !important; /* Nền dropdown tối */
        border: 1px solid #444 !important; /* Viền dropdown tối hơn */
    }

    .dark-mode .dropdown-menu .dropdown-item {
        color: white !important; /* Chữ màu trắng */
    }

    .dark-mode .dropdown-menu .dropdown-item:hover {
        background-color: #3a3a3a !important; /* Màu nền khi hover */
        color: #1e90ff !important; /* Chữ đổi sang màu xanh dương */
    }

    .dark-mode .dropdown {
        background-color: #1e1e1e !important; /* Background dropdown */
        color: white !important; /* Chữ trắng */
    }

    /* Nếu dropdown có icon */
    .dark-mode .dropdown-toggle::after {
        border-top-color: white !important; /* Mũi tên dropdown thành màu trắng */
    }

    /* Nút dropdown */
    .dark-mode .btn.dropdown-toggle {
        background-color: #333 !important; /* Màu nền nút */
        color: white !important;
        border: 1px solid white !important;
    }

    /* Khi hover vào nút dropdown */
    .dark-mode .btn.dropdown-toggle:hover {
        background-color: #444 !important;
        color: #1e90ff !important;
    }
    /* Offcanvas - Dark Mode */
    .dark-mode .offcanvas {
        background-color: #1e1e1e !important; /* Màu nền tối */
        color: white !important; /* Màu chữ */
    }

    .dark-mode .offcanvas-header {
        background-color: #2b2b2b !important; /* Nền header offcanvas */
        border-bottom: 1px solid #444 !important; /* Viền ngăn cách */
        color: white !important;
    }

    .dark-mode .offcanvas-title {
        color: white !important;
    }

    .dark-mode .offcanvas-body {
        background-color: #1e1e1e !important;
        color: white !important;
    }

    .dark-mode .offcanvas-body a {
        color: #1e90ff !important; /* Màu link xanh */
    }

    .dark-mode .offcanvas-body a:hover {
        color: #ff9800 !important; /* Màu hover vàng cam */
    }

    /* Nút đóng */
    .dark-mode .offcanvas .btn-close {
        filter: invert(1) !important; /* Chuyển nút đóng thành màu trắng */
    }
    /* Container - Dark Mode */
    .dark-mode .container,
    .dark-mode .container-fluid {
        background-color: #1e1e1e !important; /* Màu nền tối */
        color: white !important; /* Màu chữ */
    }

    /* Các thành phần bên trong container */
    .dark-mode .container p {
        color: #E0E0E0 !important; /* Màu xám nhạt, dễ đọc */
    }

    .dark-mode .container span {
        color: #A0E6FF !important; /* Màu xanh nhạt, tạo điểm nhấn */
    }

    .dark-mode .container a {
        color: #4DD0E1 !important; /* Màu xanh cyan nhẹ */
    }

    .dark-mode .container h1 {
        color: #FF9800 !important; /* Màu cam nổi bật */
    }

    .dark-mode .container h2 {
        color: #F44336 !important; /* Đỏ nhạt, dễ nhìn */
    }

    .dark-mode .container h3 {
        color: #FFEB3B !important; /* Vàng sáng, tạo điểm nhấn */
    }

    .dark-mode .container h4 {
        color: #4CAF50 !important; /* Xanh lá nhẹ, dịu mắt */
    }

    .dark-mode .container h5 {
        color: #B39DDB !important; /* Tím pastel */
    }

    .dark-mode .container h6 {
        color: #9E9E9E !important; /* Xám trung tính */
    }


    /* Định dạng liên kết */
    .dark-mode .container a {
        color: #1e90ff !important; /* Link có màu xanh */
    }

    .dark-mode .container a:hover {
        color: #ff9800 !important; /* Hover chuyển sang màu cam */
    }

    /* Input, Textarea bên trong container */
    .dark-mode .container input,
    .dark-mode .container textarea {
        background-color: #2b2b2b !important;
        color: white !important;
        border: 1px solid #ffffff !important;
    }
    /* Dark Mode cho bảng */
    .dark-mode table {
        background-color: #1e1e1e !important;
        color: white !important;
        border-color: #ffffff !important;
    }

    .dark-mode th,
    .dark-mode td {
        background-color: #2b2b2b !important;
        color: white !important;
        border-color: #ffffff !important;
    }

    /* Đổi màu chữ cho nội dung bên trong bảng */
    .dark-mode table tr,
    .dark-mode table td,
    .dark-mode table th {
        color: white !important;
    }

    /* Định dạng đường viền */
    .dark-mode table,
    .dark-mode th,
    .dark-mode td {
        border: 1px solid #444 !important;
    }

    /* Đổi màu hover khi Dark Mode */
    .dark-mode table tr:hover {
        background-color: #333 !important;
    }
    /* Dark Mode cho popup */
    .dark-mode .popup-content {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Màu chữ trắng */

    }

    /* Dark Mode cho tiêu đề popup */
    .dark-mode .popup-content h1 {
        color: #FF9800 !important; /* Màu cam nổi bật */
    }

    .dark-mode .popup-content h2 {
        color: #F44336 !important; /* Màu đỏ nhẹ */
    }

    .dark-mode .popup-content h3 {
        color: #FFEB3B !important; /* Màu vàng sáng */
    }

    .dark-mode .popup-content h4 {
        color: #4CAF50 !important; /* Xanh lá mềm */
    }

    .dark-mode .popup-content h5 {
        color: #64B5F6 !important; /* Xanh dương nhạt */
    }

    .dark-mode .popup-content h6 {
        color: #B39DDB !important; /* Màu tím pastel */
    }

    .dark-mode .lable {
        color: #E3FCBF !important;
    }
    /* Nội dung popup */
    .dark-mode .popup-content p {
        color: #E3FCBF !important; /* Màu xám nhạt giúp dễ đọc hơn */
    }


    /* Dark Mode cho nút trong popup */
    .dark-mode .popup-content button {
        background-color: #444 !important;
        color: white !important;
        border: 1px solid white !important;
    }

    /* Dark Mode cho input trong popup */
    .dark-mode .popup-content input,
    .dark-mode .popup-content textarea {
        background-color: #333 !important;
        color: white !important;
        border: 1px solid #555 !important;
    }

    /* Hover khi Dark Mode */
    .dark-mode .popup-content button:hover {
        background-color: #666 !important;
    }
    /* Dark Mode cho header-topbar */
    .dark-mode .header-topbar {
        background-color: #1e1e1e !important; /* Màu nền tối */
        color: white !important; /* Màu chữ trắng */
    }

    /* Dark Mode cho chữ trong topbar */
    .dark-mode .header-topbar a,
    .dark-mode .header-topbar span {
        color: white !important;
    }

    /* Dark Mode cho icon trong topbar */
    .dark-mode .header-topbar i {
        color: #ff9800 !important; /* Màu vàng cam để nổi bật */
    }

    /* Dark Mode cho dropdown trong topbar */
    .dark-mode .header-topbar .dropdown-menu {
        background-color: #2b2b2b !important;
        color: white !important;
        border: 1px solid #444 !important;
    }

    .dark-mode .header-topbar .dropdown-item {
        color: white !important;
    }

    .dark-mode .header-topbar .dropdown-item:hover {
        background-color: #444 !important;
    }
    /* Dark Mode cho advanced-filter */
    .dark-mode .advanced-filter.show {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */
        border: 2px solid #1e90ff !important; /* Viền xanh dương */
        border-radius: 5px;
    }
    /* Dark Mode cho category-list */
    .dark-mode .category-list {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }


    .dark-mode .dropdown {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */
        border: 2px solid #1e90ff !important; /* Viền xanh dương */

    }
    .dark-mode .sub-category-list {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }

    .dark-mode .container my-4 {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }
    .dark-mode .col-md-3 {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }

    .dark-mode .mt-3 {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }

    .dark-mode .nav-link {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }
    .dark-mode .sidebar {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */

    }

    .dark-mode .cart-container-header {
        background-color: #2b2b2b !important; /* Màu nền tối */
        color: white !important; /* Chữ trắng */
        border-radius: 5px;
    }
</style>

<!-- Nút bật/tắt Dark Mode (Ở cuối trang) -->
<button id="toggleDarkMode">🌙 Dark Mode</button>

<!-- JavaScript Dark Mode -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const toggleButton = document.getElementById("toggleDarkMode");
        const body = document.body;

        // Kiểm tra trạng thái Dark Mode trong Local Storage
        let darkMode = localStorage.getItem("darkMode");

        // Nếu chưa có trạng thái, kiểm tra chế độ hệ điều hành
        if (darkMode === null) {
            darkMode = window.matchMedia("(prefers-color-scheme: dark)").matches ? "enabled" : "disabled";
            localStorage.setItem("darkMode", darkMode);
        }

        // Áp dụng Dark Mode nếu đã bật trước đó
        if (darkMode === "enabled") {
            body.classList.add("dark-mode");
            toggleButton.textContent = "☀ Light Mode";
        } else {
            toggleButton.textContent = "🌙 Dark Mode";
        }

        // Xử lý khi nhấn nút Dark Mode
        toggleButton.addEventListener("click", function () {
            body.classList.toggle("dark-mode");

            if (body.classList.contains("dark-mode")) {
                localStorage.setItem("darkMode", "enabled");
                toggleButton.textContent = "☀ Light Mode";
            } else {
                localStorage.setItem("darkMode", "disabled");
                toggleButton.textContent = "🌙 Dark Mode";
            }
        });
    });
</script>
