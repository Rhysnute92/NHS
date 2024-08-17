class PhotoComponent extends HTMLElement {
    // Watch for changes to the URL and photo ID attributes
    static observedAttributes = ['url', 'photo-id'];

    constructor() {
        super();
        this.attachShadow({ mode: 'open' });

        this.shadowRoot.innerHTML = `
            <link rel="stylesheet" href="/css/diary/photoComponent.css">
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
        this.enlargedPhotoElement = this.enlargedPhotoContainer.querySelector('img');
        this.closeButton = this.shadowRoot.querySelector('.close-button');
    }

    static get observedAttributes() {
        return ['url', 'photo-id'];
    }

    // Update the image sources when the URL attribute changes
    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'url') {
            this.photoElement.src = newValue;
            this.enlargedPhotoElement.src = newValue;
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
        // const windowWidth = window.innerWidth;
        // const windowHeight = window.innerHeight;
        // this.enlargedPhotoContainer.style.width = windowWidth + 'px';
        // this.enlargedPhotoContainer.style.height = windowHeight + 'px';
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
