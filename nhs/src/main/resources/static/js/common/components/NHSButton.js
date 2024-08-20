class NHSButton extends HTMLElement {
  static observedAttributes = ['type', 'disabled'];

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

    this.shadowRoot.querySelector('button').addEventListener('click', () => {
      this.dispatchEvent(new CustomEvent('button-click', {
        bubbles: true,
        composed: true
      }));
    });
  }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'type') {
        this.shadowRoot.querySelector('button').className = newValue;
        } else if (name === 'disabled') {
        this.shadowRoot.querySelector('button').disabled = newValue !== null;
        }
    }
}

customElements.define('nhs-button', NHSButton);
