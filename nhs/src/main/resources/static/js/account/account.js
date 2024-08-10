document.addEventListener("DOMContentLoaded", function () {
        fetch('/userFullName').then(response => response.text())
        .then(data => {
        console.log("Fetched user name:", data);
        var span = document.getElementById('userFullname');
        if ('textContent' in span) {
            span.textContent = data;
        } else {
            span.innerText = data;
        }
    })}
  );