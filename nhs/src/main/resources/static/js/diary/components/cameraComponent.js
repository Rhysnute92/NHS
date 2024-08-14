class CameraComponent extends HTMLElement {
    constructor() {
        super();

        this.attachShadow({ mode: 'open' });

        const template = document.createElement('template');
        template.innerHTML = `
          <style>
            .photo-form {
                margin-bottom: 1rem;
            }
            
            .photo-label {
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 0.5rem;
                transition: opacity 0.5s;
                font-size: 1.2rem;
            }
            
            .photo-label:hover {
                cursor: pointer;
                opacity: 80%;
            }
            
            .photo-upload {
                opacity: 0;
                position: absolute;
                z-index: -1;
                width: 0;
                height: 0;
            }
            
            .photo-icon {
                font-size: 2rem;
                color: var(--nhs-dark-grey);
            }
            
            .photos-container, .photos-preview-container {
                background-color: var(--nhs-white);
                padding: 1rem;
                display: grid;
                grid-template-columns: repeat(5, 1fr);
                gap: 0.5rem;
            }
            
            .no-photos {
                text-align: center;
                margin-top: 2rem;
            }
            
            .camera-container {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: none;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                background: rgba(0, 0, 0, 0.8);
                z-index: 9999;
            }
            
            .video {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            
            .mirror {
                transform: scaleX(-1); /* flip the image when using the front-facing camera */
            }
            
            .horizontal-line {
                position: absolute;
                top: 50%;
                left: 0;
                width: 100%;
                height: 2px;
                background-color: #00ff00;
            }
            
            .vertical-line {
                position: absolute;
                top: 0;
                left: 50%;
                width: 2px;
                height: 100%;
                background-color: #00ff00;
            }
            
            .capture-button, .flip-camera-button, .close-camera-button {
                position: absolute;
                bottom: 20px;
                padding: 10px 20px;
                font-size: 16px;
                background-color: #00ff00;
                border: none;
                cursor: pointer;
            }
            
            .flip-camera-button {
                bottom: 60px;
            }
            
            .close-camera-button {
                bottom: 100px;
            }
            
            .canvas {
                display: none;
            }
            
            .open-camera, .open-photos-modal {
                font-size: 1.15rem;
                display: flex;
                justify-content: center;
                align-items: center;
                gap: 0.5rem;
                border: none;
                background-color: transparent;
                transition: opacity 0.5s;
            }
            
            .open-camera:hover {
                cursor: pointer;
                opacity: 80%;
            }
            
            .photos-modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.8);
                z-index: 9999;
            }
            
            .photos-modal-content {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: var(--nhs-white);
                padding: 1rem;
                border-radius: 5px;
                max-width: 90%;
                max-height: 90%;
                overflow: auto;
            }

          </style>
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
                  <i class="fa-solid fa-times"></i>
                </button>
              </div>
              <button type="button" class="open-camera">
                <i class="fa-solid fa-camera photo-icon"></i>
                Take a photo
              </button>
              <input type="file" class="photo-input" accept="image/*" multiple>
              <div class="photos-preview-container"></div>
              <button type="button" class="add-photos">Confirm</button>
            </div>
          </div>
          <div class="camera-container">
            <video class="video" autoplay playsinline></video>
            <div class="horizontal-line"></div>
            <div class="vertical-line"></div>
            <button type="button" class="capture-button">Take Photo</button>
            <button type="button" class="flip-camera-button">Flip Camera</button>
            <button type="button" class="close-camera-button">Close Camera</button>
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
    }

    async openCamera() {
        const cameraContainer = this.shadowRoot.querySelector('.camera-container');
        const video = this.shadowRoot.querySelector('.video');

        if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            cameraContainer.style.display = 'flex';
            this.startCamera(video);
        } else {
            alert('Your browser does not support accessing the camera.');
        }
    }

    async startCamera(video) {
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
        this.facingMode = (this.facingMode === 'environment') ? 'user' : 'environment';
        const video = this.shadowRoot.querySelector('.video');
        this.startCamera(video);
    }

    takePhoto() {
        const video = this.shadowRoot.querySelector('.video');
        const canvas = this.shadowRoot.querySelector('.canvas');
        const context = canvas.getContext('2d');

        const width = video.videoWidth;
        const height = video.videoHeight;

        canvas.width = width;
        canvas.height = height;
        context.drawImage(video, 0, 0, width, height);

        canvas.toBlob((blob) => {
            if (blob) {
                this.capturedPhotos.push(blob);

                // Display the photo preview
                this.displayPhotoPreview(blob);

                this.closeCamera();
            } else {
                alert('Failed to capture the photo. Please try again.');
            }
        }, 'image/png');
    }

    handlePhotoInput(event) {
        const files = Array.from(event.target.files);

        files.forEach(file => {
            // Convert each file to a Blob and store it in the capturedPhotos array
            // Maybe keep the file format the same for uploaded files?
            const reader = new FileReader();
            reader.onload = (e) => {
                const blob = new Blob([new Uint8Array(e.target.result)], { type: file.type });
                this.capturedPhotos.push(blob);

                // Display a preview for each uploaded image
                this.displayPhotoPreview(blob);
            };
            reader.readAsArrayBuffer(file);
        });

        // Clear the file input
        event.target.value = '';
    }

    displayPhotoPreview(blob) {
        // Create a preview image for each photo
        const photo = document.createElement('photo-component');
        photo.setAttribute('url', URL.createObjectURL(blob));
        this.shadowRoot.querySelector('.photos-preview-container').appendChild(photo);
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

        this.shadowRoot.querySelector('.photos-preview-container').innerHTML = '';
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
}

customElements.define('camera-component', CameraComponent);