package com.app.security;

import com.app.config.AppProperties;
import com.app.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

@Component
public class TokenProvider {
    String secretKey = "anphuc";
    int expiredTokenMsec = 60 * 60 * 1000;
    private AppProperties appProperties;
    public final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    public String generateToken(Account account) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredTokenMsec);
        return Jwts.builder()
                .setSubject(account.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateTokenData(Account account) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expiredTokenMsec);
        return Jwts.builder()
                .setSubject(String.valueOf(account))
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
        String token = Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
        return token;
    }
    public Integer getIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public Claims getDataFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims;
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            return false;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            return false;
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            return false;
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            return false;
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            return false;
        }
    }
}
