// Function to open the modal
function openModal() {
    var modal = document.getElementById("myModal");
    var modalContent = document.getElementById("modalContent");

    // Show the modal
    modal.style.display = "block";

    // Attach close functionality to the newly loaded modal
    var closeBtn = document.getElementsByClassName("close")[0];
    closeBtn.onclick = function() {
        modal.style.display = "none";
    };

    // Close modal when clicking outside the modal content
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
}

// Event listener for the button
document.getElementById("openaddappt").addEventListener("click", openModal);