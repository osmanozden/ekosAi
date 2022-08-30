package com.ekos.ekosAi.service;

import com.ekos.ekosAi.dto.UserDto;
import com.ekos.ekosAi.exception.BadRequestException;
import com.ekos.ekosAi.mapper.UserMapper;
import com.ekos.ekosAi.model.User;
import com.ekos.ekosAi.repository.UserRepository;
import com.ekos.ekosAi.security.SecurityConstants;
import com.ekos.ekosAi.util.MessageUtil;
import com.ekos.ekosAi.exception.BadRequestException;
import com.ekos.ekosAi.model.User;
import com.ekos.ekosAi.util.MessageUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.codefirst.robin.model.service.ErrorDto.USER_NOT_FOUND;
import static com.ekos.ekosAi.util.MessageUtil.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuthorizationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
  private static final int TIME_LIMIT_AS_HOUR = 12;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserDto getCurrentUser() {
    var userId = getCurrentUserId();
    if (userId == null) return null;

    var userEntity = userRepository.findById(Long.parseLong(userId));
    if (userEntity.isPresent()) {
      return userMapper.toDto(userEntity.get());
    }

    LOGGER.error("{} : {} ", AuthorizationService.class.getName(), USER_NOT_FOUND);
    throw new BadRequestException(USER_NOT_FOUND.getKey());
  }

  public String getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getPrincipal().equals("anonymousUser"))
      return null;
    return authentication.getPrincipal().toString();
  }


  public String getJWTToken(User user) {
    var privileges = new HashSet<String>();
    user.getRoleList().stream()
        .forEach(
            role -> {
              privileges.add(role.getName());
              var toAdd =
                  role.getAuthorities().stream().map(a -> a.getName()).collect(Collectors.toSet());
              privileges.addAll(toAdd);
            });

    if (user.getExtraAuthorities() != null && !user.getExtraAuthorities().isEmpty()) {
      privileges.addAll(
          user.getExtraAuthorities().stream().map(o -> o.getName()).collect(Collectors.toSet()));
    }
    var roles = user.getRoleList().stream().map(o -> o.getName()).collect(Collectors.toSet());


    return Jwts.builder()
        .setSubject(user.getId().toString())
        .claim("authorities", privileges)
        .claim("roles", null)
        .claim("email", user.getEmail())
        .claim("name", user.getFirstName())
        .claim("surname", user.getLastName())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(TIME_LIMIT_AS_HOUR)))
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
        .compact();
  }
}
