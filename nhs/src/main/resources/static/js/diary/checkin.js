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
moodButtons.forEach((button) => {
    button.addEventListener('click', () => {
        moodButtons.forEach((button) => {
            button.classList.remove('selected');
        });
        button.classList.add('selected');
    });
});




const input = document.querySelector('.checkin-photos-upload');
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

const checkinForm = document.querySelector('.checkin-form');
checkinForm.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(checkinForm);

    fetch('/diary/checkin', {
        method: 'POST',
        body: formData,
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            alert(data.message);
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('An error occurred while submitting the check-in.');
        });
});
