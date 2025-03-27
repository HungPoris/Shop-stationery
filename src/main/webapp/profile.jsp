<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thông tin người dùng</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    </head>

    <body>
        <c:import url="header.jsp"/>
        <div class="container mt-4">
            <h1 class="text-center mb-4">Thông tin người dùng</h1>
            <form action="ShowProfileServlet" method="post" class="card shadow-sm p-4">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Họ:</label>
                        <input type="text" class="form-control" name="firstName" value="${customer.firstName}" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Tên:</label>
                        <input type="text" class="form-control" name="lastName" value="${customer.lastName}" required />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Email:</label>
                    <input type="email" class="form-control" name="email" value="${customer.email}" required />
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Số điện thoại:</label>
                        <input type="tel" class="form-control" name="phoneNumber" value="${customer.phoneNumber}" required />
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ngày sinh:</label>
                        <input type="date" class="form-control" name="birthDate" 
                               value="<fmt:formatDate value='${customer.birthDate}' pattern='yyyy-MM-dd' />" required />
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">Vai trò:</label>
                    <input type="text" class="form-control" name="roleName" value="${roleName}" readonly />
                </div>

                <div class="mb-3">
                    <label class="form-label">Danh sách địa chỉ:</label>
                    <div class="list-group">
                        <c:choose>
                            <c:when test="${not empty customer.addressList}">
                                <c:forEach var="address" items="${customer.addressList}" varStatus="status">
                                    <div class="list-group-item d-flex justify-content-between align-items-center">
                                        <!-- Truyền ID địa chỉ -->
                                        <input type="hidden" name="addressID" value="${address.addressID}" />

                                        <!-- Chỉnh sửa địa chỉ -->
                                        <input type="text" class="form-control w-75" name="addressDetail" value="${address.addressDetail}" required />

                                        <!-- Chọn mặc định (chỉ 1 địa chỉ) -->
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" id="defaultAddress${address.addressID}" 
                                                   name="defaultAddress" value="${address.addressID}" ${address.defaultAddress ? 'checked' : ''} required />
                                            <label class="form-check-label me-5" for="defaultAddress${address.addressID}">Mặc định</label>

                                            <!-- Nút xóa -->
                                            <button type="submit" class="btn btn-danger btn-md" name="deleteAddress" value="${address.addressID}" onclick="return confirm('Bạn có chắc muốn xóa địa chỉ này?');">
                                                Delete
                                            </button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p class="text-muted">Không có địa chỉ nào.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Thêm địa chỉ mới:</label>
                    <input type="text" class="form-control" name="newAddress" placeholder="Nhập địa chỉ mới" />
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary px-4">
                        <i class="fas fa-save"></i> Cập nhật
                    </button>
                </div>
            </form>
        </div>
        <c:import url="footer.jsp"/>
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </body>
</html>
