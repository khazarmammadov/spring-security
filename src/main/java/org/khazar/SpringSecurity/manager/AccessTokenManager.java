package org.khazar.SpringSecurity.manager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.constants.TokenConstants;
import org.khazar.SpringSecurity.entity.User;
import org.khazar.SpringSecurity.getter.UsernameGetter;
import org.khazar.SpringSecurity.properties.SecurityProperties;
import org.khazar.SpringSecurity.token.TokenGenerator;
import org.khazar.SpringSecurity.token.TokenReader;
import org.khazar.SpringSecurity.util.PublicPrivateKeyUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccessTokenManager implements TokenGenerator<User>, TokenReader<Claims>, UsernameGetter {

    private final SecurityProperties securityProperties;

    @Override
    public String getUsername(String token) {

        return read(token).get(TokenConstants.USERNAME, String.class);
    }

    @Override
    public String generateToken(User object) {

        Claims claims = Jwts.claims();

        claims.put(TokenConstants.USERNAME, object.getUsername());
        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getAccessTokenValidityTime());

        return Jwts.builder()
                .setSubject(String.valueOf(object.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public Claims read(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
