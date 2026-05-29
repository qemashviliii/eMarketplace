document.getElementById('itemForm')
    .addEventListener('submit', async (e) => {

    e.preventDefault();

    const userId =
    localStorage.getItem('userId');

    if (!userId) {

        alert('Please login first');

        window.location.href = 'login.html';

        return;
    }

    const formData = new FormData();

    formData.append(
        'name',
        document.getElementById('name').value
    );

    formData.append(
        'price',
        document.getElementById('price').value
    );

    formData.append(
        'description',
        document.getElementById('description').value
    );

    formData.append(
        'userId',
        userId
    );

    const photoInput =
    document.getElementById('photo');

    if (photoInput.files[0]) {

        formData.append(
            'photo',
            photoInput.files[0]
        );
    }

    try {

        const response = await fetch('/market', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {

            alert('Item created');

            window.location.href = 'index.html';

        } else {

            const text = await response.text();

            alert(text);
        }

    } catch (error) {

        console.error(error);

        alert('Server error');
    }
});