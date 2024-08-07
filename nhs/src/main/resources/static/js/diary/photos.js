const form = document.querySelector('.photo-form');
const input = document.querySelector('.photo-upload');

input.addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
    form.submit();
}