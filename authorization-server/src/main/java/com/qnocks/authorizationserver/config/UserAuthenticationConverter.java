package com.qnocks.authorizationserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qnocks.authorizationserver.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class UserAuthenticationConverter implements AuthenticationConverter {

    private final ObjectMapper mapper;

    @Override
    public Authentication convert(HttpServletRequest request) {
        UserDto userDto;
        try {
            userDto = mapper.readValue(request.getInputStream(), UserDto.class);
        } catch (IOException e) {
            return null;
        }

        return UsernamePasswordAuthenticationToken.unauthenticated(userDto.getLogin(), userDto.getPassword());
    }
}

