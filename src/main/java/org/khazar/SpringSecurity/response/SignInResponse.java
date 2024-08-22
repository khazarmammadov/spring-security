package org.khazar.SpringSecurity.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInResponse {

    String accessToken;
    String refreshToken;
    UserInfo userInfo;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class UserInfo {

        Long id;
        String name;
        String surname;
        String imageUrl;


    }

}
