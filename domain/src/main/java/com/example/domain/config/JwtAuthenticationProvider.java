package com.example.domain.config;

import com.example.domain.common.UserType;
import com.example.domain.common.UserVo;
import com.example.domain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Objects;

public class JwtAuthenticationProvider {

    private String secretKey = "secretKey";

    private long tokenValidTime = 1000L * 60 * 60 * 24;

    public String createToken(String userPk, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
            .setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles", userType);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            System.out.println("claimsJws : " + claimsJws);
            System.out.println("now : " + new Date());

            System.out.println("expired time : " + claimsJws.getBody().getExpiration());
            System.out.println("여기까진 괜찮아");
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토근을 받아서 user 로 반환
    public UserVo getUserVo(String token) {
        Claims c = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(c.getId()))),
            Aes256Util.decrypt(c.getSubject()));
    }

}
