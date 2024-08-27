class TabsContainer extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
        this.shadowRoot.innerHTML = `
          <style>
            :host {
              display: block;
              color: var(--nhs-white);
            }
            
            ::slotted(div[slot="tab"]) {
              display: inline-block;
              padding: 1rem !important;
              background-color: var(--nhs-bright-blue);
              margin-right: 5px;
              cursor: pointer;
            }
            
            ::slotted(div[slot="tab"].active) {
              background-color: var(--nhs-dark-blue);
            }
            
            ::slotted(div[slot="content"]) {
              display: none;
            }
            
            ::slotted(div[slot="content"].active) {
              display: block;
              padding: 1rem !important;
              background: var(--nhs-white);
              border: 1px solid lightgray;
            }
            
          </style>
          <slot name="tab"></slot>
          <slot name="content"></slot>
        `;
    }

    connectedCallback() {
        const tabs = this.querySelectorAll('div[slot="tab"]');
        const contents = this.querySelectorAll('div[slot="content"]');

        // Add event listeners to the tabs
        tabs.forEach((tab, index) => {
            tab.addEventListener('click', () => {
                tabs.forEach((t, i) => {
                    t.classList.remove('active');
                    contents[i].classList.remove('active');
                });
                tab.classList.add('active');
                contents[index].classList.add('active');
            });

            // Set the first tab as active
            if (index === 0) {
                tab.classList.add('active');
                contents[index].classList.add('active');
            }
        });
    }
}

customElements.define('tabs-container', TabsContainer);