document.getElementById('searchType').addEventListener("change", changeSearchForm);

//Triggers function on select value change
document.getElementById('searchType').value = interactiveType
changeSearchForm()

function changeSearchForm(){
    var select = document.getElementById('searchType');
    var type = select.options[select.selectedIndex].value;
    var form = document.getElementById('searchForm'); 

    if (type == "NHS number"){
        form.innerHTML = `
        <label for="patientNhsNumber">Patient NHS number:</label>
        <input type="text" id="patientNhsNumber" name="patientNhsNumber">
        <input type="submit" value="Search" class="search_button"/>
        `;
    } else if (type == "Patient data"){
        form.innerHTML = `
        <label for="patientName">Patient First Name:</label>
        <input type="text" id="patientName" name="patientName">
        <label for="patientLastName">Patient Last Name:</label>
        <input type="text" id="patientLastName" name="patientLastName">
        <label for="patientEmail">Patient email:</label>
        <input type="text" id="patientEmail" name="patientEmail">
        <label for="patientDOB">Patient date of birth:</label>
        <input type="date" id="patientDOB" name="patientDOB">
        <input type="submit" value="Search" class="search_button"/>
        `;
    }
}