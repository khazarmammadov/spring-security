package org.khazar.SpringSecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.entity.User;
import org.khazar.SpringSecurity.properties.LoggedInUserDetails;
import org.khazar.SpringSecurity.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        log.info(">> user: {}", user);
        return new LoggedInUserDetails(user.getId(), user.getUsername(), user.getPassword());
    }
}
