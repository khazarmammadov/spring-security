package org.khazar.SpringSecurity.payload;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignInPayload {

    private String username;
    private String password;
    private boolean isRememberMe;
}
