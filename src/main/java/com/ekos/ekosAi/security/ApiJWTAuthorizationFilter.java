package com.ekos.ekosAi.security;

import static com.ekos.ekosAi.security.SecurityConstants.HEADER_AUTHORIZATION;
import static com.ekos.ekosAi.security.SecurityConstants.TOKEN_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class ApiJWTAuthorizationFilter extends BasicAuthenticationFilter {

  public ApiJWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader(HEADER_AUTHORIZATION);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_AUTHORIZATION);

    Claims claims =
        Jwts.parser()
            .setSigningKey(SecurityConstants.SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();

    var user = claims.getSubject();
    var authorities =
        ((List<String>) claims.get("authorities"))
            .stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    if (user != null) {
      return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    return null;
  }
}
