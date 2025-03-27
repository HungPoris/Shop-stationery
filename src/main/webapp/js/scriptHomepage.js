document.addEventListener("DOMContentLoaded", function () {
    // Set the date we're counting down to
    var countDownDate = new Date();
    var countDownHour = countDownDate.getHours();
    var flashSaleNav = document.getElementById('flash-sale-nav');
    var hotDeadNav = document.getElementById("hot-deal-nav");

    //Flash sale starts at 9:00- 12:00 - 15:00 - 18:00 - 21:00
    showCountdown();
    if (countDownHour < 9 || countDownHour % 3 !== 0) {
        flashSaleNav.style.display = "none";
        hotDeadNav.style.display = "block";
        displayFeaturedBooks("#featuredBooks1");
//        flashSaleNav.style.display = "block";
//        displayFeaturedBooks("#featuredBooks2");
    } else {
        hotDeadNav.style.display = "none";
        flashSaleNav.style.display = "block";
        displayFeaturedBooks("#featuredBooks2");

    }




});

function hideCountdown() {
    document.querySelector('.time-counter').style.display = "none";
}


function showCountdown() {

    // Set the date we're counting down to
    var countDownDate = new Date();
    var countDownHour = countDownDate.getHours();

    //Flash sale starts at 9:00- 12:00 - 15:00 - 18:00 - 21:00
//    if (countDownHour < 11) {
    if (countDownHour < 9 || countDownHour % 3 !== 0) {
        return;
    }


    document.querySelector('.time-counter').style.display = "flex";

    //Flash sale lasts for 1 hour
    countDownDate.setHours(countDownDate.getHours() + 1, 0, 0);

    countDownDate = countDownDate.getTime();

    // Update the count down every 1 second
    var x = setInterval(function () {

        // Get today's date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        document.getElementById("hourID").innerHTML = hours.toString().padStart(2, "0");
        document.getElementById("minuteID").innerHTML = minutes.toString().padStart(2, "0");
        document.getElementById("secondID").innerHTML = seconds.toString().padStart(2, "0");

        // If the count down is over, hide the counter
        if (distance < 0) {
            clearInterval(x);
            document.querySelector('.time-counter').style.display = "none";
        }
    }, 1000);


}

function displayFeaturedBooks(referencedID) {
    var classes = ".fast-nav-container .nav-item .nav-link";
    removeActiveState(classes);
    setActiveState(referencedID, classes);

    var displayList = document.querySelectorAll('[id^="featuredBooks"');

    referencedID = referencedID + "";
    var index = referencedID.indexOf("featuredBooks");
    referencedID = referencedID.substr(index);
    var currentSection = document.getElementById(referencedID);
    currentSection.style.display = "flex";

    for (var i = 0; i < displayList.length; i++) {
        if (displayList[i].id !== referencedID) {
            displayList[i].style.display = "none";
        }
    }
}

function removeActiveState(classes) {
    document.querySelectorAll(classes).forEach(a => {
        if (a.classList.contains("active")) {
            a.classList.remove("active");
        }
    });
}

function setActiveState(referencedID, classes) {
    document.querySelectorAll(classes).forEach(a => {
        let href = a.getAttribute("href");
        href += "";
        if (referencedID.includes(href)) {
            a.classList.add("active");
        }
    });

}

function alertFlashSaleEnding(event) {
    var countDownDate = new Date();
    var countDownHour = countDownDate.getHours();
    if (countDownHour < 9 || countDownHour % 3 !== 0) {
//    if (countDownHour < 11 ) {
        event.preventDefault();
        alert("The flash sale has ended! Please refresh the page");
    }
}



