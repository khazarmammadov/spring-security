package org.khazar.SpringSecurity.payload;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpPayload {

    String username;
    String password;
    String mail;
    boolean rememberMe;
}

