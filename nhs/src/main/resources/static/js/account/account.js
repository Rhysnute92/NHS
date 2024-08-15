function setSpanText(text, span){
    if ('textContent' in span) {
        span.textContent = text;
    } else {
        span.innerText = text;
    }
}

document.addEventListener("DOMContentLoaded", function () {
        fetch('/username').then(response => response.text())
        .then(data => {
        console.log("Fetched user name:", data);
        var element = document.getElementById('username');
        setSpanText(data, element);
    })}
  );

  document.addEventListener("DOMContentLoaded", function () {
    fetch('/patientProfile').then(response => response.json())
    .then(data => {
    setSpanText(data.fullName, document.getElementById('userFullname'));
    setSpanText(data.nhsNumber, document.getElementById('userNHS'));
    setSpanText(data.email, document.getElementById('userEmail'));
    setSpanText(data.mobile, document.getElementById('userPhone'));
    setSpanText(data.dateOB, document.getElementById('userDOB'));
    setSpanText(data.age, document.getElementById('userAge'));
    setSpanText(data.clinic, document.getElementById('userClinic'));
})}
);

