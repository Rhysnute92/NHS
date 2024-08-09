const openCameraButton = document.querySelector('.open-camera');
const cameraContainer = document.querySelector('.camera-container');
const video = document.querySelector('.video');
const captureButton = document.querySelector('.capture-button');
const flipCameraButton = document.querySelector('.flip-camera-button');
const closeCameraButton = document.querySelector('.close-camera-button');
const canvas = document.querySelector('.canvas');
const context = canvas.getContext('2d');

let currentStream = null;
let facingMode = 'environment'; // Default to back camera

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

        window.addEventListener('keydown', handleKeyDown);
    } catch (error) {
        console.error('Error accessing the camera: ', error);
        alert('Unable to access camera. Please make sure you have granted permissions.');
    }
}

function handleKeyDown(event) {
    console.log(event.key)
    if (event.key === 'Enter') {
        takePhoto();
    } else if (event.key === 'Escape') {
        closeCamera();
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

    // Convert the canvas to a blob so it can be sent to the server
    canvas.toBlob((blob) => {
        const formData = new FormData();
        formData.append('photo', blob, 'photo.png');

        // Stop the camera stream
        currentStream.getTracks().forEach(track => track.stop());

        // Hide camera container and submit form
        cameraContainer.style.display = 'none';

        // Remove event listener for the back button
        window.removeEventListener('popstate', closeCamera);

        // Send the photo to the server
        fetch('/diary/photos', {
            method: 'POST',
            body: formData
        }).then(response => {
            if (response.ok) {
                window.location.href = '/diary/photos';
            } else {
                console.error('Failed to upload photo');
                alert('Failed to upload photo. Please try again.');
            }
        }).catch(error => {
            console.error('Error:', error);
            alert('Error uploading photo. Please try again.');
        });
    }, 'image/png');
}

function flipCamera() {
    facingMode = (facingMode === 'environment') ? 'user' : 'environment';
    startCamera();
}

function closeCamera() {
    if (currentStream) {
        currentStream.getTracks().forEach(track => track.stop());
    }
    cameraContainer.style.display = 'none';
    window.removeEventListener('popstate', closeCamera);
    window.removeEventListener('keyDown', handleKeyDown);
}

openCameraButton.addEventListener('click', openCamera);
captureButton.addEventListener('click', takePhoto);
flipCameraButton.addEventListener('click', flipCamera);
closeCameraButton.addEventListener('click', closeCamera);
