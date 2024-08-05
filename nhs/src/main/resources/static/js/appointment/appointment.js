// Get the modal
var modal = document.getElementById("myModel");

// Get the button that opens the modal
var btn = document.getElementById("openaddappt");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("popup-close")[0];

// Get the iframe element inside the modal
var iframe = document.getElementById("modelFrame");

// When the user clicks the button, open the modal and load the specific page
btn.onclick = function() {
    modal.style.display = "block";
    iframe.src = "/addappointment"; // Replace with the URL you want to load
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
    iframe.src = ""; // Clear the iframe content when the modal is closed
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        iframe.src = ""; // Clear the iframe content when the modal is closed
    }
}
