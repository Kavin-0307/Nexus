package com.nexus.services;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	@Value("${security.jwt.expiration-time}")
	private long  jwtExpiration;
	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	public String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	public<T> T extractClaims(String token,Function<Claims,T> claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaims(token,Claims::getExpiration).before(new Date());
	}
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
	}
	
	private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}
	private Key getSignInKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	public String generateToken(String identifier) {
		Map<String,Object> claims=new HashMap<>();
		
		return Jwts.builder().setClaims(claims).setSubject(identifier).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+1000*60*30)).signWith(getSignInKey(),SignatureAlgorithm.HS256).compact();
	}
	
}
