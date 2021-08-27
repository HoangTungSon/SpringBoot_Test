package com.example.demo5.service;

import com.example.demo5.model.User;
import com.example.demo5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllByEmail(String email, Pageable pageable) {
        return userRepository.findAllByEmail(email, pageable);
    }
}
