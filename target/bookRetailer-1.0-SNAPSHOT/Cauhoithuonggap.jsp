<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Frequently Asked Questions</title>
        <meta charset="UTF-8">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                color: #333;
                margin: 0;
                padding: 20px;
            }
            .container {
                max-width: 800px;
                margin: auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h1 {
                text-align: center;
                color: #333;
            }
            .faq-section {
                margin: 20px 0;
            }
            .faq-question {
                font-weight: bold;
                margin: 10px 0;
                cursor: pointer;
            }
            .faq-answer {
                display: none;
                margin: 10px 0 20px;
                padding-left: 20px;
                border-left: 2px solid #ddd;
            }
        </style>
        <script>
            function toggleAnswer(id) {
                var answer = document.getElementById(id);
                if (answer.style.display === "none" || answer.style.display === "") {
                    answer.style.display = "block";
                } else {
                    answer.style.display = "none";
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Câu Hỏi Thường Gặp</h1>
            <div class="faq-section">
                <div class="faq-question" onclick="toggleAnswer('answer1')">Câu hỏi 1: Làm thế nào để tạo tài khoản?</div>
                <div class="faq-answer" id="answer1">
                    Trả lời: Để tạo tài khoản, bạn nhấp vào nút "Đăng ký" trên trang chủ và điền thông tin cần thiết.
                </div>
            </div>
            <div class="faq-section">
                <div class="faq-question" onclick="toggleAnswer('answer2')">Câu hỏi 2: Làm thế nào để đặt hàng?</div>
                <div class="faq-answer" id="answer2">
                    Trả lời: Để đặt hàng, bạn chọn sản phẩm muốn mua, thêm vào giỏ hàng và tiến hành thanh toán.
                </div>
            </div>
            <div class="faq-section">
                <div class="faq-question" onclick="toggleAnswer('answer3')">Câu hỏi 3: Chính sách đổi trả như thế nào?</div>
                <div class="faq-answer" id="answer3">
                    Trả lời: Bạn có thể xem chi tiết chính sách đổi trả tại trang "Chính sách trả hàng và hoàn tiền".
                </div>
            </div>
        </div>
    </body>
</html>
