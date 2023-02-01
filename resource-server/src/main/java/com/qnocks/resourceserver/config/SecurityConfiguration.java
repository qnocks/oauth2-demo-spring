package com.qnocks.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Protect all endpoints with the authentication
     * Indicate which scope is necessary to request the endpoints
     * Then indicate that I'm a resource server
     * And JWT will be used to validate the authentication against authorization
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.mvcMatcher("/**")
                .authorizeRequests()
                .mvcMatchers("/**")
                .access("hasAuthority('SCOPE_message.read')")
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}
