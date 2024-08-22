package org.khazar.SpringSecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.dto.CreatedUserRequest;
import org.khazar.SpringSecurity.entity.User;
import org.khazar.SpringSecurity.repository.UserRepository;
import org.khazar.SpringSecurity.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void save(CreatedUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getMail());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
