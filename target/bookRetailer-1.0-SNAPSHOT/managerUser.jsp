<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý người dùng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .container * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            .container body {
                font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
                background-color: #f5f7fa;
                color: #333;
                line-height: 1.6;
            }

            .container .header {
                padding: 20px 30px;
                border-bottom: 1px solid #e0e0e0;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .container .title {
                font-size: 24px;
                font-weight: 600;
                color: #333;
            }

            .container .stats-container {
                display: grid;
                grid-template-columns: repeat(3, 1fr);
                gap: 20px;
                margin: 20px 30px;
            }

            .container .stat-card {
                background-color: #fff;
                border-radius: 6px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                padding: 15px;
                border: 1px solid #eaeaea;
                display: flex;
                align-items: center;
            }

            .container .stat-icon {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                margin-right: 15px;
                font-size: 20px;
            }

            .container .stat-icon .total {
                background-color: rgba(52, 152, 219, 0.1);
                color: #3498db;
            }

            .container .stat-icon.active {
                background-color: rgba(46, 204, 113, 0.1);
                color: #2ecc71;
            }

            .container .stat-icon.locked {
                background-color: rgba(231, 76, 60, 0.1);
                color: #e74c3c;
            }

            .container .stat-info {
                flex: 1;
            }

            .container .stat-value {
                font-size: 24px;
                font-weight: 700;
                margin-bottom: 2px;
            }

            .container .stat-label {
                color: #666;
                font-size: 14px;
            }

            .container .table-container {
                padding: 0 30px 30px;
            }

            .container .user-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
                font-size: 14px;
            }

            .container .user-table th {
                background-color: #f8f9fa;
                color: #333;
                font-weight: 600;
                text-align: left;
                padding: 12px 15px;
                border-bottom: 2px solid #eaeaea;
            }

            .container .user-table td {
                padding: 12px 15px;
                border-bottom: 1px solid #eaeaea;
                vertical-align: middle;
            }

            .container .user-table tr:hover {
                background-color: #f5f7fa;
            }

            .container .badge {
                display: inline-block;
                padding: 4px 8px;
                font-size: 12px;
                font-weight: 600;
                border-radius: 12px;
                text-align: center;
            }

            .container .badge-active {
                background-color: rgba(46, 204, 113, 0.1);
                color: #2ecc71;
            }

            .container .badge-locked {
                background-color: rgba(231, 76, 60, 0.1);
                color: #e74c3c;
            }

            .container .btn {
                padding: 8px 14px;
                border-radius: 4px;
                border: none;
                font-weight: 500;
                font-size: 14px;
                cursor: pointer;
                display: inline-flex;
                align-items: center;
                gap: 8px;
                transition: all 0.2s;
            }

            .container .btn-lock {
                background-color: #e74c3c;
                color: white;
            }

            .container .btn-unlock {
                background-color: #2ecc71;
                color: white;
            }

            .container .btn-edit {
                background-color: #3498db;
                color: white;
            }

            .container .btn:hover {
                opacity: 0.9;
            }

            .container .user-icon {
                width: 30px;
                height: 30px;
                background-color: #e9ecef;
                border-radius: 50%;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                margin-right: 10px;
                color: #6c757d;
            }

            .container .action-buttons {
                display: flex;
                gap: 8px;
            }

            .container .empty-message {
                text-align: center;
                padding: 40px 0;
                color: #666;
                font-size: 16px;
            }

            .container .user-name {
                display: flex;
                align-items: center;
            }

            @media (max-width: 768px) {
                .container .stats-container {
                    grid-template-columns: 1fr;
                    gap: 15px;
                }

                body {
                    padding: 15px;
                }

                .container .header {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }

                .container .user-table {
                    display: block;
                    overflow-x: auto;
                }
            }

            /* Popup Styles */
            .popup {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            .popup .popup-content {
                background-color: #fff;
                padding: 20px;
                border-radius: 8px;
                width: 400px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            }

            .popup .popup-content h2 {
                margin-bottom: 20px;
                font-size: 20px;
                color: #333;
            }

            .popup .close-btn {
                float: right;
                font-size: 24px;
                cursor: pointer;
                color: #666;
            }

            .popup .close-btn:hover {
                color: #333;
            }

            .popup .form-group {
                margin-bottom: 15px;
            }

            .popup .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
                color: #333;
            }

            .popup .form-group input,
            .form-group select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
            }

            .popup .btn-save {
                background-color: #3498db;
                color: white;
                width: 100%;
                padding: 10px;
                font-size: 16px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .popup .btn-save:hover {
                background-color: #2980b9;
            }
        </style>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="container">
            <div class="header">
                <h1 class="title">Quản lý người dùng</h1>
            </div>

            <!-- Stats Cards -->
            <div class="stats-container">
                <div class="stat-card">
                    <div class="stat-icon total">
                        <i class="fas fa-users"></i>
                    </div>
                    <div class="stat-info">
                        <div class="stat-value">
                            <c:set var="totalUsers" value="${accounts.size()}" />
                            ${totalUsers - 1}
                        </div>
                        <div class="stat-label">Tổng số tài khoản</div>
                    </div>
                </div>

                <div class="stat-card">
                    <div class="stat-icon active">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div class="stat-info">
                        <div class="stat-value">
                            <c:set var="activeUsers" value="0" />
                            <c:forEach var="user" items="${accounts}">
                                <c:if test="${user.status == 1}">
                                    <c:set var="activeUsers" value="${activeUsers + 1}" />
                                </c:if>
                            </c:forEach>
                            ${activeUsers - 1}
                        </div>
                        <div class="stat-label">Tài khoản hoạt động</div>
                    </div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon locked">
                        <i class="fas fa-user-lock"></i>
                    </div>
                    <div class="stat-info">
                        <div class="stat-value">
                            <c:set var="lockedUsers" value="0" />
                            <c:forEach var="user" items="${accounts}">
                                <c:if test="${user.status == 0}">
                                    <c:set var="lockedUsers" value="${lockedUsers + 1}" />
                                </c:if>
                            </c:forEach>
                            ${lockedUsers}
                        </div>
                        <div class="stat-label">Tài khoản bị khóa</div>
                    </div>
                </div>
            </div>
            <!-- Users Table -->
            <div class="table-container">
                <c:choose>
                    <c:when test="${empty accounts}">
                        <div class="empty-message">
                            <i class="fas fa-info-circle"></i> Không có dữ liệu người dùng!
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="user-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên đăng nhập</th>
                                    <th>Vai trò</th>
                                    <th>Ngày đăng ký</th>
                                    <th>Trạng thái</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${accounts}">
                                    <c:if test="${user.roleId != 'ROL001'}">
                                        <tr>
                                            <td>${user.accountId}</td>
                                            <td>
                                                <div class="user-name">
                                                    <div class="user-icon">
                                                        <i class="fas fa-user"></i>
                                                    </div>
                                                    ${user.username}
                                                </div>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.roleId == 'ROL002'}">
                                                        <span class="text-primary">Staff</span>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 'ROL003'}">
                                                        <span class="text-success">Customer</span>
                                                    </c:when>
                                                    <c:when test="${user.roleId == 'ROL004'}">
                                                        <span class="text-warning">Shipper</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-danger">Unknown</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${user.registrationDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.status == 1}">
                                                        <span class="badge badge-active">
                                                            <i class="fas fa-circle"></i> Hoạt động
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-locked">
                                                            <i class="fas fa-ban"></i> Bị khóa
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="action-buttons">
                                                    <button type="button" class="btn btn-edit" onclick="openEditPopup('${user.accountId}', '${user.roleId}')">
                                                        <i class="fas fa-edit"></i> Sửa
                                                    </button>
                                                    <form action="lockUnlockUser" method="post" style="display: inline;">
                                                        <input type="hidden" name="accountId" value="${user.accountId}" />
                                                        <input type="hidden" name="action" value="${user.status == 1 ? 'lock' : 'unlock'}" />
                                                        <button type="submit" class="btn ${user.status == 1 ? 'btn-lock' : 'btn-unlock'}"
                                                                onclick="return confirm('Bạn có chắc chắn muốn ${user.status == 1 ? 'vô hiệu hóa' : 'kích hoạt'} người dùng này không?');">
                                                            <c:choose>
                                                                <c:when test="${user.status == 1}">
                                                                    <i class="fas fa-lock"></i> Lock
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <i class="fas fa-unlock"></i> Unlock
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Popup chỉnh sửa người dùng -->
        <div id="editUserPopup" class="popup">
            <div class="popup-content">
                <span class="close-btn">&times;</span>
                <h2>Chỉnh sửa tài khoản</h2>
                <form id="editUserForm" action="manageUser" method="POST">
                    <input type="hidden" id="editAccountId" name="accountId" value="${user.accountId}"/>
                    <div class="form-group">
                        <label for="editRoleId">Vai trò:</label>
                        <select id="editRoleId" name="roleId" required>
                            <option value="ROL002">Staff</option>
                            <option value="ROL003">Customer</option>
                            <option value="ROL004">Shipper</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-save">Lưu thay đổi</button>
                </form>
            </div>
        </div>
        <c:import url="footer.jsp"/>
        <script>
            // Mở popup và điền thông tin người dùng
            function openEditPopup(accountId, roleId) {
                document.getElementById('editAccountId').value = accountId;
                document.getElementById('editRoleId').value = roleId;
                document.getElementById('editUserPopup').style.display = 'flex';
            }

            // Đóng popup
            document.querySelector('.close-btn').addEventListener('click', function () {
                document.getElementById('editUserPopup').style.display = 'none';
            });

            // Đóng popup khi click bên ngoài
            window.addEventListener('click', function (event) {
                if (event.target === document.getElementById('editUserPopup')) {
                    document.getElementById('editUserPopup').style.display = 'none';
                }
            });
        </script>
    </body>
</html>