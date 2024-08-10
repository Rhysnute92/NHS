const openCameraButton = document.querySelector('.open-camera');
const cameraContainer = document.querySelector('.camera-container');
const video = document.querySelector('.video');
const captureButton = document.querySelector('.capture-button');
const flipCameraButton = document.querySelector('.flip-camera-button');
const closeCameraButton = document.querySelector('.close-camera-button');
const canvas = document.querySelector('.canvas');
const context = canvas.getContext('2d');
const photosContainer = document.querySelector('.photos-container');
const form = document.querySelector('.checkin-form');

let currentStream = null;
let facingMode = 'environment'; // Default to back camera
let photoIndex = 0;
let capturedPhotos = [];

async function startCamera() {
    if (currentStream) {
        currentStream.getTracks().forEach(track => track.stop());
    }
    try {
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
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        cameraContainer.style.display = 'flex';
        startCamera();
    } else {
        alert('Your browser does not support accessing the camera.');
    }
}

function takePhoto() {
    const width = video.videoWidth;
    const height = video.videoHeight;

    // Set the canvas size to the video size and draw the video frame to the canvas
    canvas.width = width;
    canvas.height = height;
    context.drawImage(video, 0, 0, width, height);

    // Convert the canvas to a blob and store it in the capturedPhotos array
    canvas.toBlob(async (blob) => {
        if (blob) {
            // Store the blob in the capturedPhotos array
            capturedPhotos.push(blob);

            // Show a preview image
            const img = document.createElement('img');
            img.src = URL.createObjectURL(blob);
            img.classList.add('captured-photo');
            photosContainer.appendChild(img);

            photoIndex++;
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

openCameraButton.addEventListener('click', openCamera);
captureButton.addEventListener('click', takePhoto);
flipCameraButton.addEventListener('click', flipCamera);
closeCameraButton.addEventListener('click', closeCamera);
