document.addEventListener('DOMContentLoaded', () => {
    let results;

    function getTitles() {
        fetch('/bin/titles', {
            method: 'GET'
        })
            .then(response => response.json())
            .then(data => {
                // console.log('/bin/hashtags');
                results = data;
            })
            .finally(() => {
                renderTitles(results);
            });
    }

    function renderTitles(results) {
        const titles = document.querySelector('.titles');
        results.forEach(result => {
            const title = document.createElement('div');
            title.className = 'title';
            title.textContent = result.title;
            titles.appendChild(title);
        });
    }

    getTitles();

});
