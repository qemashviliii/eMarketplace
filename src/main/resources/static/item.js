const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('id');

async function loadItemDetails() {
    if (!itemId) {
        document.getElementById('detailsContainer').innerHTML = "<h1>Invalid request</h1>";
        return;
    }

    try {
        const response = await fetch(`/market/${itemId}`);
        if (response.ok) {
            const item = await response.json();

            document.getElementById('itemImg').src = item.photoUrl;
            document.getElementById('itemTitle').innerText = item.name;
            document.getElementById('itemPrice').innerText = item.price + '$';

            const date = new Date(item.submissionTime);
            document.getElementById('itemDate').innerText = date.toLocaleString();

            document.getElementById('itemDescription').innerText = item.description;
        } else {
            document.getElementById('detailsContainer').innerHTML = "<h1>Item not found!</h1><a href='index.html'>Back to home</a>";
        }
    } catch (error) {
        console.error(error);
        document.getElementById('detailsContainer').innerHTML = "<h1>Error loading details</h1>";
    }
}

window.addEventListener('DOMContentLoaded', () => {
    loadItemDetails();
});