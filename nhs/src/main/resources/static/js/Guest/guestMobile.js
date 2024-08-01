document.addEventListener('DOMContentLoaded', function() {
    const path = window.location.pathname;
    const base = path.split('/').pop();

    let fragmentId = '';
    if (base.includes('appointment')) {
        fragmentId = 'info1';
    } else if (base.includes('services')) {
        fragmentId = 'info2';
    } else if (base.includes('findUs')) {
        fragmentId = 'info3';
    }

    if (fragmentId) {
        document.querySelectorAll('.info-box').forEach(function(box) {
            box.style.display = 'none';
        });
        document.getElementById(fragmentId).style.display = 'block';
    }

});
function showPopup(imageSrc) {
    document.getElementById('popup-image').src = imageSrc;
    document.getElementById('popup').style.display = 'flex';
}

function hidePopup() {
    document.getElementById('popup').style.display = 'none';
}