<%-- 
    Document   : Create
    Created on : Oct 28, 2024, 11:52:44 PM
    Author     : conkg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/edit.css">
    </head>
    <body>
        <c:import url="header.jsp"/>
        <div class="header-child">
            <a href="ListServlet">Products</a>
        </div>
    </div>

    <div class="title">
        <h2>Add Product</h2>
    </div>

    <div class="form-container">
        <div id="google_translate_element" style="position: absolute; top: 10px; right: 10px;"></div>
        <script type="text/javascript">
            function googleTranslateElementInit() {
                new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
            }
        </script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <script>
            document.getElementById('languageSelector').addEventListener('change', function () {
                let lang = this.value;
                let textElements = document.querySelectorAll('[data-translate]');

                textElements.forEach(el => {
                    let text = el.innerText;
                    fetch(`https://translation.googleapis.com/language/translate/v2?key=YOUR_API_KEY&q=${text}&target=${lang}`)
                            .then(response => response.json())
                            .then(data => {
                                if (data.data && data.data.translations.length > 0) {
                                    el.innerText = data.data.translations[0].translatedText;
                                }
                            });
                });
            });
        </script>
        <c:set var="product" value="${requestScope.product}"/>
        <form action="Create" method="post" id="f1" class="edit-form" enctype="multipart/form-data">
            <table>
                <tr>
                    <td><label for="isBook">Is Book?</label></td>
                    <td>
                        <input type="checkbox" id="isBook" name="isBook" onchange="toggleAuthorSection()">
                    </td>
                </tr>
                <tr>
                    <td><label for="txtID">ID</label></td>
                    <td><input value="${product.bookID}" id="txtID" type="text" name="txtID" required ></td>
                </tr>
                <tr>
                    <td><label for="txtTitle">Title</label></td>
                    <td><input value="${product.bookTitle}" id="txtTitle" type="text" name="txtTitle" required></td>
                </tr>
                <tr>
                    <td><label for="txtCover">Image</label></td>
                    <td><input id="txtCover" type="file"  accept="image/png, image/gif, image/jpeg" name="txtCover"></td>
                </tr>
                <tr>
                    <td><label for="txtVersion">Book Version</label></td>
                    <td><input value="${product.bookVersion}" id="txtVersion" type="text" name="txtVersion" required></td>
                </tr>
                <tr id="authorSection" style="display: none;">
                    <td><label for="txtPublisherID">Publisher</label></td>
                    <td>
                        <select id="txtPublisherID" name="txtPublisherID" required>
                            <c:forEach var="pub" items="${pushlishers}">
                                <option value="${pub.publisherID}" ${pub.publisherID == product.publisherID ? 'selected' : ''}>
                                    ${pub.publisherName}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td><label for="txtCategoryID">Categories</label></td>
                    <td>
                        <select id="txtCategoryID" name="txtCategoryID" multiple required>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryID}"
                                        <c:forEach var="selectedCat" items="${product.categories}">
                                            ${selectedCat.categoryID == cat.categoryID ? 'selected' : ''}
                                        </c:forEach>
                                        >
                                    ${cat.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="txtPublishDate">Publish Date</label></td>
                    <td><input value="${product.bookPublishDate}" id="txtPublishDate" type="date" name="txtPublishDate" required></td>
                </tr>
                <tr>
                    <td><label for="txtImportDate">Import Date</label></td>
                    <td><input value="${product.bookImportDate}" id="txtImportDate" type="date" name="txtImportDate" required></td>
                </tr>
                <tr>
                    <td><label for="txtIntro">Introduction</label></td>
                    <td><textarea id="txtIntro" name="txtIntro" required>${product.bookIntro}</textarea></td>
                </tr>
                <tr>
                    <td><label for="txtQuantity">Quantity</label></td>
                    <td><input value="${product.bookQuantity}" id="txtQuantity" type="number" name="txtQuantity" required></td>
                </tr>
                <tr>
                    <td><label for="txtPrice">Price</label></td>
                    <td><input value="${product.bookPrice}" id="txtPrice" type="number" name="txtPrice" required></td>
                </tr>
                <tr>
                    <td><label for="txtDiscount">Discount</label></td>
                    <td><input value="${product.bookDiscount}" id="txtDiscount" type="number" name="txtDiscount" required></td>
                </tr>
                <tr>
                    <td><label for="txtFlashsale">Flashsale</label></td>
                    <td><input value="${product.bookFlashSale}" id="txtFlashsale" type="number" name="txtFlashsale" required></td>
                </tr>
            </table>
            <div class="button-group">
                <button class="btn btn-primary" type="submit" name="btnSave" value="Save" form="f1">Save</button>
                <a class="btn btn-danger" href="ListServlet">Back to List</a>
            </div>
        </form>
        <c:import url="footer.jsp"/>
    </div>
    <script>
        function toggleAuthorSection() {
            var checkBox = document.getElementById("isBook");
            var authorSection = document.getElementById("authorSection");
            authorSection.style.display = checkBox.checked ? "table-row" : "none";
        }
        document.getElementById("f1").addEventListener("submit", function (event) {
            var idField = document.getElementById("txtID");
            var quantityField = document.getElementById("txtQuantity");
            var priceField = document.getElementById("txtPrice");
            var discountField = document.getElementById("txtDiscount");
            var flashSaleField = document.getElementById("txtFlashsale");

            var enteredID = idField.value.trim();
            var existingIDs = [/* Danh sách ID đã tồn tại, có thể lấy từ server qua AJAX */];

            if (parseInt(enteredID) <= 0 || existingIDs.includes(enteredID)) {
                alert("ID không được âm hoặc trùng với ID đã tồn tại!");
                event.preventDefault();
                return;
            }

            if (parseInt(quantityField.value) <= 0) {
                alert("Số lượng phải lớn hơn 0!");
                event.preventDefault();
                return;
            }

            if (parseFloat(priceField.value) <= 0) {
                alert("Giá phải lớn hơn 0!");
                event.preventDefault();
                return;
            }

            if (parseFloat(discountField.value) < 0) {
                alert("Giảm giá không được âm!");
                event.preventDefault();
                return;
            }

            if (parseFloat(flashSaleField.value) < 0) {
                alert("Flash Sale không được âm!");
                event.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>
