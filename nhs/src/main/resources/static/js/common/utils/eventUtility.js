export function addEventListener(element, event, handler) {
  element.addEventListener(event, handler);
}

export function toggleClass(element, className) {
  element.classList.toggle(className);
}

export function updateElementDisplay(element, displayStyle) {
  element.style.display = displayStyle;
}
