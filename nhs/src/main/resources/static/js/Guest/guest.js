document.addEventListener('DOMContentLoaded', function() {
    function showInfo(infoId) {

        document.querySelectorAll('.info-box').forEach(function(box) {
            box.style.display = 'none';
        });

        var selectedBox = document.getElementById(infoId);
        if (selectedBox) {
            selectedBox.style.display = 'block';
        }
    }

    document.getElementById('show-info1').addEventListener('click', function() {
        showInfo('info1');
    });

    document.getElementById('show-info2').addEventListener('click', function() {
        showInfo('info2');
    });

    document.getElementById('show-info3').addEventListener('click', function() {
        showInfo('info3');
    });

    showInfo('info1');
});

let slideIndex = 0;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function showSlides(n) {
    let slides = document.getElementsByClassName("slide");
    if (n >= slides.length) { slideIndex = 0 }
    if (n < 0) { slideIndex = slides.length - 1 }
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex].style.display = "block";
}

function toggleDropdown(contentId) {
    var content = document.getElementById(contentId);
    var parent = content.parentNode;

    if (content.style.display === "block") {
        content.style.display = "none";
        parent.classList.remove('active');
    } else {
        content.style.display = "block";
        parent.classList.add('active');
    }
}
