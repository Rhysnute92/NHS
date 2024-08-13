function submitForm() {
    const url = form.action;

    const formData = new FormData(form);

    // Append each captured photo to the form data
    capturedPhotos.forEach((blob, index) => {
        formData.append(`photos[${index}].file`, blob);
        formData.append(`photos[${index}].bodyPart`, `Photo ${index + 1}`);
    });

    const gallery = document.querySelector('.photos-container');

    // Send the form data to the server
    fetch('/diary/photos', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(photos => {
            console.log(photos);
            photos.forEach(photo => {
                const photoComponent = document.createElement('photo-component');
                photoComponent.setAttribute('url', `/files/${photo.url}`);
                photoComponent.setAttribute('photo-id', photo.id);
                gallery.appendChild(photoComponent);
            });
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('An error occurred while submitting the form. Please try again.');
        });
}