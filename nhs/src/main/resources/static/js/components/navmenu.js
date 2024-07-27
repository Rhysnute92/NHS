const navmenu = document.querySelector('.navmenu');
const navmenuToggle = document.querySelector('.navmenu-toggle');
const body = document.body;
const mobileBreakpoint = 768;

function toggleNavmenu() {
    // Only toggle the visibility and prevent scroll if we're below the breakpoint
    if (window.innerWidth <= mobileBreakpoint) {
        navmenu.classList.toggle('visible');
        body.classList.toggle('navmenu-open');
        body.classList.toggle('no-scroll');
    }
}

function handleResize() {
    // Reset menu state and body classes above the breakpoint
    if (window.innerWidth > mobileBreakpoint) {
        navmenu.classList.remove('visible');
        body.classList.remove('navmenu-open');
        body.classList.remove('no-scroll');
    }
}

navmenuToggle.addEventListener('click', toggleNavmenu);

document.addEventListener('click', (event) => {
    if (window.innerWidth <= mobileBreakpoint && navmenu.classList.contains('visible')) {
        if (!navmenu.contains(event.target) && !navmenuToggle.contains(event.target)) {
            toggleNavmenu();
        }
    }
});

body.addEventListener('touchmove', (evt) => {
    if (window.innerWidth <= mobileBreakpoint && navmenu.classList.contains('visible') && !navmenu.contains(evt.target)) {
        evt.preventDefault();
    }
}, { passive: false });

window.addEventListener('resize', handleResize);

handleResize();