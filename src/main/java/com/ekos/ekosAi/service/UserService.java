package com.ekos.ekosAi.service;

import com.ekos.ekosAi.exception.BadRequestException;
import com.ekos.ekosAi.model.User;
import com.ekos.ekosAi.repository.UserRepository;
import com.ekos.ekosAi.security.model.UserDetailsImpl;
import com.ekos.ekosAi.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findOneByEmail(email);
        if (user.isPresent()) {
            return UserDetailsImpl.build(user.get());
        }
        throw new BadRequestException(MessageUtil.EMAIL_ERROR.getKey());
    }


}
