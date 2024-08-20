// Get the canvas context
const ctx = document.getElementById('patientChart').getContext('2d');

// Initialize the Chart.js chart with empty data
const myChart = new Chart(ctx, {
    type: 'line',  // Set the type of chart
    data: {
        labels: [],
        datasets: [{
            label: 'Measurement',
            data: [],
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            fill: false
        }]
    },
    options: {
        responsive: true,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Date'
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Measurement Value'
                }
            }
        }
    }
});

async function updateChart() {
    try {
        // Get selected measurement type and date range
        const measurementType = document.getElementById('measurementType').value;
        const startDate = document.getElementById('startDate').value;
        const endDate = document.getElementById('endDate').value;

        // Build the query string with type and date range
        let query = `type=${measurementType}`.toLowerCase();
        if (startDate) {
            query += `&startDate=${startDate}`;
        }
        if (endDate) {
            query += `&endDate=${endDate}`;
        }

        // Fetch data from the server
        const response = await fetch(`/patientprofile/statistics/measurements?${query}`);

        if (!response.ok) {
            console.error(`Server error: ${response.statusText}`);
            return;
        }

        const measurements = await response.json();

        if (!measurements || measurements.length === 0) {
            console.warn('No data found for the selected criteria.');
            return;
        }

        // Extract labels (dates) and data (measurement values) from the data
        const labels = measurements.map(m => m.date);
        const data = measurements.map(m => m.value);

        // Update the chart labels and dataset with the new data
        myChart.data.labels = labels;
        myChart.data.datasets[0].label = measurementType;
        myChart.data.datasets[0].data = data;

        // Update the chart
        myChart.update();

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

document.getElementById('updateChartButton').addEventListener('click', updateChart);

// Load chart initially with default measurement type and date range
document.addEventListener('DOMContentLoaded', () => {
    updateChart();
});