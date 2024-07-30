INSERT INTO widgets (id, title, html_content, data)
VALUES (
        'task-completion',
        'Task Completion',
        '<div id="taskCompletion-widget" class="widget"><div class="circle-container"><div class="circle"><span class="percentage"></span></div></div><button class="complete-task-button">Complete Task</button></div>',
        '{"completedTasks": 5, "totalTasks": 10}'
    );