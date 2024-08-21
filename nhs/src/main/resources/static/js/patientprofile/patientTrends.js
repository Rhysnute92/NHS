// Get the canvas context
const ctx = document.getElementById('patientChart').getContext('2d');

// Uses Chart.js to create the chart with a line for each location (e.g. for arm showing wrist, elbow, etc.)
// Initialise the chart with empty data
const patientChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [],
        datasets: []
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

        // Extract unique dates for the x-axis labels
        const labels = [...new Set(measurements.map(m => m.date))];

        // Group data by location
        const locationDataMap = {};
        measurements.forEach(m => {
            const location = m.location;

            // Create array for this location if it doesn't exist
            if (!locationDataMap[location]) {
                locationDataMap[location] = [];
            }

            // Push the measurement value for this date and location
            locationDataMap[location].push({ date: m.date, value: m.value });
        });

        // Clear existing datasets
        patientChart.data.datasets = [];

        // Create a dataset for each location
        Object.keys(locationDataMap).forEach(location => {
            const locationData = locationDataMap[location];

            // Create an array for the measurement values aligned with the dates
            const data = labels.map(label => {
                const measurement = locationData.find(m => m.date === label);
                return measurement ? measurement.value : null;
            });

            patientChart.data.datasets.push({
                label: location,
                data: data,
                borderColor: getRandomColor(),
                backgroundColor: 'rgba(0, 0, 0, 0)',
                fill: false
            });
        });

        patientChart.data.labels = labels;
        patientChart.update();

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

function getRandomColor() {
    // Generate a random color for each line
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    return `rgba(${r}, ${g}, ${b}, 1)`;
}

document.getElementById('updateChartButton').addEventListener('click', updateChart);

// Load chart initially with default measurement type and date range
document.addEventListener('DOMContentLoaded', () => {
    updateChart();
});
