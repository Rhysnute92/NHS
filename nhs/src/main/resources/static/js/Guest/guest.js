document.addEventListener('DOMContentLoaded', function() {
    function showInfo(infoId) {

        document.querySelectorAll('.info-box').forEach(function(box) {
            box.style.display = 'none';
        });

        var selectedBox = document.getElementById(infoId);
        if (selectedBox) {
            selectedBox.style.display = 'block';
        }
    }

    document.getElementById('show-info1').addEventListener('click', function() {
        showInfo('info1');
    });

    document.getElementById('show-info2').addEventListener('click', function() {
        showInfo('info2');
    });

    document.getElementById('show-info3').addEventListener('click', function() {
        showInfo('info3');
    });

    showInfo('info1');
});
