package com.qnocks.authorizationserver.config;

import com.qnocks.authorizationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var login = authentication.getName();
        var password = authentication.getCredentials().toString();

        var optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(CharBuffer.wrap(password), optionalUser.get().getPassword())) {
                return UsernamePasswordAuthenticationToken.authenticated(login, password, Collections.emptyList());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
