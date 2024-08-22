package org.khazar.SpringSecurity.service;

import org.khazar.SpringSecurity.payload.RefreshTokenPayload;
import org.khazar.SpringSecurity.payload.SignInPayload;
import org.khazar.SpringSecurity.payload.SignUpPayload;
import org.khazar.SpringSecurity.response.SignInResponse;

public interface AuthBusinessService {

    void signUp(SignUpPayload signUpPayload);
    SignInResponse signIn(SignInPayload signInPayload);
    Void refresh(RefreshTokenPayload refreshTokenPayload);
    void signOut();

    void setAuthentication(String username);
}
