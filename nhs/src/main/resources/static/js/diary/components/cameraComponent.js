class CameraComponent extends HTMLElement {
    constructor() {
        super();

        this.attachShadow({ mode: 'open' });


        const template = document.createElement('template');
        template.innerHTML = `
<!--          <link rel="stylesheet" href="/css/diary/cameraComponent.css">-->
          <div class="photo-capture-container">
            <button type="button" class="open-photos-modal photo-button">
            <svg width="200px" height="200px" viewBox="0 0 24 24" fill="color" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" clip-rule="evenodd" d="M9.77778 21H14.2222C17.3433 21 18.9038 21 20.0248 20.2646C20.51 19.9462 20.9267 19.5371 21.251 19.0607C22 17.9601 22 16.4279 22 13.3636C22 10.2994 22 8.76721 21.251 7.6666C20.9267 7.19014 20.51 6.78104 20.0248 6.46268C19.3044 5.99013 18.4027 5.82123 17.022 5.76086C16.3631 5.76086 15.7959 5.27068 15.6667 4.63636C15.4728 3.68489 14.6219 3 13.6337 3H10.3663C9.37805 3 8.52715 3.68489 8.33333 4.63636C8.20412 5.27068 7.63685 5.76086 6.978 5.76086C5.59733 5.82123 4.69555 5.99013 3.97524 6.46268C3.48995 6.78104 3.07328 7.19014 2.74902 7.6666C2 8.76721 2 10.2994 2 13.3636C2 16.4279 2 17.9601 2.74902 19.0607C3.07328 19.5371 3.48995 19.9462 3.97524 20.2646C5.09624 21 6.65675 21 9.77778 21ZM12 9.27273C9.69881 9.27273 7.83333 11.1043 7.83333 13.3636C7.83333 15.623 9.69881 17.4545 12 17.4545C14.3012 17.4545 16.1667 15.623 16.1667 13.3636C16.1667 11.1043 14.3012 9.27273 12 9.27273ZM12 10.9091C10.6193 10.9091 9.5 12.008 9.5 13.3636C9.5 14.7192 10.6193 15.8182 12 15.8182C13.3807 15.8182 14.5 14.7192 14.5 13.3636C14.5 12.008 13.3807 10.9091 12 10.9091ZM16.7222 10.0909C16.7222 9.63904 17.0953 9.27273 17.5556 9.27273H18.6667C19.1269 9.27273 19.5 9.63904 19.5 10.0909C19.5 10.5428 19.1269 10.9091 18.6667 10.9091H17.5556C17.0953 10.9091 16.7222 10.5428 16.7222 10.0909Z" fill="#1C274C"/>
            </svg>
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
                  <button type="button" class="open-camera photo-button">
                    <svg width="200px" height="200px" viewBox="0 0 24 24" fill="color" xmlns="http://www.w3.org/2000/svg">
                      <path fill-rule="evenodd" clip-rule="evenodd" d="M9.77778 21H14.2222C17.3433 21 18.9038 21 20.0248 20.2646C20.51 19.9462 20.9267 19.5371 21.251 19.0607C22 17.9601 22 16.4279 22 13.3636C22 10.2994 22 8.76721 21.251 7.6666C20.9267 7.19014 20.51 6.78104 20.0248 6.46268C19.3044 5.99013 18.4027 5.82123 17.022 5.76086C16.3631 5.76086 15.7959 5.27068 15.6667 4.63636C15.4728 3.68489 14.6219 3 13.6337 3H10.3663C9.37805 3 8.52715 3.68489 8.33333 4.63636C8.20412 5.27068 7.63685 5.76086 6.978 5.76086C5.59733 5.82123 4.69555 5.99013 3.97524 6.46268C3.48995 6.78104 3.07328 7.19014 2.74902 7.6666C2 8.76721 2 10.2994 2 13.3636C2 16.4279 2 17.9601 2.74902 19.0607C3.07328 19.5371 3.48995 19.9462 3.97524 20.2646C5.09624 21 6.65675 21 9.77778 21ZM12 9.27273C9.69881 9.27273 7.83333 11.1043 7.83333 13.3636C7.83333 15.623 9.69881 17.4545 12 17.4545C14.3012 17.4545 16.1667 15.623 16.1667 13.3636C16.1667 11.1043 14.3012 9.27273 12 9.27273ZM12 10.9091C10.6193 10.9091 9.5 12.008 9.5 13.3636C9.5 14.7192 10.6193 15.8182 12 15.8182C13.3807 15.8182 14.5 14.7192 14.5 13.3636C14.5 12.008 13.3807 10.9091 12 10.9091ZM16.7222 10.0909C16.7222 9.63904 17.0953 9.27273 17.5556 9.27273H18.6667C19.1269 9.27273 19.5 9.63904 19.5 10.0909C19.5 10.5428 19.1269 10.9091 18.6667 10.9091H17.5556C17.0953 10.9091 16.7222 10.5428 16.7222 10.0909Z" fill="#1C274C"/>
                    </svg>    
                    Take photo
                  </button>
                  <input type="file" id="photo-input" class="photo-input" accept="image/*" multiple>
                  <label for="photo-input" class="photo-label photo-button">
                    <svg width="200px" height="200px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M18.5116 10.0771C18.5116 10.8157 17.8869 11.4146 17.1163 11.4146C16.3457 11.4146 15.7209 10.8157 15.7209 10.0771C15.7209 9.33841 16.3457 8.7396 17.1163 8.7396C17.8869 8.7396 18.5116 9.33841 18.5116 10.0771Z" fill="#1C274C"/>
                        <path fill-rule="evenodd" clip-rule="evenodd" d="M18.0363 5.53245C16.9766 5.39588 15.6225 5.39589 13.9129 5.39591H10.0871C8.37751 5.39589 7.02343 5.39588 5.9637 5.53245C4.87308 5.673 3.99033 5.96913 3.29418 6.63641C2.59803 7.30369 2.28908 8.14982 2.14245 9.19521C1.99997 10.211 1.99999 11.5089 2 13.1475V13.2482C1.99999 14.8868 1.99997 16.1847 2.14245 17.2005C2.28908 18.2459 2.59803 19.092 3.29418 19.7593C3.99033 20.4266 4.87307 20.7227 5.9637 20.8633C7.02344 20.9998 8.37751 20.9998 10.0871 20.9998H13.9129C15.6225 20.9998 16.9766 20.9998 18.0363 20.8633C19.1269 20.7227 20.0097 20.4266 20.7058 19.7593C21.402 19.092 21.7109 18.2459 21.8575 17.2005C22 16.1847 22 14.8868 22 13.2482V13.1476C22 11.5089 22 10.211 21.8575 9.19521C21.7109 8.14982 21.402 7.30369 20.7058 6.63641C20.0097 5.96913 19.1269 5.673 18.0363 5.53245ZM6.14963 6.858C5.21373 6.97861 4.67452 7.20479 4.28084 7.58215C3.88716 7.9595 3.65119 8.47635 3.52536 9.37343C3.42443 10.093 3.40184 10.9923 3.3968 12.1686L3.86764 11.7737C4.99175 10.8309 6.68596 10.885 7.74215 11.8974L11.7326 15.7223C12.1321 16.1053 12.7611 16.1575 13.2234 15.8461L13.5008 15.6593C14.8313 14.763 16.6314 14.8668 17.8402 15.9096L20.2479 17.9866C20.3463 17.7226 20.4206 17.4075 20.4746 17.0223C20.6032 16.106 20.6047 14.8981 20.6047 13.1979C20.6047 11.4976 20.6032 10.2897 20.4746 9.37343C20.3488 8.47635 20.1128 7.9595 19.7192 7.58215C19.3255 7.20479 18.7863 6.97861 17.8504 6.858C16.8944 6.7348 15.6343 6.73338 13.8605 6.73338H10.1395C8.36575 6.73338 7.10559 6.7348 6.14963 6.858Z" fill="#1C274C"/>
                        <path d="M17.0863 2.61039C16.2265 2.49997 15.1318 2.49998 13.7672 2.5H10.6775C9.31284 2.49998 8.21815 2.49997 7.35834 2.61039C6.46796 2.72473 5.72561 2.96835 5.13682 3.53075C4.79725 3.8551 4.56856 4.22833 4.41279 4.64928C4.91699 4.41928 5.48704 4.28374 6.12705 4.20084C7.21143 4.06037 8.597 4.06038 10.3463 4.06039H14.2612C16.0105 4.06038 17.396 4.06037 18.4804 4.20084C19.0394 4.27325 19.545 4.38581 20 4.56638C19.8454 4.17917 19.625 3.83365 19.3078 3.53075C18.719 2.96835 17.9767 2.72473 17.0863 2.61039Z" fill="#1C274C"/>
                    </svg>                    
                  <span class="photo-text">From gallery</span>
                  </label> 
              </div>
              <photo-container></photo-container>
              <button type="button" class="add-photos photo-button">Confirm</button>
            </div>
          </div>
          <div class="camera-container">
            <div class="camera-header">
              <select class="body-part-select">
                <option value="none">Select body part</option>
                <option value="head">Head</option>
                <option value="neck">Neck</option>
                <option value="arm">Arm</option>
                <option value="leg">Leg</option>
                <option value="chest">Chest</option>
              </select>
              <button type="button" class="close-camera-button">&times;</button>
            </div>
            <div class="video-container">
              <video class="video" autoplay playsinline></video>
              <img class="camera-overlay" src="">
            </div>
            <div class="camera-controls-container">
                <div class="camera-controls">
                    <div>
                      <button type="button" class="flip-camera-button">
                        <svg width="200px" height="200px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                          <path d="M4.06189 13C4.02104 12.6724 4 12.3387 4 12C4 7.58172 7.58172 4 12 4C14.5006 4 16.7332 5.14727 18.2002 6.94416M19.9381 11C19.979 11.3276 20 11.6613 20 12C20 16.4183 16.4183 20 12 20C9.61061 20 7.46589 18.9525 6 17.2916M9 17H6V17.2916M18.2002 4V6.94416M18.2002 6.94416V6.99993L15.2002 7M6 20V17.2916" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                      </button>
                    </div>
                    <div>
                      <button type="button" class="capture-button">
                        <div class="capture-button-inner"></div>
                      </button>
                    </div>
                    <div class="spacer"></div> <!-- Spacer to center the buttons -->
                </div>
            </div>
            <canvas class="canvas"></canvas>
          </div>
        `;

        this.sheet = new CSSStyleSheet();
        this.setCss().then(() => {
            this.shadowRoot.adoptedStyleSheets = [this.sheet];
            this.shadowRoot.appendChild(template.content.cloneNode(true));
            this.capturedPhotos = [];
            this.currentStream = null;
            this.facingMode = 'environment'; // Default to back camera
            this.setupEventListeners();
        });
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
        const bodyPartSelect = this.shadowRoot.querySelector('.body-part-select');

        openCameraButton.addEventListener('click', this.openCamera.bind(this));
        openPhotosModalButton.addEventListener('click', this.showPhotosModal.bind(this));
        closePhotosModalButton.addEventListener('click', this.closePhotosModal.bind(this));
        captureButton.addEventListener('click', this.takePhoto.bind(this));
        flipCameraButton.addEventListener('click', this.flipCamera.bind(this));
        closeCameraButton.addEventListener('click', this.closeCamera.bind(this));
        addPhotosButton.addEventListener('click', this.confirmPhotos.bind(this));
        photoInput.addEventListener('change', this.handlePhotoInput.bind(this));
        bodyPartSelect.addEventListener('change', this.updateCameraOverlay.bind(this));
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
                    blob: blob,
                    url: URL.createObjectURL(blob),
                    bodyPart: this.shadowRoot.querySelector('.body-part-select').value,
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
                    blob: blob,
                    url: URL.createObjectURL(blob),
                    bodyPart: "",
                    id: null
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

    updateCameraOverlay() {
        const bodyPart = this.shadowRoot.querySelector('.body-part-select').value;
        const cameraOverlay = this.shadowRoot.querySelector('.camera-overlay');
        cameraOverlay.src = `/images/guidelines/${bodyPart}.png`;
    }

    async setCss() {
        await fetch("/css/diary/cameraComponent.css")
            .then(response => response.text())
            .then(css => {
                this.sheet.replace(css);
            });
    }
}

customElements.define('camera-component', CameraComponent);
