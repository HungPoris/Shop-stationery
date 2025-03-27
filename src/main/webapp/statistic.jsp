<%@page import="java.util.Arrays"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Admin Statistic</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <c:import url="header.jsp"/>
    </head>
    <body class="bg-light">
        <div class="container mt-4">
            <h2 class="text-center mb-4">Statistics</h2>

            <c:choose>
                <c:when test="${sessionScope.statisticByMonth == null || sessionScope.statisticByYear == null}">
                    <div class="alert alert-warning text-center">There is no data yet.</div>
                </c:when>
                <c:otherwise>

                    <!-- Chọn kiểu biểu đồ -->
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div>
                            <label for="chartType" class="fw-bold">Select Chart Type:</label>
                            <select id="chartType" class="form-select">
                                <option value="month">${sessionScope.secondCeil[0]} By ${sessionScope.firstCeil[0]}</option>
                                <option value="year">${sessionScope.secondCeil[1]} By ${sessionScope.firstCeil[1]}</option>
                            </select>
                        </div>

                        <a href="ExportExcel" class="btn btn-success">
                            <i class="bi bi-file-earmark-excel-fill"></i> Export Excel
                        </a>
                    </div>

                    <!-- Biểu đồ -->
                    <div class="card shadow p-3 mb-4 bg-white rounded">
                        <canvas id="chart" width="400" height="200"></canvas>
                    </div>

                    <!-- Bảng dữ liệu -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="card shadow p-3 mb-4">
                                <h5 class="card-title text-center">Statistic By Month</h5>
                                <table class="table table-bordered table-hover text-center">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>${sessionScope.firstCeil[0]}</th>
                                            <th>${sessionScope.secondCeil[0]}</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="statistic" items="${sessionScope.statisticByMonth}">
                                            <tr>
                                                <td>${statistic.key}</td>
                                                <td class="statistic-month">${statistic.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="card shadow p-3 mb-4">
                                <h5 class="card-title text-center">Statistic By Year</h5>
                                <table class="table table-bordered table-hover text-center">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>${sessionScope.firstCeil[1]}</th>
                                            <th>${sessionScope.secondCeil[1]}</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="statistic" items="${sessionScope.statisticByYear}">
                                            <tr>
                                                <td>${statistic.key}</td>
                                                <td class="statistic-year">${statistic.value}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </c:otherwise>
            </c:choose>
        </div>

        <c:import url="footer.jsp"/>

        <!-- JavaScript Biểu Đồ -->
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                function formatPrice(selector) {
                    document.querySelectorAll(selector).forEach(priceElement => {
                        let price = parseFloat(priceElement.innerText.replace(/\D/g, "")) / 10;
                        if (!isNaN(price)) {
                            priceElement.innerText = price.toLocaleString("vi-VN") + " VNĐ";
                        }
                    });
                }

                formatPrice(".statistic-month");
                formatPrice(".statistic-year");
            });

            const revenueByMonthJson = ${jsonStatisticByMonth};
            const revenueByYearJson = ${jsonStatisticByYear};

            // Sắp xếp dữ liệu
            const labelsMonth = Object.keys(revenueByMonthJson).sort((a, b) => {
                let [monthA, yearA] = a.split("-").map(Number);
                let [monthB, yearB] = b.split("-").map(Number);
                return yearA === yearB ? monthA - monthB : yearA - yearB;
            });
            const dataMonth = labelsMonth.map(label => revenueByMonthJson[label]);

            const labelsYear = Object.keys(revenueByYearJson).sort((a, b) => Number(a) - Number(b));
            const dataYear = labelsYear.map(label => revenueByYearJson[label]);

            // Khởi tạo biểu đồ mặc định
            const ctx = document.getElementById('chart').getContext('2d');
            let myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labelsMonth,
                    datasets: [{
                            label: '${sessionScope.secondCeil[0]} By ${sessionScope.firstCeil[0]}',
                                                data: dataMonth,
                                                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                                borderColor: 'blue',
                                                borderWidth: 3,
                                                pointBackgroundColor: 'blue',
                                                pointRadius: 5,
                                                pointHoverRadius: 7
                                            }]
                                    },
                                    options: {
                                        scales: {
                                            y: {beginAtZero: true}
                                        },
                                        responsive: true,
                                        maintainAspectRatio: false
                                    }
                                });

                                // Hàm cập nhật biểu đồ
                                function updateChart() {
                                    let selectedType = document.getElementById("chartType").value;
                                    let labels, datasets;

                                    if (selectedType === "month") {
                                        labels = labelsMonth;
                                        datasets = [{
                                                label: '${sessionScope.secondCeil[0]} By ${sessionScope.firstCeil[0]}',
                                                                    data: dataMonth,
                                                                    backgroundColor: 'rgba(54, 162, 235, 0.2)',
                                                                    borderColor: 'blue',
                                                                    borderWidth: 3,
                                                                    pointBackgroundColor: 'blue',
                                                                    pointRadius: 5,
                                                                    pointHoverRadius: 7
                                                                }];
                                                        } else if (selectedType === "year") {
                                                            labels = labelsYear;
                                                            datasets = [{
                                                                    label: '${sessionScope.secondCeil[1]} By ${sessionScope.firstCeil[1]}',
                                                                                        data: dataYear,
                                                                                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                                                                                        borderColor: 'red',
                                                                                        borderWidth: 3,
                                                                                        pointBackgroundColor: 'red',
                                                                                        pointRadius: 5,
                                                                                        pointHoverRadius: 7
                                                                                    }];
                                                                            }

                                                                            // Cập nhật biểu đồ
                                                                            myChart.data.labels = labels;
                                                                            myChart.data.datasets = datasets;
                                                                            myChart.update();
                                                                        }

                                                                        // Sự kiện thay đổi dropdown
                                                                        document.getElementById("chartType").addEventListener("change", updateChart);
                                                                        // Gọi cập nhật lần đầu tiên
                                                                        updateChart();
        </script>
    </body>
</html>