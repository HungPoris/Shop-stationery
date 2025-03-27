<%-- 
    Document   : report
    Created on : Mar 26, 2025, 9:43:24 PM
    Author     : Tran Viet Minh - CE182526
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Feedback Form</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="container mt-5">
            <h5>Hãy góp ý để chúng mình cải thiện nhằm đem lại một trải nghiệm tuyệt vời hơn<span class="text-danger">*</span></h5>
            <form action="AddReportServlet" method="post" id="f1" class="edit-form" enctype="multipart/form-data">
                <div class="mb-3">
                    <textarea class="form-control" name="suggestion" placeholder="Your suggestion matters." maxlength="1000" required></textarea>
                </div>
                <p>Upload images to help us understand more.<span class="text-danger">*</span></p>
                <div class="mb-3 border border-secondary p-4 text-center">
                    <input id="txtCover" type="file"  accept="image/png, image/gif, image/jpeg" name="txtCover">
                    <p class="text-muted">Supported types: JPG, PNG, and GIF.</p>
                </div>
                <button type="submit" class="btn btn-danger w-100">Submit</button>
            </form>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                var errorType = "${ERROR_TYPE}";
                var errorMessage = "${ERROR_MESSAGE}";

                if (errorType && errorMessage) {
                    Swal.fire({
                        title: "Thông báo",
                        text: errorMessage,
                        icon: "error",
                        toast: true,
                        position: "top-end",
                        showConfirmButton: false,
                        timer: 5000,
                        timerProgressBar: true,
                        background: "#ff204e",
                        color: "#ffffff"
                    });
                }
            });
        </script>
        <c:import url="footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
