package com.example.registrationapp.Service;

import com.example.registrationapp.model.Users;
import com.example.registrationapp.repository.JpaApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private final JpaApp usersRepository;

    @Autowired
    public UsersService(JpaApp usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Users updateUser(Long id, Users userDetails) {
        Users user = usersRepository.getReferenceById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAddress(userDetails.getAddress());
        return usersRepository.save(user);
    }


    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void registerUser(Users user) {

    }

    public Users saveUser(Users user) {
        return usersRepository.save(user);

    }
    public Users getUserByEmailId(String email, String password){
        return usersRepository.findByEmailAndPassword( email,  password);
    }

    public void deleteAllUsers() {
    }
}


