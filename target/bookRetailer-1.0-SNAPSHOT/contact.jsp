<%-- 
    Document   : fdfd
    Created on : Nov 4, 2024, 10:34:34 PM
    Author     : conkg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Contact Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css/styleHeaderFooter.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            .section-title {
                font-size: 24px;
                font-weight: bold;
                margin-top: 20px;
                margin-bottom: 20px;
            }
            .map-container {
                margin-bottom: 20px;
            }
            .comments-section, .location-section {
                margin-top: 20px;
            }
            .section-title, .comments-section, .location-section {
                padding-left: 15px;
                padding-right: 15px;
            }
            .location-details {
                background-color: #f8f9fa;
                border-radius: 5px;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .location-details p {
                margin: 0;
                font-size: 16px;
                line-height: 1.5;
            }
        </style>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="container">
            <div class="section-title">LIÊN HỆ</div>
            <div class="map-container">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3929.634073634073!2d105.7599983154021!3d10.02851199310414!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31a0880b5b5b5b5b%3A0x5b5b5b5b5b5b5b!2sFPT%20Can%20Tho!5e0!3m2!1sen!2s!4v1633072800000!5m2!1sen!2s" width="100%" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
            </div>
            <div class="col-md-6 location-section">
                <div class="section-title">Chúng tôi ở đây</div>
                <div class="location-details">
                    <p><strong>Đại học FPT Cần Thơ</strong></p>
                    <p>600 Nguyễn Văn Cừ, Phường An Bình, Quận Ninh Kiều, Thành phố Cần Thơ</p>
                    <p>Email: <a href="https://daihoc.fpt.edu.vn/huong-dan-cach-tao-tai-khoan-email-fpt-edu-vn-cho-tan-sinh-vien-k17/">contact@fpt.edu.vn</a></p>
                    <p>Điện thoại: 0292 7305 925</p>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
        <!--        <script src="js/script.js"></script>
                <script src="js/scriptHomepage.js"></script>-->
        <c:import url="footer.jsp"/>
    </body>
</html>