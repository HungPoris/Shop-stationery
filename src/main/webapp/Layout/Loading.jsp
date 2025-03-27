<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
    #loading-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.7);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 9999;
    }

    .loader {
        position: relative;
        width: 150px;
        height: 150px;
    }

    .circle {
        position: absolute;
        border: 3px solid transparent;
        border-radius: 50%;
        animation: rotate linear infinite, colorChange 3s infinite;
    }

    .circle.one {
        height: 50px;
        width: 50px;
        left: 50px;
        top: 50px;
        animation: rotate 0.85s linear infinite, colorChange1 3s infinite;
    }

    .circle.two {
        height: 75px;
        width: 75px;
        top: 38px;
        left: 38px;
        animation: rotate 0.95s linear infinite, colorChange2 3s infinite;
    }

    .circle.three {
        height: 100px;
        width: 100px;
        top: 25px;
        left: 25px;
        animation: rotate 1.05s linear infinite, colorChange3 3s infinite;
    }

    @keyframes rotate {
        from {
            transform: rotate(0deg);
        }
        to {
            transform: rotate(360deg);
        }
    }

    @keyframes colorChange1 {
        0% {
            border-top-color: #4CAF50;
        }
        33% {
            border-top-color: #2196F3;
        }
        66% {
            border-top-color: #9C27B0;
        }
        100% {
            border-top-color: #4CAF50;
        }
    }

    @keyframes colorChange2 {
        0% {
            border-top-color: #2196F3;
        }
        33% {
            border-top-color: #9C27B0;
        }
        66% {
            border-top-color: #4CAF50;
        }
        100% {
            border-top-color: #2196F3;
        }
    }

    @keyframes colorChange3 {
        0% {
            border-top-color: #9C27B0;
        }
        33% {
            border-top-color: #4CAF50;
        }
        66% {
            border-top-color: #2196F3;
        }
        100% {
            border-top-color: #9C27B0;
        }
    }

    /* Thêm hiệu ứng glow */
    .circle::after {
        content: '';
        position: absolute;
        top: -5px;
        left: -5px;
        right: -5px;
        bottom: -5px;
        border-radius: 50%;
        animation: glowEffect 3s infinite;
        filter: blur(10px);
        z-index: -1;
    }

    @keyframes glowEffect {
        0% {
            box-shadow: 0 0 10px rgba(76, 175, 80, 0.3);
        }
        33% {
            box-shadow: 0 0 10px rgba(33, 150, 243, 0.3);
        }
        66% {
            box-shadow: 0 0 10px rgba(156, 39, 176, 0.3);
        }
        100% {
            box-shadow: 0 0 10px rgba(76, 175, 80, 0.3);
        }
    }
</style>

<div id="loading-overlay">
    <div class="loader">
        <div class="circle one"></div>
        <div class="circle two"></div>
        <div class="circle three"></div>
    </div>
</div>

<script>
    (function () {
        if (window.loadingScriptExecuted)
            return;
        window.loadingScriptExecuted = true;

        function hideLoading() {
            var loader = document.getElementById('loading-overlay');
            if (loader) {
                loader.style.opacity = '0';
                loader.style.transition = 'opacity 0.5s ease';
                setTimeout(() => {
                    loader.style.display = 'none';
                }, 500);
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            setTimeout(hideLoading, 3000);
        });

        if (document.readyState === 'complete') {
            setTimeout(hideLoading, 3000);
        }
    })();
</script>