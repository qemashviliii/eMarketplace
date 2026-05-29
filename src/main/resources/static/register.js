document.getElementById('registerForm')
    .addEventListener('submit', async (e) => {

    e.preventDefault();

    const formData = new FormData();

    formData.append(
        'username',
        document.getElementById('username').value
    );

    formData.append(
        'email',
        document.getElementById('email').value
    );

    formData.append(
        'password',
        document.getElementById('password').value
    );

    formData.append(
        'birthday',
        document.getElementById('birthday').value
    );

    try {

        const response = await fetch('/users/register', {
            method: 'POST',
            body: formData
        });

        const text = await response.text();

        if (response.ok) {

            alert('Registration successful!');

            window.location.href = 'login.html';

        } else {

            alert(text);
        }

    } catch (error) {

        console.error(error);

        alert('Server connection failed');
    }
});