const urlParams =
new URLSearchParams(window.location.search);

const itemId = urlParams.get('id');

async function loadItemDetails() {

    if (!itemId) {

        document.getElementById(
            'detailsContainer'
        ).innerHTML = '<h1>Invalid item</h1>';

        return;
    }

    try {

        const response =
        await fetch(`/market/${itemId}`);

        if (!response.ok) {

            throw new Error('Not found');
        }

        const item = await response.json();

        document.getElementById(
            'itemImg'
        ).src =
        item.photoUrl || 'placeholder.png';

        document.getElementById(
            'itemTitle'
        ).innerText = item.name;

        document.getElementById(
            'itemPrice'
        ).innerText = item.price + '$';

        document.getElementById(
            'itemDescription'
        ).innerText = item.description;

        document.getElementById(
            'itemSeller'
        ).innerText =
        `Seller: ${item.username}`;

        const date =
        new Date(item.submissionTime);

        document.getElementById(
            'itemDate'
        ).innerText =
        date.toLocaleString();

    } catch (error) {

        console.error(error);

        document.getElementById(
            'detailsContainer'
        ).innerHTML =
        '<h1>Error loading item</h1>';
    }
}

window.addEventListener(
    'DOMContentLoaded',
    loadItemDetails
);