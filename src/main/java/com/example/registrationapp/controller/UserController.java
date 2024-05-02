package com.example.registrationapp.controller;
import com.example.registrationapp.Service.EmailService;
import com.example.registrationapp.Service.ForgotPasswordService;
import com.example.registrationapp.Service.ResetPasswordService;
import com.example.registrationapp.Service.UsersService;
import com.example.registrationapp.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UsersService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/index")
    public String listUsers(Model model) { //display users in index.jsp
        List<Users> users = userService.getAllUsers();
        model.addAttribute("Users", users);
        return "index";
    }


    @PostMapping("/Users")
    public String saveUser(Users user) {
        userService.saveUser(user);
        return "redirect:/Users";
    }


    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/Users";
    }

    @PostMapping("/users/deleteAll")
    public String deleteAllUsers() {
        userService.deleteAllUsers();
        return "redirect:/Users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Users user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new Users());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(Users user, Model model) {
        Users loggedInUser = userService.getUserByEmailId(user.getEmail(), user.getPassword());
        if (loggedInUser != null) {
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgotpassword")
    public String showForgotPasswordForm(Model model) {
        return "forgotpassword"; // Assuming "forgot-password.html" is the name of your forgot password page
    }

    @GetMapping("/forgotpasswordsucess")
    public String showForgotPasswordSuccessPage() {

        return "forgotpasswordsucess";
    }


    @PostMapping("/forgotpassword")
    public String forgotPassword(@RequestParam("email") String email) {
        // Call the service to handle the forgot password logic
        String resetToken = forgotPasswordService.forgotPassword(email);

        // If resetToken is null, it means the email doesn't exist in the database
        if (resetToken == null) {
            // Handle case where email is not found
            // You can throw an exception, return a specific response, or log the event
            return "redirect:/forgotpassword?error=emailNotFound";
        }

        // Construct the reset password URL using ServletUriComponentsBuilder
        String resetPasswordUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resetpassword")
                .queryParam("token", resetToken)
                .toUriString();

        // Send an email with the reset password link
        String emailContent = "Please click the following link to reset your password: " + resetPasswordUrl;
        emailService.sendEmail(email, "Password Reset", emailContent);

        return "redirect:/forgotpasswordsucess"; // Redirect to a success page
    }

    @Autowired
    private ResetPasswordService resetPasswordService;

    @GetMapping("/resetpassword")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "resetpassword";
    }

    @PostMapping("/resetpassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword) {
        // Add validation for password matching if needed
        if (!password.equals(confirmPassword)) {
            // Handle password mismatch error
            return "redirect:/resetpassword?error=passwordMismatch";
        }
        resetPasswordService.resetPassword(token, password);
        return "redirect:/login"; // Redirect to the login page after successful password reset
    }

        // You can add additional mappings or error handling as needed
    }

