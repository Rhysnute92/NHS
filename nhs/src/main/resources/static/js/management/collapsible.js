export function initializeCollapsible() {
  const collapsibleHeaders = document.querySelectorAll(".collapsible-header");
  collapsibleHeaders.forEach((header) => {
    header.addEventListener("click", toggleCollapsible);
  });
}

function toggleCollapsible(event) {
  const header = event.currentTarget;
  const content = header.nextElementSibling;
  content.classList.toggle("expanded");
  header.classList.toggle("expanded"); //TODO: fix this
}
