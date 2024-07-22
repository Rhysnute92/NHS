const navmenu = document.querySelector('.navmenu');
const navmenuToggle = document.querySelector('.navmenu-toggle');

navmenuToggle.addEventListener('click', () => {
    console.log('click')
    if (navmenu.style.display === 'block') {
        navmenu.style.display = 'none';
    } else {
        navmenu.style.display = 'block';
    }
});