package com.qnocks.clientserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Indicate that all coming requests must be authenticated
     * To authenticate the request the end user will be redirected to the OAuth2 login page, which is present in authorization-server
     * Indicate that this is an OAuth2 client server
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.loginPage(
                        OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI +
                                "/messages-client-oidc"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }
}
