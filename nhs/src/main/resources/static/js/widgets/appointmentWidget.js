// widget.js
document.addEventListener('DOMContentLoaded', function () {
    const widgetContainer = document.getElementById('widget-container');

    // Create a widget element
    const widget = document.createElement('div');
    widget.className = 'widget';
    widget.textContent = 'This is a dynamically created widget';

    // Append the widget to the container
    widgetContainer.appendChild(widget);
});
