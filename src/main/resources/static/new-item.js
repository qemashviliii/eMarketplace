document.getElementById('itemForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append('name', document.getElementById('name').value);
    formData.append('price', document.getElementById('price').value);
    formData.append('description', document.getElementById('description').value);

    const photoInput = document.getElementById('photo');
    if (photoInput.files[0]) {
        formData.append('photo', photoInput.files[0]);
    }

    try {
        const response = await fetch('/market', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            alert('Item added successfully!');
            window.location.href = 'index.html';
        } else {
            const errorText = await response.text();
            alert('Error: ' + errorText);
        }
    } catch (error) {
        console.error(error);
        alert('Connection to server failed.');
    }
});