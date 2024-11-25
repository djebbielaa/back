package com.Elaa.demo.Controller;

import com.Elaa.demo.Entity.User;
import com.Elaa.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/pending")
    public List<User> getPendingUsers() {
        return userService.getPendingUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Sends a validation email to a user.
     *
     * @param id The ID of the user.
     */
    @PostMapping("/{id}/send-validation-email")
    public void sendValidationEmail(@PathVariable Long id) {
        userService.sendValidationEmail(id);
    }

    /**
     * Validates a user when they click the link in the email.
     *
     * @param id The ID of the user.
     * @return A success message.
     */
    @GetMapping("/validate/{id}")
    public String validateUser(@PathVariable Long id) {
        userService.validateUser(id);
        return "Votre compte a été validé avec succès.";
    }
}
