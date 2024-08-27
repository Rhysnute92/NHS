function openModal() {
    document.getElementById('myModal').style.display = "block";
}

function closeModal() {
    document.getElementById('myModal').style.display = "none";
}

// Close the modal when the user clicks outside of it
window.onclick = function(event) {
    let modal = document.getElementById('myModal');
    if (event.target === modal) {
        modal.style.display = "none";
    }
    document.getElementById("add-appt-btn").addEventListener("click", function () {
        document.getElementById("appointment-form").style.display = "block";
    });
}