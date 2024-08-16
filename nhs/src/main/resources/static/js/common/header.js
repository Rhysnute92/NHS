// src/main/resources/static/js/header.js
document.addEventListener("DOMContentLoaded", function () {
  if (document.querySelector("title")) {
    const pageTitle = document.querySelector("title").innerText;
    const titleElement = document.getElementById("page-title");
    if (titleElement) {
      titleElement.innerText = pageTitle;
    }
  }

  const navMenu = document.querySelector(".navmenu");
  const navmenuToggle = document.querySelector(".navmenu-toggle");
  if (!navMenu) {
    navmenuToggle.style.opacity = 0;
  }


});


