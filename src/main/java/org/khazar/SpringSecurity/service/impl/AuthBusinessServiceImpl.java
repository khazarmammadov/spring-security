package org.khazar.SpringSecurity.service.impl;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.dto.CreatedUserRequest;
import org.khazar.SpringSecurity.dto.RefreshTokenDto;
import org.khazar.SpringSecurity.entity.User;
import org.khazar.SpringSecurity.manager.AccessTokenManager;
import org.khazar.SpringSecurity.manager.RefreshTokenManager;
import org.khazar.SpringSecurity.payload.RefreshTokenPayload;
import org.khazar.SpringSecurity.payload.SignInPayload;
import org.khazar.SpringSecurity.payload.SignUpPayload;
import org.khazar.SpringSecurity.response.SignInResponse;
import org.khazar.SpringSecurity.service.AuthBusinessService;
import org.khazar.SpringSecurity.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthBusinessServiceImpl implements AuthBusinessService {


    final AuthenticationManager authenticationManager;
    final AccessTokenManager accessTokenManager;
    final RefreshTokenManager refreshTokenManager;
    final UserService userService;
    final UserDetailsService userDetailsService;
    final PasswordEncoder passwordEncoder;


    @Override
    public SignInResponse signIn(SignInPayload payload) {
        System.out.println("serv1");

        authenticate(payload);
        System.out.println("serv2");

        return prepareLoginResponse(payload.getUsername(), payload.isRememberMe());
    }

    @Override
    public Void refresh(RefreshTokenPayload payload) {
         prepareLoginResponse(
                refreshTokenManager.getUsername(payload.getRefreshToken()),
                payload.isRememberMe()
        );
         return null;
    }

    @Override
    public void signOut() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("{} user logout succeed", userDetails.getUsername());
        SecurityContextHolder.clearContext();
    }

    @Override
    public void signUp(SignUpPayload payload) {

        if (userService.existsByUsername(payload.getUsername())) {
            throw new RuntimeException("User Exist...");
        }


        CreatedUserRequest request = CreatedUserRequest.builder()
                .username(payload.getUsername())
                .mail(payload.getMail())
                .password(passwordEncoder.encode(payload.getPassword()))
                .build();
        userService.save(request);


    }

    @Override
    public void setAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        SecurityContextHolder.getContext().setAuthentication(
                UsernamePasswordAuthenticationToken.authenticated(userDetails, "", userDetails.getAuthorities())
        );
    }

    private void authenticate(SignInPayload payload) {
        System.out.println("serv3: " + payload);
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken.unauthenticated(payload.getUsername(), payload.getPassword())
            );

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong Credentials: "  + e.getMessage());
        }
    }

    private SignInResponse prepareLoginResponse(String username, boolean rememberMe) {
        User user = userService.findByUsername(username);
        System.out.println("serv4");

        return SignInResponse.builder()
                .accessToken(accessTokenManager.generateToken(user))
                .refreshToken(refreshTokenManager.generateToken(
                        RefreshTokenDto.builder()
                                .user(user)
                                .rememberMe(rememberMe)
                                .build()
                ))
                .build();
    }


}