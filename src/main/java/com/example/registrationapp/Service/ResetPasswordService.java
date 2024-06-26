package com.example.registrationapp.Service;
import com.example.registrationapp.model.Users;
import com.example.registrationapp.repository.JpaApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResetPasswordService {

    @Autowired
    private JpaApp userRepository;

    @Transactional
    public void resetPassword(String token, String newPassword) {
        // Find the user by the reset token
        Users user = userRepository.findByResetToken(token);
        if (user != null) {
            // Update the user's password and clear the reset token
            user.setPassword(newPassword);
            user.setResetToken(null); // Reset the token after password change
            userRepository.save(user);
        } else {
            // Handle case where token is invalid or expired
            // You can throw an exception, return a specific response, or log the event
        }
    }
}
