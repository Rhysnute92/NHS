class PhotoContainer extends HTMLElement {

  static observedAttributes = ['photos'];

  static get observedAttributes() {
    return ['photos'];
  }

  constructor() {
    super();
    this.attachShadow({ mode: 'open' });

    this.shadowRoot.innerHTML = `
      <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }
        
        :host {
            height: 100%;
            overflow-y: auto;
        }
   
        .photo-container {
            overflow-y: auto;
            overflow-x: hidden;
            height: 100%;
            padding: 1rem 0;
        }
        
        .photo-grid {
            background-color: var(--nhs-white);
            display: grid;
            grid-auto-columns: 1fr;
            grid-template-columns: repeat(5, 1fr);
            gap: 0.5rem;
        }
        
        @media (max-width: 768px) {
            .photo-grid {
                grid-template-columns: repeat(3, 1fr);
            }
        }
      </style>
      <div class="photo-container">
        <div class="photo-grid"></div>
      </div>
    `;

    this.photoContainer = this.shadowRoot.querySelector('.photo-container');
    this.photoGrid = this.shadowRoot.querySelector('.photo-grid');
  }

  attributeChangedCallback(name, oldValue, newValue) {
    // Update the photos when the photos attribute changes
    if (name === 'photos') {
      try {
        const parsedPhotos = JSON.parse(newValue);
        this.updatePhotos(parsedPhotos);
      } catch (error) {
        console.error('Invalid JSON for photos:', error);
      }
      if (newValue === '[]') {
        this.photoGrid.style.display = 'none';
      } else {
        this.photoGrid.style.display = 'grid';
      }
    }
  }

  // Called when the element is added to the DOM
  connectedCallback() {
    const photos = this.getAttribute('photos');
    if (photos) {
      try {
        // Parse the initial photos attribute from the server
        const parsedPhotos = JSON.parse(photos);
        this.updatePhotos(parsedPhotos);
      } catch (error) {
        console.error('Invalid JSON for initial photos:', error);
      }
    } else {
        this.photoGrid.style.display = 'none';
    }
  }

  updatePhotos(photoObjects) {
    // Clear existing photos
    this.photoGrid.childNodes.forEach((child) => {
        if (!child.id) {
          child.remove();
        }
      });
    this.photoGrid.innerHTML = '';

    // Create a photo-component for each photo object
    if (Array.isArray(photoObjects)) {
      photoObjects.forEach((photo) => {
        // Check that the photo object has a valid URL
        if (photo && photo.url) {
          const photoComponent = document.createElement('photo-component');

          // Check if the URL is a blob URL
          const isBlobUrl = photo.url.startsWith('blob:');

          // Use /files/ as URL root only if it's not a blob URL so it doesn't break the link
          const fullUrl = isBlobUrl ? photo.url : `/files/${photo.url.trim()}`;

          photoComponent.setAttribute('url', fullUrl);
          photoComponent.setAttribute('photo-id', photo.id);
          this.photoGrid.appendChild(photoComponent);
        }
      });
    } else {
      console.error('Photos should be an array of objects');
    }
  }
}

customElements.define('photo-container', PhotoContainer);
