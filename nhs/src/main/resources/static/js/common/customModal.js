/**
 *
 * A modal component that can be opened and closed by clicking a button.
 * The modal content, title and button element are customizable using slots.
 *
 * Usage Example:
 * ```html
 * <custom-modal>
 *   <button slot="open-btn">Open Modal</button>
 *   <span slot="title">Modal Title</span>
 *   <p slot="content">This is the modal content.</p>
 * </custom-modal>
 * ```
 *
 * HTML Structure:
 * - `slot="open-btn"`: Button to trigger the modal to open.
 * - `slot="title"`: Slot for modal title.
 * - `slot="content"`: Slot for modal content.
 *
 *
 * Methods:
 * - `show()`: Opens the modal.
 * - `hide()`: Closes the modal.
 */
class CustomModal extends HTMLElement {
    constructor() {
        super();

        // Attach a shadow DOM to the element
        this.attachShadow({ mode: 'open' });

        const template = document.createElement('template');
        template.innerHTML = `
          <link rel="stylesheet" href="/css/components/customModal.css">
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
