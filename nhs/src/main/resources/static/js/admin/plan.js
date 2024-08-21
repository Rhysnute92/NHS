document.addEventListener('DOMContentLoaded', getUserTasks(userId))

function getUserTasks(id){
    fetch(`/usertask/${id}`)
    .then(response => response.json())
    .then(data => {
        if (Object.keys(data).length > 0){
            var tableBody = document.getElementById("taskBody");
            data.forEach((userTask) => {
                var taskItem = document.createElement('tr');
                var taskItemName = document.createElement('td');
                taskItemName.innerHTML = `${userTask.task.name}`;
                taskItem.appendChild(taskItemName);
                var taskItemFrequency = document.createElement('td');
                taskItemFrequency.innerHTML = `${userTask.task.periodicity}`;
                taskItem.appendChild(taskItemFrequency);
                tableBody.appendChild(taskItem);
            })
        }
    })
}