export class ChartRenderer {
  constructor(chartElementId) {
    this.chartElementId = chartElementId;
    this.chartInstance = null;
  }

  renderChart(labels, datasets) {
    const ctx = document.getElementById(this.chartElementId).getContext("2d");

    if (this.chartInstance) {
      this.chartInstance.destroy(); // Clear previous chart
    }

    this.chartInstance = new Chart(ctx, {
      type: "line",
      data: {
        labels: labels,
        datasets: datasets,
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });

    console.log("Chart rendered with datasets:", datasets);
  }

  processAndRenderData(trendData) {
    const labels = trendData.map((item) => item.date);
    const categories = Object.keys(trendData[0]).filter(
      (key) => key !== "date"
    );
    const datasets = categories.map((category) => {
      return {
        label: category,
        data: trendData.map((item) => item[category]),
        borderColor: this.getRandomColor(),
        borderWidth: 1,
      };
    });

    console.log("Processed trend data for rendering:", { labels, datasets });
    this.renderChart(labels, datasets);
  }

  getRandomColor() {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}
