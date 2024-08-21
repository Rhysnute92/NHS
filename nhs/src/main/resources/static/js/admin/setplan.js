document.addEventListener('DOMContentLoaded', getTasks(0));
document.getElementById("newTask").addEventListener("click", addTask);

function getTasks(i){
    fetch(`/tasks`)
    .then(response => response.json())
    .then(data => {
            var taskOptions = document.getElementById(`tasks-${i}`);
            data.forEach((task) => {
                var taskItem = document.createElement('option');
                var taskId = task.id;
                taskItem.setAttribute("value", taskId);
                taskItem.innerHTML = `${task.name}`;
                taskOptions.appendChild(taskItem);
        })
    })
}

function addTask(){
    const container = document.getElementById("selects");
    const index = container.querySelectorAll('.taskSelect').length;
    var select = document.createElement('select');
    select.classList.add('taskSelect');
    select.setAttribute("id", `tasks-${index}`);
    select.setAttribute("name", `taskList[${index}]`);
    select.setAttribute("form", "newplan");
    var selectLabel = document.createElement('label');
    selectLabel.setAttribute("for", `tasks-${index}`);
    selectLabel.innerHTML = "Select task: ";
    container.appendChild(selectLabel);
    container.appendChild(select);
    getTasks(index);
}