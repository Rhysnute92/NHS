document.addEventListener('DOMContentLoaded', (event) => {
    const dateElements = document.querySelectorAll('.diary-entry-title');
    dateElements.forEach(element => {
        const dateStr = element.getAttribute('data-date');
        console.log(dateStr)
        const date = new Date(dateStr);
        const options = { weekday: "long", day: '2-digit', month: 'long' };
        const formattedDate = date.toLocaleDateString('en-GB', options);
        element.textContent = formattedDate;
    });
});