package com.lcwd.user.service.controller;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> craeteUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallbackMethod")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> ratingHotelFallbackMethod(String userId, Exception ex) {
        log.info("Fallback is executed because services is down : {}", ex.getMessage());
        User user = User.builder().email("dummy@dummy.com").name("dummy").userId("12345")
                .about("Dummy about").build();
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserInfo")
    public String getUsers(@AuthenticationPrincipal Jwt jwt) {

        // Extract claims from the token
        String userId = jwt.getSubject();                    // Auth0 user ID e.g. "auth0|64abc..."
        String email = jwt.getClaimAsString("email");       // user's email
        String name = jwt.getClaimAsString("name");        // user's name
        List<String> roles = jwt.getClaimAsStringList("permissions"); // Auth0 permissions

        System.out.println("Request from user: " + userId);
        return "Hello " + name;
    }

}
