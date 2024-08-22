package org.khazar.SpringSecurity.manager;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.khazar.SpringSecurity.constants.TokenConstants;
import org.khazar.SpringSecurity.dto.RefreshTokenDto;
import org.khazar.SpringSecurity.entity.User;
import org.khazar.SpringSecurity.getter.UsernameGetter;
import org.khazar.SpringSecurity.properties.SecurityProperties;
import org.khazar.SpringSecurity.token.TokenGenerator;
import org.khazar.SpringSecurity.token.TokenReader;
import org.khazar.SpringSecurity.util.PublicPrivateKeyUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.khazar.SpringSecurity.constants.TokenConstants.TOKEN_TYPE;

@Component
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenManager implements TokenGenerator<RefreshTokenDto>, TokenReader<Claims>, UsernameGetter {

    private final SecurityProperties securityProperties;

    @Override
    public String generateToken(RefreshTokenDto obj) {

        final User user = obj.getUser();

        Claims claims = Jwts.claims();
        claims.put(TokenConstants.USERNAME, user.getUsername());
        claims.put("type", TOKEN_TYPE);

        Date now = new Date();
        Date exp = new Date(now.getTime() + securityProperties.getJwt().getRefreshTokenValidityTime(obj.isRememberMe()));

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setIssuedAt(now)
                .setExpiration(exp)
                .addClaims(claims)
                .signWith(PublicPrivateKeyUtils.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public Claims read(String token) {
        Claims tokenData = Jwts.parserBuilder()
                .setSigningKey(PublicPrivateKeyUtils.getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String typeOfToken = tokenData.get("type", String.class);

        if(!TOKEN_TYPE.equals(typeOfToken)) {
            throw new RuntimeException("Invalid type of Token");
        }

        return tokenData;
    }

    @Override
    public String getUsername(String token) {
        return read(token).get(TokenConstants.USERNAME, String.class);
    }
}

