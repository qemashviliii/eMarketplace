document.getElementById('loginForm')
    .addEventListener('submit', async (e) => {

    e.preventDefault();

    const formData = new FormData();

    formData.append(
        'login',
        document.getElementById('login').value
    );

    formData.append(
        'password',
        document.getElementById('password').value
    );

    try {

        const response = await fetch('/users/login', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {

            const user = await response.json();

            localStorage.setItem(
                'userId',
                user.id
            );

            localStorage.setItem(
                'username',
                user.username
            );

            alert('Login successful');

            window.location.href = 'index.html';

        } else {

            const text = await response.text();

            alert(text);
        }

    } catch (error) {

        console.error(error);

        alert('Server connection failed');
    }
});