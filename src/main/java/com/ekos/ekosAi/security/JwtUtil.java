package com.ekos.ekosAi.security;

/*import com.ekos.ekosAi.exception.BadRequestException;*/
import com.ekos.ekosAi.exception.BadRequestException;
import com.ekos.ekosAi.security.model.*;

import com.ekos.ekosAi.util.MessageUtil;
import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  public static final String TOKEN_PREFIX = "Bearer ";
  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  @Value("${robin.app.jwtSecret}")
  private String jwtSecret;

  @Value("${robin.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getEmail()))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(12)))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
        .compact();
  }

  private Authentication getUser(String token) {

    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    String username = claims.getSubject();
    @SuppressWarnings("unchecked")
    List<String> roleList = (List<String>) claims.get("roles");

    Collection<? extends GrantedAuthority> authorities =
        roleList.stream()
            .map(authority -> new SimpleGrantedAuthority(authority))
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(username, "", authorities);
  }

  public String getEmailFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser()
          .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
          .parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
      throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN.getKey());
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
      throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN.getKey());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
      throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN.getKey());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
      throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN.getKey());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
      throw new BadRequestException(MessageUtil.INVALID_JWT_TOKEN.getKey());
    }
  }
}
