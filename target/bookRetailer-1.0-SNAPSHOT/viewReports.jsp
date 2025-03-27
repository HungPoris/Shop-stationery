<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, model.Report" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách Báo cáo</title>
        <style>
            /* Thiết lập font chữ và căn chỉnh toàn bộ trang */
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                flex-direction: column;
            }

            h2 {
                color: #333;
                margin-top: 20px;
            }

            /* Thiết lập bảng */
            table {
                width: 90%;
                max-width: 1000px;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
                overflow: hidden;
                margin: 20px 0;
            }

            th, td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #ddd;
            }

            th {
                background-color: #007bff;
                color: white;
                text-transform: uppercase;
            }

            tr:hover {
                background-color: #f1f1f1;
            }

            /* Ảnh hiển thị đẹp hơn */
            img {
                max-width: 80px;
                border-radius: 5px;
                transition: transform 0.3s;
            }

            img:hover {
                transform: scale(1.2);
            }

            /* Nút thêm báo cáo */
            .add-report-btn {
                text-decoration: none;
                background-color: #28a745;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
                font-size: 16px;
                transition: 0.3s;
                margin-bottom: 20px;
            }

            .add-report-btn:hover {
                background-color: #218838;
            }

            /* Nút về trang chủ */
            .home-btn {
                position: absolute;
                top: 20px;
                left: 20px;
                text-decoration: none;
                background-color: #dc3545;
                color: white;
                padding: 10px 15px;
                border-radius: 5px;
                font-size: 16px;
                transition: 0.3s;
            }

            .home-btn:hover {
                background-color: #c82333;
            }
        </style>
    </head>
    <body>

        <!-- Nút về trang chủ -->
        <a href="http://localhost:8080/home" class="home-btn">🏠 Trang chủ</a>

        <h2>Danh sách Báo cáo</h2>

        <table>
            <tr>
                <th>ID</th>
                <th>Hình ảnh</th>
                <th>Mô tả</th>
            </tr>
            <c:forEach var="r" items="${requestScope.reportList}">
                <tr>
                    <td>${r.reportID}</td>
                    <td><img src="${r.image_path}" alt="Report Image"></td>
                    <td>${r.description}</td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>