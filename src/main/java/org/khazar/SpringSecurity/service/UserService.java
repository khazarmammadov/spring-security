package org.khazar.SpringSecurity.service;

import org.khazar.SpringSecurity.dto.CreatedUserRequest;
import org.khazar.SpringSecurity.entity.User;

public interface UserService {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    void save(CreatedUserRequest request);
}
