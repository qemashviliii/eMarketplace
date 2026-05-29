let currentPage = 0;

function checkAuth() {

    const username =
    localStorage.getItem('username');

    if (username) {

        document.getElementById(
            'usernameDisplay'
        ).innerText = `Hello, ${username}`;

        document.getElementById(
            'loginBtn'
        ).style.display = 'none';

        document.getElementById(
            'registerBtn'
        ).style.display = 'none';

    } else {

        document.getElementById(
            'logoutBtn'
        ).style.display = 'none';
    }
}

function logout() {

    localStorage.clear();

    window.location.reload();
}

async function loadItems(page) {

    try {

        const sort =
        document.getElementById(
            'sortSelect'
        ).value;

        const response = await fetch(
            `/market?page=${page}&sort=${sort}`
        );

        if (!response.ok) {
            throw new Error('Failed');
        }

        const data = await response.json();

        document.getElementById(
            'totalItems'
        ).innerText = data.totalElements;

        document.getElementById(
            'pageNumber'
        ).innerText = data.number + 1;

        document.getElementById(
            'prevBtn'
        ).disabled = data.first;

        document.getElementById(
            'nextBtn'
        ).disabled = data.last;

        const grid =
        document.getElementById('itemsGrid');

        grid.innerHTML = '';

        data.content.forEach(item => {

            grid.innerHTML += `

                <div class="card">

                    <div
                        class="img-container"
                        onclick="location.href='item.html?id=${item.id}'"
                    >

                        <img
                            src="${item.photoUrl || 'placeholder.png'}"
                            alt="${item.name}"
                        >

                    </div>

                    <h3>${item.name}</h3>

                    <p class="price">
                        ${item.price}$
                    </p>

                    <p class="seller">
                        Seller: ${item.username}
                    </p>

                </div>
            `;
        });

    } catch (error) {

        console.error(error);

        document.getElementById(
            'itemsGrid'
        ).innerHTML =
        `<p class="error-text">
            Error loading items
         </p>`;
    }
}

document.getElementById(
    'sortSelect'
).addEventListener('change', () => {

    currentPage = 0;

    loadItems(currentPage);
});

document.getElementById(
    'prevBtn'
).addEventListener('click', () => {

    currentPage--;

    loadItems(currentPage);
});

document.getElementById(
    'nextBtn'
).addEventListener('click', () => {

    currentPage++;

    loadItems(currentPage);
});

window.addEventListener('DOMContentLoaded', () => {

    checkAuth();

    loadItems(currentPage);
});