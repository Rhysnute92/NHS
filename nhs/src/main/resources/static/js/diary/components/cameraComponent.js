class CameraComponent extends HTMLElement {
    constructor() {
        super();

        this.attachShadow({ mode: 'open' });

        const template = document.createElement('template');
        template.innerHTML = `
          <link rel="stylesheet" href="/css/diary/cameraComponent.css">
          <div class="photo-capture-container">
            <button type="button" class="open-photos-modal">
              <i class="fa-solid fa-camera photo-icon"></i>
              Add a photo
            </button>
          </div>
          <div class="photos-modal">
            <div class="photos-modal-content">
              <div class="photos-modal-header">
                <h3>Photos</h3>
                <button type="button" class="close-photos-modal">
                  &times;
                </button>
              </div>
              <div class="photos-inputs">
                  <button type="button" class="open-camera">
                    <i class="fa-solid fa-camera photo-icon"></i>
                    Take photo
                  </button>
                  <input type="file" id="photo-input" class="photo-input" accept="image/*" multiple>
                  <label for="photo-input" class="photo-label">
                    <span class="photo-text">From gallery</span>
                  </label> 
              </div>
              <photo-container></photo-container>
              <button type="button" class="add-photos">Confirm</button>
            </div>
          </div>
          <div class="camera-container">
            <video class="video" autoplay playsinline></video>
            <div class="horizontal-line"></div>
            <div class="vertical-line"></div>
            <button type="button" class="close-camera-button">&times;</button>
            <div class="camera-controls">
                <button type="button" class="flip-camera-button">Flip Camera</button>
                <button type="button" class="capture-button"></button>
                <div class="spacer"></div> <!-- Spacer to center the buttons -->
            </div>
            <canvas class="canvas"></canvas>
          </div>
        `;
        this.shadowRoot.appendChild(template.content.cloneNode(true));

        this.capturedPhotos = [];
        this.currentStream = null;
        this.facingMode = 'environment'; // Default to back camera

        this.setupEventListeners();
    }

    setupEventListeners() {
        const openCameraButton = this.shadowRoot.querySelector('.open-camera');
        const openPhotosModalButton = this.shadowRoot.querySelector('.open-photos-modal');
        const closePhotosModalButton = this.shadowRoot.querySelector('.close-photos-modal');
        const captureButton = this.shadowRoot.querySelector('.capture-button');
        const flipCameraButton = this.shadowRoot.querySelector('.flip-camera-button');
        const closeCameraButton = this.shadowRoot.querySelector('.close-camera-button');
        const addPhotosButton = this.shadowRoot.querySelector('.add-photos');
        const photoInput = this.shadowRoot.querySelector('.photo-input');

        openCameraButton.addEventListener('click', this.openCamera.bind(this));
        openPhotosModalButton.addEventListener('click', this.showPhotosModal.bind(this));
        closePhotosModalButton.addEventListener('click', this.closePhotosModal.bind(this));
        captureButton.addEventListener('click', this.takePhoto.bind(this));
        flipCameraButton.addEventListener('click', this.flipCamera.bind(this));
        closeCameraButton.addEventListener('click', this.closeCamera.bind(this));
        addPhotosButton.addEventListener('click', this.confirmPhotos.bind(this));
        photoInput.addEventListener('change', this.handlePhotoInput.bind(this));
        document.addEventListener('mousedown', this.handleClickOutside.bind(this));
    }

    async openCamera() {
        const cameraContainer = this.shadowRoot.querySelector('.camera-container');
        const video = this.shadowRoot.querySelector('.video');

        // Check if the browser supports accessing the camera
        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            cameraContainer.style.display = 'flex';
            this.startCamera(video);
        } else {
            alert('Your browser does not support accessing the camera.');
        }
    }

    async startCamera(video) {
        // Stop the camera stream if it's already open
        if (this.currentStream) {
            this.currentStream.getTracks().forEach(track => track.stop());
        }
        try {
            // Get the camera stream
            const constraints = { video: { facingMode: this.facingMode } };
            const stream = await navigator.mediaDevices.getUserMedia(constraints);
            this.currentStream = stream;
            video.srcObject = stream;
            await video.play();

            this.updateMirrorEffect(video);

            // Add event listener for the back button on mobile
            window.addEventListener('popstate', this.closeCamera.bind(this));

            // Add event listener for the enter and escape keys
            document.addEventListener('keydown', this.handleKeyPress.bind(this));
        } catch (error) {
            console.error('Error accessing the camera: ', error);
            alert('Unable to access camera. Please make sure you have granted permissions.');
        }
    }

    stopCamera() {
        // Stop the camera stream
        if (this.currentStream) {
            this.currentStream.getTracks().forEach(track => track.stop());
        }
        this.currentStream = null;

        // Remove event listeners that affect the camera
        window.removeEventListener('popstate', this.closeCamera.bind(this));
        document.removeEventListener('keydown', this.handleKeyPress.bind(this));
    }

    closeCamera() {
        // Hide the camera container
        const cameraContainer = this.shadowRoot.querySelector('.camera-container');
        this.stopCamera();
        cameraContainer.style.display = 'none';
    }

    updateMirrorEffect(video) {
        // Add or remove the mirror class based on front/back camera
        if (this.facingMode === 'user') {
            video.classList.add('mirror');
        } else {
            video.classList.remove('mirror');
        }
    }

    flipCamera() {
        // Toggle the camera facing mode
        this.facingMode = (this.facingMode === 'environment') ? 'user' : 'environment';
        const video = this.shadowRoot.querySelector('.video');
        this.startCamera(video);
    }

    takePhoto() {
        // Capture a photo from the video stream
        const video = this.shadowRoot.querySelector('.video');
        const canvas = this.shadowRoot.querySelector('.canvas');
        const context = canvas.getContext('2d');

        const width = video.videoWidth;
        const height = video.videoHeight;

        canvas.width = width;
        canvas.height = height;
        context.drawImage(video, 0, 0, width, height);

        // Convert the canvas to a blob
        canvas.toBlob((blob) => {
            if (blob) {
                this.capturedPhotos.push({
                    url: URL.createObjectURL(blob),
                    id: null
                });

                this.updatePhotoContainer();
                this.closeCamera();
            } else {
                alert('Failed to capture the photo. Please try again.');
            }
        }, 'image/png');
    }

    handlePhotoInput(event) {
        const files = Array.from(event.target.files);

        files.forEach(file => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const blob = new Blob([new Uint8Array(e.target.result)], { type: file.type });
                this.capturedPhotos.push({
                    url: URL.createObjectURL(blob),
                    id: Date.now().toString() // Temporary ID
                });

                this.updatePhotoContainer();
            };
            reader.readAsArrayBuffer(file);
        });

        // Clear the file input
        event.target.value = '';
    }

    updatePhotoContainer() {
        const photoContainer = this.shadowRoot.querySelector('photo-container');
        photoContainer.setAttribute('photos', JSON.stringify(this.capturedPhotos));
    }

    showPhotosModal() {
        const photosModal = this.shadowRoot.querySelector('.photos-modal');
        photosModal.style.display = 'block';
    }

    closePhotosModal() {
        const photosModal = this.shadowRoot.querySelector('.photos-modal');
        photosModal.style.display = 'none';
    }

    confirmPhotos() {
        const photosModal = this.shadowRoot.querySelector('.photos-modal');
        photosModal.style.display = 'none';

        // Create a custom event with the photos
        this.dispatchEvent(new CustomEvent('photos-confirmed', {
            detail: this.capturedPhotos,
            bubbles: true,
            composed: true
        }));

        this.shadowRoot.querySelector('photo-container').setAttribute('photos', '[]');
        this.capturedPhotos = [];
    }

    handleKeyPress(event) {
        // Check camera is open
        const cameraContainer = this.shadowRoot.querySelector('.camera-container');
        if (cameraContainer.style.display === 'flex') {
            if (event.key === 'Enter') {
                event.preventDefault();
                this.takePhoto();
            } else if (event.key === 'Escape') {
                this.closeCamera();
            }
        }
    }

    handleClickOutside(event) {
        // Handle clicks outside modal (optional, left for future implementation)
    }
}

customElements.define('camera-component', CameraComponent);
