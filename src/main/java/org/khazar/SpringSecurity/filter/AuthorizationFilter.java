package org.khazar.SpringSecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.constants.TokenConstants;
import org.khazar.SpringSecurity.manager.AccessTokenManager;
import org.khazar.SpringSecurity.service.AuthBusinessService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final AccessTokenManager accessTokenManager;
    private final AuthBusinessService authBusinessService;
    private final AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("dof1");
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("dof2");
        if (Objects.nonNull(token) && token.startsWith(TokenConstants.PREFIX)) {
            System.out.println("dof3");
            String username = accessTokenManager.getUsername(token.substring(7));
            authBusinessService.setAuthentication(username);
        }



        filterChain.doFilter(request, response);

    }
}
