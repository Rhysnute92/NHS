class PhotoComponent extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });

        this.shadowRoot.innerHTML = `
            <style>
                .photo {
                    position: relative;
                    display: inline-block;
                    width: 100%;
                    height: 100%;
                    aspect-ratio: 1 / 1;
                    cursor: pointer;
                    
                }

                .photo img {
                    max-width: 100%;
                    height: 100%;
                    object-fit: cover;
                    transition: transform 0.3s ease;
                }

                .photo img:hover {
                    transform: scale(1.05);
                }

                .delete-photo {
                    position: absolute;
                    top: 10px;
                    right: 10px;
                    background: rgba(255, 0, 0, 0.7);
                    border: none;
                    color: white;
                    border-radius: 50%;
                    width: 1.5rem;
                    height: 1.5rem;
                    cursor: pointer;
                    font-size: 1.2rem;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .enlarged-photo-container {
                    display: none;
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0, 0, 0, 0.8);
                    justify-content: center;
                    align-items: center;
                    z-index: 1000;
                }

                .enlarged-photo-container img {
                    max-width: 90%;
                    max-height: 90%;
                }

                .enlarged-photo-container .close-button {
                    position: absolute;
                    top: 20px;
                    right: 20px;
                    background: none;
                    border: none;
                    color: white;
                    font-size: 24px;
                    cursor: pointer;
                }
            </style>
            <div class="photo">
                <img alt="Photo" />
                <button type="button" class="delete-photo">&times;</button>
            </div>
            <div class="enlarged-photo-container">
                <button class="close-button">&times;</button>
                <img alt="Enlarged Photo" />
            </div>
        `;

        this.photoElement = this.shadowRoot.querySelector('.photo img');
        this.deleteButton = this.shadowRoot.querySelector('.delete-photo');
        this.enlargedPhotoContainer = this.shadowRoot.querySelector('.enlarged-photo-container');
        this.closeButton = this.shadowRoot.querySelector('.close-button');
    }

    static get observedAttributes() {
        // Watch for changes to the URL and photo ID attributes
        return ['url', 'photo-id'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        // Update the image URL
        if (name === 'url') {
            this.photoElement.src = newValue;
        }
    }

    connectedCallback() {
        // Add event listeners
        this.photoElement.addEventListener('click', this.showEnlargedPhoto.bind(this));
        this.closeButton.addEventListener('click', this.hideEnlargedPhoto.bind(this));
        this.enlargedPhotoContainer.addEventListener('click', this.hideEnlargedPhoto.bind(this));
        this.deleteButton.addEventListener('click', this.confirmDelete.bind(this));
    }

    showEnlargedPhoto() {
        this.enlargedPhotoContainer.style.display = 'flex';
    }

    hideEnlargedPhoto() {
        this.enlargedPhotoContainer.style.display = 'none';
    }

    confirmDelete() {
        if (confirm('Are you sure you want to delete this photo?')) {
            this.deletePhoto();
        }
    }

    deletePhoto() {
        // If there's no ID, it's a new photo that hasn't been saved yet, so just remove it from the DOM
        if (!this.photoId) {
            this.remove();
            return;
        }

        // If there's an ID, it's an existing photo that needs to be deleted
        fetch(`/diary/photos/${this.photoId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    this.remove(); // Remove the element from the DOM
                } else {
                    alert('Failed to delete the photo.');
                }
            })
            .catch(error => {
                console.error('Error deleting photo:', error);
                alert('An error occurred while deleting the photo.');
            });
    }
}

customElements.define('photo-component', PhotoComponent);
