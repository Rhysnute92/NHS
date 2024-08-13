/**
 * Checks if the current view is in mobile mode based on the window's inner width.
 *
 * @return {boolean} True if the view is in mobile mode, false otherwise.
 */
export function detectMobileView() {
  return window.innerWidth <= 768;
}
