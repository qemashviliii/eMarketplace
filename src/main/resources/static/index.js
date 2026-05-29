let currentPage = 0;

async function loadItems(page) {
    try {
        const response = await fetch(`/market?page=${page}`);
        if (!response.ok) throw new Error("Failed to fetch data");

        const data = await response.json();

        document.getElementById('totalItems').innerText = data.totalElements;
        document.getElementById('pageNumber').innerText = data.number + 1;

        document.getElementById('prevBtn').disabled = data.first;
        document.getElementById('nextBtn').disabled = data.last;

        const grid = document.getElementById('itemsGrid');
        grid.innerHTML = '';

        data.content.forEach(item => {
            grid.innerHTML += `
                <div class="card">
                    <div class="img-container" onclick="location.href='item.html?id=${item.id}'">
                        <img src="${item.photoUrl}" alt="${item.name}">
                    </div>
                    <h3>${item.name}</h3>
                    <p class="price">${item.price}\$</p>
                </div>
            `;
        });
    } catch (error) {
        console.error(error);
        document.getElementById('itemsGrid').innerHTML = `<p style="color:red; text-align:center; width:100%;">Error loading items.</p>`;
    }
}

function changePage(direction) {
    currentPage += direction;
    loadItems(currentPage);
}

window.addEventListener('DOMContentLoaded', () => {
    loadItems(currentPage);
});