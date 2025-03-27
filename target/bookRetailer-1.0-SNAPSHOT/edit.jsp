<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Edit Product</title>
        <link rel="stylesheet" href="css/edit.css">
    </head>
    <body>

        <!-- Header -->
        <div class="header-child">
            <a href="ListServlet">Products</a>
        </div>

        <!-- Title -->
        <div class="title">
            <h2>Edit Product</h2>
        </div>

        <!-- Form Container -->
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
            <form action="Edit" method="post" id="f1" class="edit-form" enctype="multipart/form-data">

                <table>
                    <tr>
                        <td><label for="txtID">ID</label></td>
                        <td><input value="${product.bookID}" id="txtID" type="text" name="txtID" readonly></td>
                    </tr>
                    <tr>
                        <td><label for="txtTitle">Title</label></td>
                        <td><input value="${product.bookTitle}" id="txtTitle" type="text" name="txtTitle" required></td>
                    </tr>
                    <tr>
                        <td><label for="txtCover">Image</label></td>
                        <td>
                            <!-- Display the current book cover image if it exists -->
                            <!-- File input for uploading a new image -->
                            <input id="txtCover" type="file" value="${product.bookCover}" accept="image/png, image/gif, image/jpeg" name="txtCover">
                            <img id="currentCover" src="${product.bookCover}" alt="Current Book Cover" style="max-width: 100px; max-height: 100px;">
                        </td>

                    </tr>
                    <tr>
                        <td><label for="txtVersion">Book Version</label></td>
                        <td><input value="${product.bookVersion}" id="txtVersion" type="text" name="txtVersion" required></td>
                    </tr>

                    <!-- Publisher -->
                    <c:if test="${product.publisherID != 'PUB015'}">
                        <tr>
                            <td><label>Publisher:</label></td>
                            <td>
                                <select name="txtPublisherID">
                                    <c:forEach var="pub" items="${publishers}">
                                        <option value="${pub.publisherID}" ${product.publisherID == pub.publisherID ? 'selected' : ''}>${pub.publisherName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${product.publisherID == 'PUB015'}">
                        <input value="${product.publisherID}" id="txtPublisherID" type="hidden" name="txtPublisherID" required>
                    </c:if>

                    <!-- Categories -->
                    <tr>
                        <td><label>Categories:</label></td>
                        <td>
                            <select id="txtCategoryID" name="txtCategoryID" multiple required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryID}" 
                                            ${fn:contains(selectedCategories, cat.categoryID) ? 'selected' : ''}>
                                        ${cat.categoryName}
                                    </option>
                                </c:forEach>
                            </select>

                        </td>
                    </tr>

                    <!-- Dates -->
                    <tr>
                        <td><label for="txtPublishDate">Publish Date</label></td>
                        <td><input value="${product.bookPublishDate}" id="txtPublishDate" type="date" name="txtPublishDate" required></td>
                    </tr>
                    <tr>
                        <td><label for="txtImportDate">Import Date</label></td>
                        <td><input value="${product.bookImportDate}" id="txtImportDate" type="date" name="txtImportDate" required></td>
                    </tr>

                    <!-- Introduction -->
                    <tr>
                        <td><label for="txtIntro">Introduction</label></td>
                        <td><textarea id="txtIntro" name="txtIntro" required>${product.bookIntro}</textarea></td>
                    </tr>

                    <!-- Quantity & Price -->
                    <tr>
                        <td><label for="txtQuantity">Quantity</label></td>
                        <td><input value="${product.bookQuantity}" id="txtQuantity" type="number" name="txtQuantity" required></td>
                    </tr>
                    <tr>
                        <td><label for="txtPrice">Price</label></td>
                        <td><input value="${product.bookPrice}" id="txtPrice" type="number" name="txtPrice" required></td>
                    </tr>

                    <!-- Discounts -->
                    <tr>
                        <td><label for="txtDiscount">Discount</label></td>
                        <td><input value="${product.bookDiscount}" id="txtDiscount" type="number" name="txtDiscount" required></td>
                    </tr>
                    <tr>
                        <td><label for="txtFlashsale">Flashsale</label></td>
                        <td><input value="${product.bookFlashSale}" id="txtFlashsale" type="number" name="txtFlashsale" required></td>
                    </tr>
                </table>

                <!-- Buttons -->
                <div class="button-group">
                    <button class="btn btn-primary" type="submit" name="btnSave" value="Save" form="f1">Save</button>
                    <a class="btn btn-danger" href="ListServlet">Back to List</a>
                </div>
            </form>
        </div>

        <!-- JavaScript -->
        <script>
            document.getElementById('txtQuantity').addEventListener('input', function () {
                const importDateInput = document.getElementById('txtImportDate');
                const today = new Date();
                const formattedDate = today.toISOString().split('T')[0]; // Format: yyyy-MM-dd
                importDateInput.value = formattedDate;
            });
        </script>

    </body>
</html>
