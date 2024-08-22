package org.khazar.SpringSecurity.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreatedUserRequest {

    private String username;
    private String mail;
    private String password;

}

