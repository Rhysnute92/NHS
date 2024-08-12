/**
 * Creates the checkmark SVG element.
 * @return {HTMLElement} The checkmark SVG element.
 */
export function createCheckCircle() {
  const checkCircleWrapper = document.createElement("div");
  checkCircleWrapper.classList.add("check-circle-wrapper");

  const checkmarkSVG = `
      <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
        <circle class="checkmark__circle" cx="26" cy="26" r="25" fill="none"/>
        <path class="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
      </svg>
    `;

  checkCircleWrapper.innerHTML = checkmarkSVG;

  return checkCircleWrapper;
}

/**
 * Toggles the check circle's completed state.
 * @param {HTMLElement} checkCircleElement The element containing the check circle SVG.
 */
export function toggleCheckCircle(checkCircleElement) {
  const checkmark = checkCircleElement.querySelector(".checkmark");
  const circle = checkCircleElement.querySelector(".checkmark__circle");
  const check = checkCircleElement.querySelector(".checkmark__check");

  if (checkmark && circle && check) {
    checkCircleElement.classList.toggle("checked");
    circle.style.strokeDashoffset = checkCircleElement.classList.contains(
      "checked"
    )
      ? "0"
      : "166";
    check.style.strokeDashoffset = checkCircleElement.classList.contains(
      "checked"
    )
      ? "0"
      : "48";
      
  } else {
    console.error("Required elements not found.");
  }
}
