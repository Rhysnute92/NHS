class NHSButton extends HTMLElement {
    static observedAttributes = ['class', 'type', 'disabled'];

    constructor() {
        super();

        this.attachShadow({ mode: 'open' });

        this.shadowRoot.innerHTML = `
          <style>
            :host {
              display: inline-block;
            }
            
            button {
              padding: 0.5rem 1rem;
              color: var(--nhs-white);
              font-size: 1rem;
              font-weight: 600;
              border: none;
              border-radius: 0.25rem;
              cursor: pointer;
              transition: background-color 0.3s;
            }
            
            button:hover {
              cursor: pointer;
            }
            
            .primary {
              background-color: #007f3b;
              box-shadow: 0 4px 0 #00401e;
            }
            
            .primary:hover {
              background-color: #00662F;
            }
            
            .secondary {
              background-color: #4c6272;
              box-shadow: 0 4px 0 #263139;
            }
            
            .secondary:hover {
              background-color: #384853;
            }
            
            .reverse {
              background-color: #FFFFFF;
              box-shadow: 0 4px 0 #212B32;
              color: #212B32;
            }
            
            .reverse:hover {
              background-color: #F2F2F2;
            }
            
            .warning {
              background-color: #d5281b;
              box-shadow: 0 4px 0 #6b140e
            }
            
            .warning:hover {
              background-color: #A82015;
            }
        </style>
        <button><slot></slot></button>
    `;

        this.shadowRoot.querySelector('button').addEventListener('click', (event) => {
            this.handleClick(event);
        });
    }

    handleClick(event) {
        // Check if the button type is submit
        if (this.getAttribute('type') === 'submit') {
            // Prevent the default form submission behavior
            event.preventDefault();

            // Find the closest form ancestor
            const form = this.closest('form');

            if (form) {
                // Emit the form's submit event
                const submitEvent = new Event('submit', { bubbles: true, cancelable: true });
                const isNotCanceled = form.dispatchEvent(submitEvent);

                // Manually submit if the submit event wasn't canceled
                if (isNotCanceled) {
                    form.submit();
                }
            }
        }
    }

    attributeChangedCallback(name, oldValue, newValue) {
        const button = this.shadowRoot.querySelector('button');

        // Update the button's attributes based on the custom element's attributes
        if (name === 'type') {
            button.type = newValue;
        } else if (name === 'class') {
            button.className = newValue;
        } else if (name === 'disabled') {
            button.disabled = newValue !== null;
        }
    }
}

customElements.define('nhs-button', NHSButton);
