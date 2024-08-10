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
const preview = document.querySelector('.checkin-photos-preview');

input.addEventListener('change', updateImageDisplay);

function updateImageDisplay() {
    while (preview.firstChild) {
        preview.removeChild(preview.firstChild);
    }

    const curFiles = input.files;
    if (curFiles.length === 0) {
        const para = document.createElement("p");
        para.textContent = "No files currently selected for upload";
        preview.appendChild(para);
    } else {

        for (const file of curFiles) {
            const photoContainer = document.createElement("div");
            photoContainer.classList.add("checkin-photos-preview-container");
            const para = document.createElement("p");
            if (validFileType(file)) {
                para.textContent = `File name ${file.name}, file size ${returnFileSize(
                    file.size,
                )}.`;
                const image = document.createElement("img");
                image.src = URL.createObjectURL(file);
                image.alt = image.title = file.name;
                image.classList.add("checkin-photos-preview-image");

                photoContainer.appendChild(image);
                preview.appendChild(photoContainer);
            } else {
                para.textContent = `File name ${file.name}: Not a valid file type. Update your selection.`;
                preview.appendChild(para);
            }

        }
    }
}

const fileTypes = [
    "image/apng",
    "image/bmp",
    "image/gif",
    "image/jpeg",
    "image/pjpeg",
    "image/png",
    "image/svg+xml",
    "image/tiff",
    "image/webp",
    "image/x-icon",
];

function validFileType(file) {
    return fileTypes.includes(file.type);
}

function returnFileSize(number) {
    if (number < 1e3) {
        return `${number} bytes`;
    } else if (number >= 1e3 && number < 1e6) {
        return `${(number / 1e3).toFixed(1)} KB`;
    } else {
        return `${(number / 1e6).toFixed(1)} MB`;
    }
}
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

function updateMeasurementNames() {
    const measurementItems = document.querySelectorAll('.measurement');
    measurementItems.forEach((item, index) => {
        item.querySelector('select[name*="type"]').name = `measurements[${index}].type`;
        item.querySelector('input[name*="value"]').name = `measurements[${index}].value`;
        item.querySelector('select[name*="unit"]').name = `measurements[${index}].unit`;
    });
}

