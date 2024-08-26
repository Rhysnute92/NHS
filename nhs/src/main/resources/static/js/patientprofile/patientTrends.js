// Declare charts object globally to store all chart instances
let charts = {};

// Function to get the canvas context for a given chart type
function getChartContext(chartType) {
    const canvasId = `${chartType}Chart`;
    const canvas = document.getElementById(canvasId);

    if (canvas) {
        return canvas.getContext('2d');
    } else {
        console.error(`Canvas element for chart ${canvasId} not found`);
        return null;
    }
}

function initialiseChart(chartType) {
    const ctx = getChartContext(chartType);

    if (ctx) {
        console.log(`Initialising chart: ${chartType}`);
        charts[`${chartType}Chart`] = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [],
                datasets: []
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
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
                            text: 'Value'
                        }
                    }
                }
            }
        });
    } else {
        console.error(`Failed to initialise chart: ${chartType}`);
    }
}

// Function to fetch data and update the chart with new data
async function updateChart(chartType) {
    try {
        let startDate, endDate;
        let query = '';
        let endpoint = '';

        // Handle date inputs for each chart type
        switch (chartType) {
            case 'mood':
                startDate = document.getElementById('startDateMood').value;
                endDate = document.getElementById('endDateMood').value;
                query = `?startDate=${startDate}&endDate=${endDate}`;
                endpoint = `/patientprofile/trends/mood${query}`;
                break;
            case 'symptoms':
                startDate = document.getElementById('startDateSymptoms').value;
                endDate = document.getElementById('endDateSymptoms').value;
                query = `?startDate=${startDate}&endDate=${endDate}`;
                const symptomType = document.getElementById('symptomType').value;
                query += `&type=${symptomType}`;
                endpoint = `/patientprofile/trends/symptoms${query}`;
                break;
            case 'measurements':
                startDate = document.getElementById('startDateMeasurements').value;
                endDate = document.getElementById('endDateMeasurements').value;
                query = `?startDate=${startDate}&endDate=${endDate}`;
                const measurementType = document.getElementById('measurementType').value;
                query += `&type=${measurementType}`;
                endpoint = `/patientprofile/trends/measurements${query}`;
                break;
            default:
                return;
        }

        const response = await fetch(endpoint);

        if (!response.ok) {
            console.error(`Server error: ${response.statusText}`);
            return;
        }

        const data = await response.json();

        if (!data || data.length === 0) {
            console.warn('No data found for the selected criteria.');
            charts[`${chartType}Chart`].data.datasets = [];
            charts[`${chartType}Chart`].update();
            return;
        }

        const labels = [...new Set(data.map(item => item.date))];
        const dataMap = {};

        data.forEach(item => {
            const category = item.category || item.location || item.mood;
            if (!dataMap[category]) {
                dataMap[category] = [];
            }
            dataMap[category].push({ date: item.date, value: item.value });
        });

        charts[`${chartType}Chart`].data.datasets = [];

        Object.keys(dataMap).forEach(category => {
            const chartData = labels.map(label => {
                const measurement = dataMap[category].find(item => item.date === label);
                return measurement ? measurement.value : null;
            });

            charts[`${chartType}Chart`].data.datasets.push({
                label: category,
                data: chartData,
                borderColor: getRandomColor(),
                backgroundColor: 'rgba(0, 0, 0, 0)',
                fill: false
            });
        });

        charts[`${chartType}Chart`].data.labels = labels;
        charts[`${chartType}Chart`].update();

    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// Generate a random color for each line
function getRandomColor() {
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    return `rgba(${r}, ${g}, ${b}, 1)`;
}

// Handle tab switching and chart initialisation
function handleTabActivation() {
    const tabs = document.querySelectorAll('tabs-container div[slot="tab"]');
    const tabContents = document.querySelectorAll('.tab-content');

    tabs.forEach((tab, index) => {
        tab.addEventListener('click', () => {
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            tab.classList.add('active');
            tabContents[index].classList.add('active');

            let activeChart = tabContents[index].dataset.chart;

            if (!charts[`${activeChart}Chart`]) {
                initialiseChart(activeChart);
            }

            updateChart(activeChart);
        });
    });

    // Initialise the first chart by default
    tabs[0].classList.add('active');
    tabContents[0].classList.add('active');
    let defaultChartType = tabContents[0].dataset.chart;
    initialiseChart(defaultChartType);
    updateChart(defaultChartType);

    // Add event listeners to dropdowns to update the chart when selection changes
    const symptomTypeDropdown = document.getElementById('symptomType');
    if (symptomTypeDropdown) {
        symptomTypeDropdown.addEventListener('change', () => updateChart('symptoms'));
    }

    const measurementTypeDropdown = document.getElementById('measurementType');
    if (measurementTypeDropdown) {
        measurementTypeDropdown.addEventListener('change', () => updateChart('measurements'));
    }
}

document.addEventListener('DOMContentLoaded', handleTabActivation);
