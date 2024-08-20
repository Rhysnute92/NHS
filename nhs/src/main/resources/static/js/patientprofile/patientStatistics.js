// Initialize empty data structure for the chart
const data = {
    labels: [], // Dates will go here
    datasets: [
        {
            label: 'Weight (kg)',
            data: [],
            borderColor: 'rgba(255, 99, 132, 1)',
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            fill: false,
            tension: 0.1
        },
        {
            label: 'Arm Circumference (cm)',
            data: [],
            borderColor: 'rgba(54, 162, 235, 1)',
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            fill: false,
            tension: 0.1
        },
        {
            label: 'Leg Circumference (cm)',
            data: [],
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            fill: false,
            tension: 0.1
        }
    ]
};

const config = {
    type: 'line',
    data: data,
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Patient Measurements Over Time'
            }
        },
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
                    text: 'Measurement'
                },
                beginAtZero: false
            }
        }
    }
};

async function fetchData() {
    try {
        const response = await fetch('/patientprofile/statistics/measurements'); // Replace with your server URL
        const measurements = await response.json();

        measurements.forEach(measurement => {
            console.log(measurement);
            const date = measurement.date;
            const type = measurement.type;
            const value = measurement.value;

            // Add dates to the labels array if not already present
            if (!data.labels.includes(date)) {
                data.labels.push(date);
            }

            // Assign measurement values to the corresponding dataset
            if (type === 'Weight') {
                data.datasets[0].data.push(value);
            } else if (type === 'Arm Circumference') {
                data.datasets[1].data.push(value);
            } else if (type === 'Leg Circumference') {
                data.datasets[2].data.push(value);
            }
        });

        // Update the chart with the new data
        myChart.update();

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Create the chart
const myChart = new Chart(
    document.getElementById('patientChart'),
    config
);

document.addEventListener('DOMContentLoaded', () => {
    fetchData();
});