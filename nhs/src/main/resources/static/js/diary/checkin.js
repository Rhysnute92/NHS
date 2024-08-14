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

    const input = document.querySelector('.photo-upload');

    document.getElementById('add-measurement-button').addEventListener('click', addMeasurement);

    function addMeasurement() {
        const container = document.querySelector('.checkin-measurements-container');
        const index = container.querySelectorAll('.measurement').length;
        const newMeasurement = document.createElement('div');
        newMeasurement.classList.add('measurement');
        newMeasurement.innerHTML = `
        <select class="type-select" name="measurements[${index}].type">
            <option value="WEIGHT">Weight</option>
            <option value="NECK">Neck</option>
            <option value="BREAST">Breast</option>
            <option value="CALF">Calf</option>
            <option value="THIGH">Thigh</option>
            <option value="FOREARM">Forearm</option>
        </select>
        <input type="number" name="measurements[${index}].value" step="0.1" required>
        <select class="unit-select" name="measurements[${index}].unit"></select>
        <button type="button" class="fa-solid fa-xmark remove-measurement-button"></button>
    `;

        const unitSelect = newMeasurement.querySelector(`select[name="measurements[${index}].unit"]`);
        changeUnits(unitSelect, newMeasurement);

        const typeSelect = newMeasurement.querySelector(`select[name="measurements[${index}].type"]`);
        typeSelect.addEventListener('change', () => {
            changeUnits(unitSelect, newMeasurement);
        });

        newMeasurement.querySelector('.remove-measurement-button').addEventListener('click', () => {
            newMeasurement.remove();
            updateMeasurementNames();
        });

        container.appendChild(newMeasurement);
    }

    function changeUnits(unitSelect, newMeasurement) {
        unitSelect.innerHTML = '';

        const weightUnits = ['KG', 'LBS'];
        const bodyPartUnits = ['CM', 'INCHES'];

        if (newMeasurement.querySelector('select[name*="type"]').value === 'WEIGHT') {
            for (const unit of weightUnits) {
                const option = document.createElement('option');
                option.value = unit;
                option.textContent = unit.toLowerCase();
                unitSelect.appendChild(option);
            }
        } else {
            for (const unit of bodyPartUnits) {
                const option = document.createElement('option');
                option.value = unit;
                option.textContent = unit.toLowerCase();
                unitSelect.appendChild(option);
            }
        }
    }

// Make sure the measurement names are updated when a measurement is removed
    function updateMeasurementNames() {
        const measurementItems = document.querySelectorAll('.measurement');
        measurementItems.forEach((item, index) => {
            item.querySelector('select[name*="type"]').name = `measurements[${index}].type`;
            item.querySelector('input[name*="value"]').name = `measurements[${index}].value`;
            item.querySelector('select[name*="unit"]').name = `measurements[${index}].unit`;
        });
    }


// Store captured photos globally or within a scoped variable
    let capturedPhotos = [];

// Listen for the 'photos-confirmed' event
    const photoCaptureElement = document.querySelector('camera-component');
    photoCaptureElement.addEventListener('photos-confirmed', function (event) {
        // Access the captured photos from the event's detail
        capturedPhotos = event.detail;

        // Display the captured photos in the UI
        updatePhotosPreview(capturedPhotos);
    });

// Function to update the photos preview in the UI
    function updatePhotosPreview(capturedPhotos) {
        const photosContainer = document.querySelector('.photos-container');

        // Clear the container before adding new photos
        photosContainer.innerHTML = '';

        // Display each captured photo in the container using a custom element
        capturedPhotos.forEach(blob => {
            const photo = document.createElement('photo-component');
            photo.setAttribute('url', URL.createObjectURL(blob));
            photosContainer.appendChild(photo);
        });
    }

// Modify the form submit event listener
    document.querySelector('.checkin-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const form = event.target;
        const formData = new FormData(form);

        // Append the captured photos to the form data
        capturedPhotos.forEach((blob, index) => {
            formData.append(`photos[${index}].file`, blob);
            formData.append(`photos[${index}].bodyPart`, `Photo ${index + 1}`);
        });

        // Submit the form data via Fetch API
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