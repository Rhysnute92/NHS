(function() {
    const nav = document.querySelector('.navbar');
    const header = document.querySelector('header');
    const footer = document.querySelector('footer');

    function moveNav() {
        if (window.innerWidth <= 768) {
            if (!footer.contains(nav)) {
                footer.appendChild(nav);
            }
        } else {
            if (!header.contains(nav)) {
                header.appendChild(nav);
            }
        }
    }

    moveNav();

    // Move navigation whenever the window is resized
    window.addEventListener('resize', moveNav);
})();
