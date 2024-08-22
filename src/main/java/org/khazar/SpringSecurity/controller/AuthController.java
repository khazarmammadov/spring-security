package org.khazar.SpringSecurity.controller;

import lombok.RequiredArgsConstructor;
import org.khazar.SpringSecurity.response.BaseResponse;
import org.khazar.SpringSecurity.payload.RefreshTokenPayload;
import org.khazar.SpringSecurity.payload.SignInPayload;
import org.khazar.SpringSecurity.payload.SignUpPayload;
import org.khazar.SpringSecurity.response.SignInResponse;
import org.khazar.SpringSecurity.service.AuthBusinessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusinessService authBusinessService;


        @PostMapping("/sign-in")
        public BaseResponse<SignInResponse> login(@RequestBody SignInPayload payload) {
            System.out.println("controller");
            return BaseResponse.success(authBusinessService.signIn(payload));
        }

        @PostMapping("/token/refresh")
        public BaseResponse<Void> refresh(@RequestBody RefreshTokenPayload payload) {
            return BaseResponse.success(authBusinessService.refresh(payload));
        }

        @PostMapping("/sign-out")
        public BaseResponse<Void> signOut() {
            authBusinessService.signOut();
            return BaseResponse.success();
        }

        @PostMapping("/sign-up")
        public void signUp(@RequestBody SignUpPayload payload) {
            authBusinessService.signUp(payload);
        }



    }

