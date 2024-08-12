// Get modal elements
const modal = document.getElementById("myModel");
const openBtn = document.getElementById("openaddappt");
const closeBtn = document.querySelector(".popup-close");
const iframe = document.getElementById("modelFrame");

// When the user clicks the button, open the modal
openBtn.addEventListener("click", function() {
    iframe.src = "/addappointment"; // Set the iframe src to the form URL
    modal.style.display = "block";
});

// When the user clicks on the close button (x), close the modal
closeBtn.addEventListener("click", function() {
    modal.style.display = "none";
    iframe.src = ""; // Clear the iframe src to stop any loaded content
});

// When the user clicks anywhere outside of the modal content, close it
window.addEventListener("click", function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
        iframe.src = ""; // Clear the iframe src to stop any loaded content
    }
});
