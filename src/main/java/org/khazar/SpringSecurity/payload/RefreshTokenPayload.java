package org.khazar.SpringSecurity.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenPayload {

    String refreshToken;
    boolean isRememberMe;
}

