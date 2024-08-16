class CustomModal extends HTMLElement {
    constructor() {
        super();

        // Attach a shadow DOM to the element
        this.attachShadow({ mode: 'open' });

        const template = document.createElement('template');
        template.innerHTML = `
          <link rel="stylesheet" href="/css/diary/customModal.css">
          <span class="open-btn">
            <slot name="open-btn"></slot>
          </span>
          <div class="modal">
            <div class="modal-content">
                <div class="modal-header">
                  <h2>
                    <slot name="title"></slot>
                  </h2>
                  <button type="button" class="close-btn">
                    &times;
                  </button>
                </div>
                <slot name="content"></slot>
            </div>
          </div>
        `;

        this.shadowRoot.appendChild(template.content.cloneNode(true));
        this.modal = this.shadowRoot.querySelector('.modal');

        // Event listeners
        this.shadowRoot.querySelector('.close-btn').addEventListener('click', () => {
            this.hide();
        });

        this.shadowRoot.querySelector('.open-btn').addEventListener('click', (event) => {
            this.show();
        });

        // Close the modal if the user clicks outside of it
        this.modal.addEventListener('click', (event) => {
            if (event.target === this.modal) {
                this.hide();
            }
        });
    }

    show() {
        this.modal.style.display = 'flex';
    }

    hide() {
        this.modal.style.display = 'none';
    }
}

customElements.define('custom-modal', CustomModal);
