(function () {
    let checkinSections = document.querySelectorAll('.checkin-section');
    checkinSections.forEach((section) => {
        const title = section.querySelector('.checkin-section-title');
        const content = section.querySelector('.checkin-section-content');
        title.addEventListener('click', () => {
            content.classList.toggle('hidden');
        });

        const inputs = section.querySelectorAll('input, textarea');
        inputs.forEach((input) => {
            input.addEventListener('input', () => {
                section.classList.add('completed');
            });
        });
    });

    const moodButtons = document.querySelectorAll('.checkin-mood-button');
    moodButtons.forEach((currentButton) => {
        currentButton.addEventListener('click', () => {
            if (!currentButton.classList.contains('selected')) {
                moodButtons.forEach((button) => {
                    button.classList.remove('selected');
                });
                currentButton.classList.add('selected');
            } else {
                currentButton.classList.remove('selected');
                currentButton.querySelector('input').checked = false;
            }
        });
    });

    document.getElementById('add-measurement-button').addEventListener('click', addMeasurement);

    function addMeasurement() {
        const container = document.querySelector('.checkin-measurements-container');
        const index = container.querySelectorAll('.measurement').length;
        const newMeasurement = document.createElement('div');
        newMeasurement.classList.add('measurement');

        newMeasurement.innerHTML = `
        <div class="measurement-header">
            <label for="body-part-select-${index}">Select Body Part:</label>
            <select class="body-part-select" id="body-part-select-${index}">
                <option value="">Select Body Part</option>
                <option value="ARM">Arm</option>
                <option value="LEG">Leg</option>
                <option value="WEIGHT">Weight</option>
            </select>
            <button type="button" class="fa-solid fa-xmark remove-measurement-button"></button>
        </div>
        <div class="measurement-columns hidden">
            <div class="measurement-column left-column">
                <h4>Left Side</h4>
                <div class="measurement-points left-points"></div>
            </div>
            <div class="measurement-column right-column">
                <h4>Right Side</h4>
                <div class="measurement-points right-points"></div>
            </div>
        </div>
    `;

        // Add event listeners
        newMeasurement.querySelector('.remove-measurement-button').addEventListener('click', () => {
            newMeasurement.remove();
            updateMeasurementNames();
        });

        const bodyPartSelect = newMeasurement.querySelector(`#body-part-select-${index}`);
        bodyPartSelect.addEventListener('change', () => {
            loadMeasurementFields(newMeasurement, index);
        });

        container.appendChild(newMeasurement);
    }

    function loadMeasurementFields(newMeasurement, index) {
        const bodyPart = newMeasurement.querySelector(`select#body-part-select-${index}`).value;
        const columnsContainer = newMeasurement.querySelector('.measurement-columns');
        const leftPointsContainer = newMeasurement.querySelector('.left-points');
        const rightPointsContainer = newMeasurement.querySelector('.right-points');
        leftPointsContainer.innerHTML = '';
        rightPointsContainer.innerHTML = '';

        let measurementLocations = [];

        // Predefined measurement locations for each body part
        if (bodyPart === 'ARM') {
            measurementLocations = ['Bicep', 'Forearm', 'Wrist', 'Palm', 'Index finger'];
        } else if (bodyPart === 'LEG') {
            measurementLocations = ['Upper thigh', 'Mid thigh', 'Over kneecap', '2 cm below knee crease', 'Widest part of calf', '2 cm above ankle bone', 'Base of toes'];
        } else if (bodyPart === 'WEIGHT') {
            measurementLocations = ['Weight'];
        }

        if (bodyPart === 'ARM' || bodyPart === 'LEG') {
            columnsContainer.classList.remove('hidden');
            measurementLocations.forEach((location, locationIndex) => {
                // Left side
                const leftPointInput = document.createElement('div');
                leftPointInput.classList.add('measurement-point');
                leftPointInput.innerHTML = `
                <label>${location} (Left):</label>
                <input type="hidden" name="measurements[${index + locationIndex}].location" value="${location}">
                <input type="hidden" name="measurements[${index + locationIndex}].side" value="Left">
                <input type="hidden" name="measurements[${index + locationIndex}].type" value="${bodyPart}">
                <div>
                    <input type="number" name="measurements[${index + locationIndex}].value" step="0.1" placeholder="Value"> 
                    <select name="measurements[${index + locationIndex}].unit">
                        <option value="cm">cm</option>
                        <option value="inches">inches</option>
                    </select>
                </div>
            `;
                leftPointsContainer.appendChild(leftPointInput);

                // Right side
                const rightPointInput = document.createElement('div');
                rightPointInput.classList.add('measurement-point');
                rightPointInput.innerHTML = `
                <label>${location} (Right):</label>
                <input type="hidden" name="measurements[${index + locationIndex}].location" value="${location}">
                <input type="hidden" name="measurements[${index + locationIndex}].side" value="Right">
                <input type="hidden" name="measurements[${index + locationIndex}].type" value="${bodyPart}">
                <div>
                    <input type="number" name="measurements[${index + locationIndex}].value" step="0.1" placeholder="Value">
                    <select name="measurements[${index + locationIndex}].unit">
                        <option value="cm">cm</option>
                        <option value="inches">inches</option>
                    </select>
                <div>
            `;
                rightPointsContainer.appendChild(rightPointInput);
            });
        } else {
            // For measurements like WEIGHT, just generate a single input field
            columnsContainer.classList.remove('hidden');
            const singlePointInput = document.createElement('div');
            singlePointInput.classList.add('measurement-point');
            singlePointInput.innerHTML = `
            <label>Weight:</label>
            <input type="hidden" name="measurements[${index}].type" value="${bodyPart}">
            <div>
                <input type="number" name="measurements[${index}].value" step="0.1" placeholder="Value">
                <select name="measurements[${index}].unit">
                    <option value="kg">kg</option>
                    <option value="lbs">lbs</option>
                </select>
            <div>
        `;
            leftPointsContainer.appendChild(singlePointInput);
        }

        updateMeasurementNames(); // Reindex all names in case measurements were added/removed
    }


    function updateMeasurementNames() {
        const measurementItems = document.querySelectorAll('.measurement');
        measurementItems.forEach((item, index) => {
            const locationFields = item.querySelectorAll('.measurement-point');
            locationFields.forEach((locationField) => {
                // Update the name attributes for the dynamically generated fields
                locationField.querySelector('input[name*="location"]').name = `measurements[${index}].location`;
                locationField.querySelector('input[name*="side"]').name = `measurements[${index}].side`;
                locationField.querySelector('input[type="number"]').name = `measurements[${index}].value`;
                locationField.querySelector('select[name*="unit"]').name = `measurements[${index}].unit`;

                const typeField = locationField.querySelector('input[name*="type"]');
                if (typeField) {
                    typeField.name = `measurements[${index}].type`;
                }

                index++; // Increment the index for the next measurement
            });
        });
    }


    let capturedPhotos = [];

    const photoCaptureElement = document.querySelector('camera-component');

    // Listen for the 'photos-confirmed' event to get the captured photos
    photoCaptureElement.addEventListener('photos-confirmed', function (event) {
        // Access the captured photos from the event's detail
        capturedPhotos = event.detail;

        // Display the captured photos in the UI
        updatePhotosPreview(capturedPhotos);
    });

    function updatePhotosPreview(capturedPhotos) {
        const photosContainer = document.querySelector('photo-container');

        // Clear the container before adding new photos
        photosContainer.innerHTML = '';

        // Display each captured photo in the container using a custom element
        capturedPhotos.forEach(photo => {
            let currentPhotos = JSON.parse(photosContainer.getAttribute('photos') || '[]');

            // Add the new photo to the array
            currentPhotos.push(photo);

            // Update the photos attribute with the new array
            photosContainer.setAttribute('photos', JSON.stringify(currentPhotos));
        });
    }

    document.querySelector('.checkin-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);

        // Append the captured photos to the form data
        capturedPhotos.forEach((photo, index) => {
            formData.append(`photos[${index}].file`, photo.blob);
            formData.append(`photos[${index}].bodyPart`, photo.bodyPart);
        });

        // Submit the form data to the server
        fetch('/diary/checkin', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                window.location.href = '/diary';
            } else {
                console.error('Form submission failed.');
            }
        }).catch(error => {
            console.error('An error occurred:', error);
        });
    });
})();