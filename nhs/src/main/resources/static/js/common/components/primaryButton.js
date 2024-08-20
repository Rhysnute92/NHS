class PrimaryButton extends HTMLElement {
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
          background-color: #007f3b;
          box-shadow: 0 4px 0 #00401e;
          color: var(--nhs-white);
          font-size: 1rem;
          font-weight: 600;
          border: none;
          border-radius: 0.25rem;
          cursor: pointer;
          transition: background-color 0.3s;
        }
        
        button:hover {
          background-color: #00662F;
          cursor: pointer;
        }
      </style>
      <button><slot></slot></button>
    `;

    this.shadowRoot.querySelector('button').addEventListener('click', () => {
      this.dispatchEvent(new CustomEvent('primary-button-click', {
        bubbles: true,
        composed: true
      }));
    });
  }
}

customElements.define('primary-button', PrimaryButton);
