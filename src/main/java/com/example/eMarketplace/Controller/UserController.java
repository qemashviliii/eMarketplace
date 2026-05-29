package com.example.eMarketplace.Controller;


import com.example.eMarketplace.Entity.User;
import com.example.eMarketplace.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(

            @RequestParam String username,

            @RequestParam String email,

            @RequestParam String password,

            @RequestParam String birthday
    ) {

        userService.register(

                username,
                email,
                password,
                LocalDate.parse(birthday)
        );

        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(

            @RequestParam String login,

            @RequestParam String password
    ) {

        return ResponseEntity.ok(
                userService.login(login, password)
        );
    }
}
