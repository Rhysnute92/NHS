document.addEventListener("DOMContentLoaded", function () {
        fetch('/username').then(response => response.text())
        .then(data => {
        console.log("Fetched user name:", data);
        var span = document.getElementById('username');
        if ('textContent' in span) {
            span.textContent = data;
        } else {
            span.innerText = data;
        }
    })}
  );