package com.example.eMarketplace.Service;

import com.example.eMarketplace.Entity.User;
import com.example.eMarketplace.Repository.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(
            String username,
            String email,
            String password,
            LocalDate birthday
    ) {

        validate(username, email, birthday);

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthday(birthday);

        userRepository.save(user);
    }

    public User login(String login, String password) {

        User user = userRepository
                .findByUsernameOrEmail(login, login)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    private void validate(
            String username,
            String email,
            LocalDate birthday
    ) {

        if (!username.matches("^[a-zA-Z0-9]{8,20}$")) {
            throw new RuntimeException(
                    "Username must be 8-20 letters/numbers"
            );
        }

        EmailValidator validator =
                EmailValidator.getInstance();

        if (!validator.isValid(email)) {
            throw new RuntimeException("Invalid email");
        }

        if (birthday.isAfter(
                LocalDate.now().minusYears(13)
        )) {
            throw new RuntimeException(
                    "User must be older than 13"
            );
        }
    }
}
