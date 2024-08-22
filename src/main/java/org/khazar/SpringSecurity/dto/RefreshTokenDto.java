package org.khazar.SpringSecurity.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.khazar.SpringSecurity.entity.User;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenDto {

    boolean rememberMe;
    User user;
}
