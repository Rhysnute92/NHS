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

    let globalMeasurementIndex = 0; // Single global index for all measurements

    document.getElementById('add-measurement-button').addEventListener('click', addMeasurement);

    function addMeasurement() {
        const container = document.querySelector('.checkin-measurements-container');
        const newMeasurement = document.createElement('div');
        newMeasurement.classList.add('measurement');

        const currentMeasurementIndex = globalMeasurementIndex; // Use the global index

        newMeasurement.innerHTML = `
        <div class="measurement-header">
            <label for="body-part-select-${currentMeasurementIndex}">Measurement type:</label>
            <select class="body-part-select" id="body-part-select-${currentMeasurementIndex}">
                <option value="">Select type</option>
                <option value="Arm">Arm</option>
                <option value="Leg">Leg</option>
                <option value="Weight">Weight</option>
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

        const bodyPartSelect = newMeasurement.querySelector(`#body-part-select-${currentMeasurementIndex}`);
        bodyPartSelect.addEventListener('change', () => {
            loadMeasurementFields(newMeasurement, currentMeasurementIndex);
        });

        container.appendChild(newMeasurement);

        // Increment the global index after adding the measurement
        globalMeasurementIndex++;
    }

    function loadMeasurementFields(newMeasurement, index) {
        const bodyPart = newMeasurement.querySelector(`select#body-part-select-${index}`).value;
        const columnsContainer = newMeasurement.querySelector('.measurement-columns');
        const leftPointsContainer = newMeasurement.querySelector('.left-points');
        const rightPointsContainer = newMeasurement.querySelector('.right-points');
        leftPointsContainer.innerHTML = '';
        rightPointsContainer.innerHTML = '';

        let measurementLocations = [];

        if (bodyPart === 'Arm') {
            measurementLocations = ['Bicep', 'Forearm', 'Wrist', 'Palm', 'Index finger'];
            columnsContainer.classList.remove('hidden');
        } else if (bodyPart === 'Leg') {
            measurementLocations = ['Upper thigh', 'Mid thigh', 'Over kneecap', '2 cm below knee crease', 'Widest part of calf', '2 cm above ankle bone', 'Base of toes'];
            columnsContainer.classList.remove('hidden');
        } else if (bodyPart === 'Weight') {
            columnsContainer.classList.add('hidden');
            const singlePointInput = document.createElement('div');
            singlePointInput.classList.add('measurement-point');
            singlePointInput.innerHTML = `
            <label>Weight:</label>
            <input type="hidden" name="measurements[${index}].type" value="Weight">
            <input type="hidden" name="measurements[${index}].location" value="Weight">
            <input type="hidden" name="measurements[${index}].side" value="">
            <div>
                <input type="number" name="measurements[${index}].value" step="0.1" placeholder="Value">
                <select name="measurements[${index}].unit">
                    <option value="kg">kg</option>
                    <option value="lbs">lbs</option>
                </select>
            </div>
        `;
            newMeasurement.appendChild(singlePointInput);
            return;
        }

        // Add left and right inputs for body parts that have sides
        measurementLocations.forEach((location) => {
            // Left side
            const leftPointInput = document.createElement('div');
            leftPointInput.classList.add('measurement-point');
            leftPointInput.innerHTML = `
            <label>${location} (Left):</label>
            <input type="hidden" name="measurements[${globalMeasurementIndex}].location" value="${location}">
            <input type="hidden" name="measurements[${globalMeasurementIndex}].side" value="Left">
            <input type="hidden" name="measurements[${globalMeasurementIndex}].type" value="${bodyPart}">
            <div>
                <input type="number" name="measurements[${globalMeasurementIndex}].value" step="0.1" placeholder="Value">
                <select name="measurements[${globalMeasurementIndex}].unit">
                    <option value="cm">cm</option>
                    <option value="inches">inches</option>
                </select>
            </div>
        `;
            leftPointsContainer.appendChild(leftPointInput);
            globalMeasurementIndex++;

            // Right side
            const rightPointInput = document.createElement('div');
            rightPointInput.classList.add('measurement-point');
            rightPointInput.innerHTML = `
            <label>${location} (Right):</label>
            <input type="hidden" name="measurements[${globalMeasurementIndex}].location" value="${location}">
            <input type="hidden" name="measurements[${globalMeasurementIndex}].side" value="Right">
            <input type="hidden" name="measurements[${globalMeasurementIndex}].type" value="${bodyPart}">
            <div>
                <input type="number" name="measurements[${globalMeasurementIndex}].value" step="0.1" placeholder="Value">
                <select name="measurements[${globalMeasurementIndex}].unit">
                    <option value="cm">cm</option>
                    <option value="inches">inches</option>
                </select>
            </div>
        `;
            rightPointsContainer.appendChild(rightPointInput);
            globalMeasurementIndex++;
        });
    }

    function updateMeasurementNames() {
        const measurementItems = document.querySelectorAll('.measurement');
        let index = 0;

        measurementItems.forEach((item) => {
            const locationFields = item.querySelectorAll('.measurement-point');
            locationFields.forEach((locationField) => {
                const locationInput = locationField.querySelector('input[name*="location"]');
                const sideInput = locationField.querySelector('input[name*="side"]');
                const valueInput = locationField.querySelector('input[type="number"]');
                const unitSelect = locationField.querySelector('select[name*="unit"]');

                // Only update if the input fields exist
                if (locationInput) locationInput.name = `measurements[${index}].location`;
                if (sideInput) sideInput.name = `measurements[${index}].side`;
                if (valueInput) valueInput.name = `measurements[${index}].value`;
                if (unitSelect) unitSelect.name = `measurements[${index}].unit`;

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
        capturedPhotos = event.detail;
        updatePhotosPreview(capturedPhotos);
    });

    function updatePhotosPreview(capturedPhotos) {
        const photosContainer = document.querySelector('photo-container');
        photosContainer.innerHTML = '';
        capturedPhotos.forEach(photo => {
            let currentPhotos = JSON.parse(photosContainer.getAttribute('photos') || '[]');
            currentPhotos.push(photo);
            photosContainer.setAttribute('photos', JSON.stringify(currentPhotos));
        });
    }

    // Submit the form data along with photos
    document.querySelector('.checkin-form').addEventListener('submit', function (event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);

        // Append captured photos to form data
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
