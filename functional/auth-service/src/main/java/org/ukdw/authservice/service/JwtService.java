package org.ukdw.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.ukdw.authservice.config.AppProperties;
import org.ukdw.authservice.entity.CustomUserDetails;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Autowired
    private final AppProperties appProperties;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) throws ParseException {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken (UserDetails userDetails) throws ParseException {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) throws JwtException {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();
        return claimsResolvers.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser().verifyWith(getSigningKey()).build();
        return parser.parseSignedClaims(token).getPayload();
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(appProperties.getAuth().getTokenExpirationDay());
        Date expirationDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        var customUserDetails = (CustomUserDetails) userDetails;

        var role = customUserDetails.getUserAccountEntity().getGroups().stream().findFirst().get().getGroupname();
        var permission = customUserDetails.getUserAccountEntity().getGroups().stream().findFirst().get().getPermission();
        var id = customUserDetails.getUserAccountEntity().getId();

        extraClaims.put("role", role);
        extraClaims.put("permission", permission);
        extraClaims.put("id", id);

        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    private String generateRefreshToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
        LocalDateTime localDateTime =  LocalDateTime.now().plusDays(appProperties.getAuth().getTokenExpirationDay() + 6);
        Date expirationDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        var customUserDetails = (CustomUserDetails) userDetails;
        var role = customUserDetails.getUserAccountEntity().getGroups().stream().findFirst().get().getGroupname();
        var permission = customUserDetails.getUserAccountEntity().getGroups().stream().findFirst().get().getPermission();
        var id = customUserDetails.getUserAccountEntity().getId();

        extraClaims.put("role", role);
        extraClaims.put("permission", permission);
        extraClaims.put("id", id);


        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appProperties.getAuth().getTokenSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        final String userName = extractUserName(refreshToken);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(refreshToken);
    }
}
