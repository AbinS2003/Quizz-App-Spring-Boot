package com.abin.quizzapp.service;


import com.abin.quizzapp.dao.UserRepository;
import com.abin.quizzapp.dto.UserDto;
import com.abin.quizzapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public User save(UserDto userDto) {

        User user = new User(
                userDto.getFullname(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

}
