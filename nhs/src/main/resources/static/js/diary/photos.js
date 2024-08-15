(function () {
    const photoCaptureElement = document.querySelector('camera-component');
    const photosContainer = document.querySelector('photo-container');

    photoCaptureElement.addEventListener('photos-confirmed', function(event) {
        const form = document.querySelector('.photos-form');

        const formData = new FormData(form);

        const capturedPhotos = event.detail;

        // Append each captured photo to the form data
        capturedPhotos.forEach((blob, index) => {
            formData.append(`photos[${index}].file`, blob);
            formData.append(`photos[${index}].bodyPart`, `Photo ${index + 1}`);
        });

        // Send the form data to the server
        fetch('/diary/photos', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(photos => {
                console.log(photos);
                photos.forEach(photo => {
                    let currentPhotos = JSON.parse(photosContainer.getAttribute('photos') || '[]');

                    // Add the new photo to the array
                    currentPhotos.push(photo);

                    // Update the photos attribute with the new array
                    photosContainer.setAttribute('photos', JSON.stringify(currentPhotos));
                });
            })
            .catch((error) => {
                console.error('Error:', error);
                alert('An error occurred while submitting the form. Please try again.');
            });
    });
})();