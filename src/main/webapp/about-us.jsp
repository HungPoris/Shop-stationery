<%-- 
    Document   : about-us
    Created on : Oct 23, 2024, 11:45:19 PM
    Author     : anhkc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ICHIBOOKS| About us</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
        <link rel="stylesheet" href="css/styleHeaderFooter.css"/>
        <link rel="stylesheet" href="css/styleAboutUs_2.css"/>
    </head>
    <body>
        <c:import url="header.jsp"/>
        <section class="about-us">
            <h2>Giới thiệu</h2>
            <p>
                Chào mừng bạn đến với ICHIBOOKS! Chúng tôi tự hào mang đến một thế giới sách phong phú, đa dạng về thể loại dành cho mọi lứa tuổi. Đặc biệt, chúng tôi tập trung vào những khách hàng trẻ từ 16 đến 30 tuổi, với mong muốn giúp bạn khám phá và phát triển tri thức mỗi ngày. 
            </p>

            <h3>Sản phẩm và dịch vụ</h3>
            <ul>
                <li>Cung cấp sách đa dạng từ tiểu thuyết, sách giáo dục, đến sách đời sống và giải trí.</li>
                <li>Các chương trình khuyến mãi hấp dẫn và dịch vụ chăm sóc khách hàng tận tình.</li>
                <li>Dịch vụ giao hàng nhanh chóng trên toàn quốc.</li>
            </ul>

            <h3>Tầm nhìn</h3>
            <p>
                Trở thành nhà cung cấp sách uy tín và chất lượng, là lựa chọn hàng đầu của những người yêu sách tại Việt Nam.
            </p>

            <h3>Sứ mệnh</h3>
            <p>
                Góp phần nâng cao văn hóa đọc và truyền cảm hứng cho thế hệ trẻ thông qua việc cung cấp những đầu sách có giá trị, chất lượng cao.
            </p>

            <h3>Giá trị cốt lõi</h3>
            <p>
                Phát triển dịch vụ dựa trên sự hài lòng của khách hàng, với các sản phẩm chất lượng và chính sách bán hàng linh hoạt.
            </p>
        </section>
        <c:import url="footer.jsp"/>
        <!-- Bootstrap JavaScript Libraries -->
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
                integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
    </body>
</html>
