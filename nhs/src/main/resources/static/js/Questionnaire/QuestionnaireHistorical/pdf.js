const { jsPDF } = window.jspdf;

export function generatePDF() {
    const doc = new jsPDF();

    // PDF Title
    doc.setFontSize(18);
    doc.text("Questionnaire Results", 10, 10);

    // Table generation
    doc.autoTable({
        html: '#questionnaireTable',
        startY: 20,
        theme: 'striped',
    });

    // Save the PDF
    doc.save("questionnaire_results.pdf");
}

window.generatePDF = generatePDF;

window.addEventListener('load', function() {
    const button = document.getElementById("download-pdf");
    if (button) {
        button.addEventListener("click", generatePDF);
    }
});