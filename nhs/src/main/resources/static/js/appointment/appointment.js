// Get the model
var model = document.getElementById("myModel");

// Get the button that opens the model
var btn = document.getElementById("openaddappt");

// Get the <span> element that closes the model
var span = document.getElementsByClassName("popup-close")[0];

// Get the iframe element inside the model
var iframe = document.getElementById("modelFrame");

// When the user clicks the button, open the model and load the specific page
btn.onclick = function() {
    model.style.display = "block";
    iframe.src = "/addappointment"; // Replace with the URL you want to load
}

// When the user clicks on <span> (x), close the model
span.onclick = function() {
    model.style.display = "none";
    iframe.src = ""; // Clear the iframe content when the model is closed
}

// When the user clicks anywhere outside of the model, close it
window.onclick = function(event) {
    if (event.target == model) {
        model.style.display = "none";
        iframe.src = ""; // Clear the iframe content when the model is closed
    }
}
