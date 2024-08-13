const openCameraButton = document.querySelector('.open-camera');
const cameraContainer = document.querySelector('.camera-container');
const video = document.querySelector('.video');
const captureButton = document.querySelector('.capture-button');
const flipCameraButton = document.querySelector('.flip-camera-button');
const closeCameraButton = document.querySelector('.close-camera-button');

let currentStream = null;
let facingMode = 'environment'; // Default to back camera
let capturedPhotos = [];

async function startCamera() {
    if (currentStream) {
        currentStream.getTracks().forEach(track => track.stop());
    }
    try {
        // Get the camera stream
        const constraints = { video: { facingMode: facingMode } };
        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        currentStream = stream;
        video.srcObject = stream;
        await video.play();

        // Add or remove the mirror class based on front/back camera
        if (facingMode === 'user') {
            video.classList.add('mirror');
        } else {
            video.classList.remove('mirror');
        }
        // Add event listener for the back button on mobile
        window.addEventListener('popstate', closeCamera);

        // Add event listener for the enter and escape keys
        document.addEventListener('keydown', handleKeyPress);
    } catch (error) {
        console.error('Error accessing the camera: ', error);
        alert('Unable to access camera. Please make sure you have granted permissions.');
    }
}

function handleKeyPress(event) {
    // Check camera is open
    if (cameraContainer.style.display === 'flex') {
        if (event.key === 'Enter') {
            event.preventDefault();
            takePhoto();
        } else if (event.key === 'Escape') {
            closeCamera();
        }
    }
}

function openCamera() {
    // Check if the browser supports accessing the camera
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        cameraContainer.style.display = 'flex';
        startCamera();
    } else {
        alert('Your browser does not support accessing the camera.');
    }
}

function takePhoto() {
    const canvas = document.querySelector('.canvas');
    const context = canvas.getContext('2d');

    const width = video.videoWidth;
    const height = video.videoHeight;

    canvas.width = width;
    canvas.height = height;
    context.drawImage(video, 0, 0, width, height);

    canvas.toBlob((blob) => {
        if (blob) {
            // Create a new image element and display the captured photo
            const photo = document.createElement('photo-component');
            photo.setAttribute('url', URL.createObjectURL(blob));
            photo.classList.add('photo-preview');
            document.querySelector('.photos-preview-container').appendChild(photo);

            capturedPhotos.push(blob);

            closeCamera();
        } else {
            alert('Failed to capture the photo. Please try again.');
        }
    }, 'image/png');
}

function flipCamera() {
    facingMode = (facingMode === 'environment') ? 'user' : 'environment';
    startCamera();
}

function closeCamera() {
    if (currentStream) {
        // Stop the camera stream
        currentStream.getTracks().forEach(track => track.stop());
    }
    cameraContainer.style.display = 'none';
    window.removeEventListener('popstate', closeCamera);
    document.removeEventListener('keydown', handleKeyPress);
}

const openPhotosModalButton = document.querySelector('.open-photos-modal');
const photosModal = document.querySelector('.photos-modal');
const closePhotosModalButton = document.querySelector('.close-photos-modal');
const addPhotosButton = document.querySelector('.add-photos');

openPhotosModalButton.addEventListener('click', () => {
    photosModal.style.display = 'block';
});

closePhotosModalButton.addEventListener('click', () => {
    photosModal.style.display = 'none';
});

let form;
if (document.querySelector('.photos-form')) {
    form = document.querySelector('.photos-form');
} else if (document.querySelector('.checkin-form')) {
    form = document.querySelector('.checkin-form');
}

if (!form) {
    throw new Error('Form element not found');
}

form.addEventListener('submit', function(event) {
    event.preventDefault();
    submitForm();
});

addPhotosButton.addEventListener('click', () => {
    photosModal.style.display = 'none';
    if (form.classList.contains('photos-form')) {
        // Just submit the form with the photos if it's the photos page
        submitForm();
        capturedPhotos = []; // Reset the captured photos to avoid duplicates
    } else if (form.classList.contains('checkin-form')) {
        // Add the photos to the check-in form as a preview without submitting yet
        const photosContainer = document.querySelector('.photos-container');
        capturedPhotos.forEach((blob) => {
            const photoComponent = document.createElement('photo-component');
            photoComponent.setAttribute('url', URL.createObjectURL(blob));
            photosContainer.appendChild(photoComponent);
        });
    }

    document.querySelector('.photos-preview-container').innerHTML = '';
});

openCameraButton.addEventListener('click', openCamera);
captureButton.addEventListener('click', takePhoto);
flipCameraButton.addEventListener('click', flipCamera);
closeCameraButton.addEventListener('click', closeCamera);
