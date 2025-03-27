//document.addEventListener("DOMContentLoaded", function () {
//    document.querySelectorAll("a.nav-link.active").forEach(li => {
//        li.classList.remove("active");
//        li.attributes.removeNamedItem("aria-current");
//    });
//
//    document.querySelectorAll(`a[href="${location.pathname}"].nav-link`).forEach(a => {
//        a.classList.add("active");
//        a.setAttribute("aria-current", "page");
//    });
//
//});
//document.addEventListener("DOMContentLoaded", function () {
//    document.querySelectorAll(".header-navbar-main .nav-item a.nav-link.active").forEach(li => {
//        li.classList.remove("active");
//        li.attributes.removeNamedItem("aria-current");
//    });
//
//    document.querySelectorAll(`a[href="${location.pathname}"].nav-link`).forEach(a => {
//        a.classList.add("active");
//        a.setAttribute("aria-current", "page");
//    });
//
//});


//
//document.addEventListener("DOMContentLoaded", function () {
//    document.querySelectorAll("a.nav-link.active").forEach(li => {
//        li.classList.remove("active");
//        li.attributes.removeNamedItem("aria-current");
//    });
//    // Get the current hash from the URL
//    let currentHash = location.hash || "#featuredBooks1";  // Default to #featuredBooks1 if no hash
//
//    // Log the current hash for debugging
//    console.log("Current Hash:", currentHash);
//    console.log("Current Pathname:", location.pathname);
//
//    // Add active class to the nav link that matches the current hash
//    document.querySelectorAll("a.nav-link").forEach(a => {
//        let href = a.getAttribute("href");
//
//        console.log("Link Href:", href);
//
//        if (href === currentHash || href === location.pathname) {
//            a.classList.add("active");
//            a.setAttribute("aria-current", "page");
//        }
//    });
//
//    
//});

//document.addEventListener("DOMContentLoaded", function () {
//    // Function to remove active class and aria-current from all links
//    function clearActiveState(link) {
//        document.querySelectorAll(link).forEach(li => {
//            li.classList.remove("active");
//            li.attributes.removeNamedItem("aria-current");
//        });
//    }
//    
//    let link = "a.nav-link.active";
//    clearActiveState(link);
//
//    // Get the current hash from the URL or default to #featuredBooks1
////    let currentHash = location.hash || "#featuredBooks1";
//    let currentHash = location.hash;
//
//    // Log the current hash for debugging
//    console.log("Current Hash:", currentHash);
//    console.log("Current Pathname:", location.pathname);
//
//    // Add active class to the nav link that matches the current hash on page load
//    document.querySelectorAll("a.nav-link").forEach(a => {
//        let href = a.getAttribute("href");
//
//        if (href === location.pathname) {
//            a.classList.add("active");
//            a.setAttribute("aria-current", "page");
//        }
//        
//        else if(href === currentHash){
//             a.classList.add("active");
//        }
//
//        // Add an onclick event to each nav-link to change the active state dynamically
//        a.onclick = function () {
//            // Clear previous active states
////            clearActiveState(".fast-nav-container ul li a.nav-link.active");
//            document.querySelectorAll(".fast-nav-container ul li a.nav-link.active").forEach(li => {
//            li.classList.remove("active");
////            li.attributes.removeNamedItem("aria-current");
//        });
//
//            // Add active state to the clicked link
//            a.classList.add("active");
////            a.setAttribute("aria-current", "true");
//        };
//    });
//});















