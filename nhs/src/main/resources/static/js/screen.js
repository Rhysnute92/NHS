(function() {
    const nav = document.querySelector('.nav');
    const header = document.querySelector('header');
    const footer = document.querySelector('footer');

    function moveNav() {
        if (window.innerWidth <= 767) {
            nav.classList.remove('desktop-nav');
            nav.classList.add('mobile-nav');
            if (!footer.contains(nav)) {
                footer.appendChild(nav);
            }
        } else {
            nav.classList.remove('mobile-nav');
            nav.classList.add('desktop-nav');
            if (!header.contains(nav)) {
                header.appendChild(nav);
            }
        }
    }

    moveNav();

    // Move navigation whenever the window is resized
    window.addEventListener('resize', moveNav);
})();
